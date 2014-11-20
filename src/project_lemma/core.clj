(ns project-lemma.core
  (:gen-class))

(require '[project-lemma.server :as server]
         '[project-lemma.message-handler-json :as json-handler])

;#(.toLowerCase %)

(defn -main
  "Serving via TCP..."
  [& args]
  (server/serve-persistent 8888 json-handler/read))
