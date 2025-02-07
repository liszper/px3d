(ns user
 (:require [figwheel-sidecar.repl-api :as ra]
           [clojure.java.io :as io]
           [environ.core :refer [env]]))

(import 'java.lang.Runtime)

(println "Start blender rebuilder?" (boolean (env :dev)))

; hack to start the blend file watcher in the background
(when (env :dev)
  (.start
    (Thread.
      (fn []
        (let [proc (.exec (Runtime/getRuntime) "./bin/watch-and-build-assets.sh")]
          (with-open [rdr (io/reader (.getInputStream proc))]
            (doseq [line (line-seq rdr)]
              (println line))))))))

(defn start-fw []
 (ra/start-figwheel!))

(defn stop-fw []
 (ra/stop-figwheel!))

(defn cljs []
 (ra/cljs-repl))
