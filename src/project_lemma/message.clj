(ns project-lemma.message
  (:gen-class))

; consider functions per type
(defmulti map-message-type
  "Maps the message values to the appropriate message type"
  (fn [msg-payload] (first msg-payload)))

(defmethod map-message-type "event"
  [[msg-type guest topic value]]
  {:type msg-type :guest guest :topic topic :value value})

(defmethod map-message-type "heartbeat"
  [[msg-type guest]]
  {:type msg-type :guest guest})

(defmethod map-message-type :default
  [[]]
  nil)
