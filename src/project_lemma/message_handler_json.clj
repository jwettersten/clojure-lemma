(ns project-lemma.message-handler-json
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io])

; json message handler
; pass this into server to read and format messages
; need to add exception handling here
; validate length
(defn read
  [reader]
  (.readLine reader))

; message parser notes:
; if (!message.equals("")) {
;    Event event = MessageParser.parseEvent(message);
;    filter.handle(event);
; }
;
; 000042["event","guest1","topic1",[1,2,"potato"]]
(defn payload-match?
  [msg]
  (try
    (let [msg-length-count (Integer/parseInt (subs msg 0 6))
         msg-payload-data (count (subs msg 6))]
      (if (= msg-length-count msg-payload-data) true false))
    (catch Exception e
      (println "Could not perform payload comparison: " e) false)))

(defn extract-payload-message
  [msg-payload-data]
  (try
    ; parse the message data using JSON and pass to message module
    ; to determine the type of message
    ; and return that new message type
    (json/read-str msg-payload-data)
    (catch Exception e
      (println "Could not parse JSON message data: " e) nil)))

(defn parse-payload
  [msg]
  (try
    (let [msg-payload-data (subs msg 6)]
      (if (payload-match? msg) (extract-payload-message msg-payload-data) nil))
    (catch Exception e
      (println "Failed parsing payload count: " e) nil)))
