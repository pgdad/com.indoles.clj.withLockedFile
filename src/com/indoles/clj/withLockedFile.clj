(ns com.indoles.clj.withLockedFile
  (:import (java.io File FileOutputStream)
           (java.nio.channels FileLock OverlappingFileLockException)
           (java.util.concurrent TimeoutException)))

(defn- tryLock
  [^String filePath interval nTries]
  (loop [triesLeft nTries
         f (File. filePath)
         fc (-> f FileOutputStream. .getChannel)
         gotLock (.tryLock fc)]
    (if gotLock
      gotLock
      (if (and (< 0 nTries) (= 0 triesLeft))
        ;; done trying
        (throw (TimeoutException. "Unable to obtain lock within time allowed."))
        ;; wait and try again
        (do
          (Thread/sleep interval)
          (recur (- triesLeft 1) f fc (.tryLock fc)))))))

(defn obtainAndInvoke
  [filePath interval nTries f]
  (when-let [^FileLock gotLock (tryLock filePath interval nTries)]
    (let [result (f)]
      (.release gotLock)
      result)))
