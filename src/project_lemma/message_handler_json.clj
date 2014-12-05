(ns project-lemma.message-handler-json
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io]
         '[project-lemma.message :as message])

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
  (message/map-message-type (parse-payload (read-payload reader (read-payload-length reader))))
   (catch Exception e
     (println "Could not return message: " e))))

(defn create-event-msg
  [lemma-id topic value]
  ;need to dynamically build the json with the proper lenght of the payload
  (str "000042[\"event\"," lemma-id "," topic "," value "]"))

