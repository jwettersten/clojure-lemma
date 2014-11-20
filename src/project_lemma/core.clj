(ns project-lemma.core
  (:gen-class))

(require '[project-lemma.server :as server])

(defn -main
  "Serving via TCP..."
  [& args]
  (server/serve-persistent 8888 #(.toLowerCase %)))
