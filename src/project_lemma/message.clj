(ns project-lemma.message
  (:gen-class))

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

(defn create-event-message
  "Creates outbound event messages"
  [lemma-id topic value]
  ["event" lemma-id topic value])

(defn create-registration-message
  "Creates outbound registration message"
  [lemma-id lemma-port hears-list speaks-list dialect lemma-version & options]
  ["register" lemma-id lemma-port hears-list speaks-list dialect lemma-version options])
