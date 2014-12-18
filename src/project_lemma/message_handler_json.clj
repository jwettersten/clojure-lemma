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
      (println "Could not convert buffer length to Integer: " e) 0)))

(defn read-payload
  [reader length]
  (if (> length 0) (String. (byte-array (take length (repeatedly #(.read reader))))) nil))

(defn parse-payload
  [msg-payload-data]
  (if-not nil (try
                (json/read-str msg-payload-data)
                (catch Exception e
                  (println "Could not parse JSON message data: " e) nil))))

(defn read-msg-in
  [reader]
 (if-not nil (try
               (message/map-message-type (parse-payload (read-payload reader (read-payload-length reader))))
               (catch Exception e
                 (println "Could not return message: " e) nil))))

(defn apply-topic-handler
  "Map and call the relevant topic handler on the incoming message"
  [msg topic-handlers]
  (let [{topic-handler :topic-handler topic :topic value :value} (assoc msg :topic-handler (topic-handlers (msg :topic)))]
    (topic-handler topic value)))

(defn create-payload-length
  [msg]
  (let [byte-length-str (str (count msg))]
    (str (subs "000000" 0 (- 6 (count byte-length-str))) byte-length-str)))

(defn write-json
  "Converts msg into json string"
  [msg]
  (json/write-str msg))

(defn package-message
  "Prepares message for outbound communication to noam"
  [msg]
  (let [json-msg (write-json msg)]
    (str (create-payload-length json-msg) json-msg)))
