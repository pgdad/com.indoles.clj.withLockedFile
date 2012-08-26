(defproject com.indoles.clj.withLockedFile "0.1.0"
  :description "Utility that uses locked file to enable synchronization over different processes."
  :url "http://github.com/indoles/"
  :plugins [[lein-swank "1.4.4"]]
  :main com.indoles.clj.withLockedFile.shell
  :aot :all
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [conch "0.3.1"]])
