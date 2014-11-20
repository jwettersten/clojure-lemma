(ns project-lemma.message-handler-json
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io])

; json message handler
; pass this into server to read and format messages
(defn read
  [reader]
  (json/read reader))


(defn helloJSON [name]
  (let [json-reader (json/read-str name :key-fn keyword)]
    (let [greeting-name (get json-reader :name)]
      (json/write-str {:greeting "Hello!" :greeting-name greeting-name}))))
