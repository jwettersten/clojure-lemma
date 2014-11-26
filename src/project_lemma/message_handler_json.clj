(ns project-lemma.message-handler-json
  (:gen-class))

(require '[clojure.data.json :as json]
         '[clojure.java.io :as io]
         '[project-lemma.message :as msg-type])

(defn payload-match?
  [msg]
  (try
    (let [msg-length-count (Integer/parseInt (subs msg 0 6))
         msg-payload-data (count (subs msg 6))]
      (= msg-length-count msg-payload-data))
    (catch Exception e
      (println "Could not perform payload comparison: " e) false)))

(defn create-payload-message
  [msg-payload-data]
  (try
    (json/read-str msg-payload-data)
    (catch Exception e
      (println "Could not parse JSON message data: " e) nil)))

(defn parse-payload
  [msg]
  (try
    (let [msg-payload-data (subs msg 6)]
      (if (payload-match? msg) (create-payload-message msg-payload-data) nil))
    (catch Exception e
      (println "Failed parsing payload count: " e) nil)))

(defn read-payload-length
  [reader]
  (let [buffer (byte-array 6) n (.read reader buffer)]
    (Integer/parseInt (String. buffer))))

(defn read-payload
  [reader length]
  (let [buffer (byte-array (+ 6 length)) n (.read reader buffer)]
    (String. buffer)))

(defn read
  [reader]
  ; read the bytes, parse the payload, return message
  (msg-type/message (parse-payload (read-payload [reader (read-payload-length reader)]))))
