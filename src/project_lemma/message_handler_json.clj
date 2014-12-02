(ns project-lemma.message-handler-json
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io]
         '[project-lemma.message :as msg-type])

(defn read-payload-length
  [reader]
  (try
    (let [buffer-string (String. (byte-array (take 6 (repeatedly #(.read reader)))))]
      (Integer/parseInt buffer-string))
    (catch Exception e
      (println "Could not convert buffer length to Integer: " e) nil)))

(defn read-payload
  [reader length]
  (String. (byte-array (take length (repeatedly #(.read reader))))))

(defn parse-payload
  [msg-payload-data]
  (try
    (json/read-str msg-payload-data)
    (catch Exception e
      (println "Could not parse JSON message data: " e) nil)))

(defn read
  [reader]
 (try
  (msg-type/message (parse-payload (read-payload reader (read-payload-length reader))))
   (catch Exception e
     (println "Could not return message: " e))))
