(ns com.indoles.clj.withLockedFile.shell
  (:import (java.io PrintWriter))
  (:require [conch.core :as sh]
            [com.indoles.clj.withLockedFile :as wlf])
  (:gen-class))

(defn- stream-to-err
  [process from & args]
  (apply sh/stream-to process from (System/err) args))

(defn- shell-cmd
  [& args]
  (let [p (apply sh/proc args)
        exit (future (sh/exit-code p))]
    (future (sh/feed-from p System/in))
    (future (sh/stream-to-out p :out :flush true))
    (future (stream-to-err p :err))
    @exit))

(defn -main
  [filePath interval nTries & args]
  (System/exit (wlf/obtainAndInvoke filePath
                                    (read-string interval)
                                    (read-string nTries)
                                    #(apply shell-cmd args)))
)