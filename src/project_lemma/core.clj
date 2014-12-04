(ns project-lemma.core
  (:gen-class))

(require '[project-lemma.lemma :as lemma])

(defn -main
  "Lemma standalone app entry point"
  [& args]
  (lemma/init [args]))
