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
      (println e))))

(defn extract-payload-values
  [msg-payload-data]
  (try
    ; pull out the json key values into a map
    (json/read-str msg-payload-data)
    (catch Exception e
      (println "Could not parse JSON message data: " e))))

(defn parse-payload
  [msg]
  (try
    (let [msg-payload-data (subs msg 6)]
      (if (payload-match? msg) (extract-payload-values msg-payload-data) nil))
    (catch Exception e
      (println e))))
