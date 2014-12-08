(ns project-lemma.message-handler-json
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io]
         '[project-lemma.message :as message]
         '[project-lemma.tcp-client :as sender])

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

(defn read-msg-in
  [reader]
 (try
  (message/map-message-type (parse-payload (read-payload reader (read-payload-length reader))))
   (catch Exception e
     (println "Could not return message: " e))))

(defn apply-topic-handler
  "Attempt to map the incoming message to it's topic handler"
  [msg-map topic-handlers]
  (let [topic-handler (topic-handlers (msg-map :topic))]
    (topic-handler (msg-map :topic) (msg-map :value))))

(defn create-payload-length
  [msg]
  (let [byte-length-str (str (count msg))]
    (str (subs "000000" 0 (- 6 (count byte-length-str))) byte-length-str)))

(defn package-message
  "Prepares message for outbound communication to noam"
  [msg]
  (let [json-msg (json/write-str msg)]
    (str (create-payload-length json-msg) json-msg)))

