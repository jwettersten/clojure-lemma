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

(defmethod map-message-type "polo"
  [[msg-type noam-room port address]]
  {:type msg-type :room-name noam-room :port port :ip address})

(defmethod map-message-type :default
  [[]]
  nil)

(defn create-event-message
  "Creates outbound event messages"
  [lemma-name topic value]
  ["event" lemma-name topic value])

(defn create-registration-message
  "Creates outbound registration message"
  [lemma-name lemma-port hears-list speaks-list lemma-dialect lemma-version & options]
  ["register" lemma-name lemma-port hears-list speaks-list lemma-dialect lemma-version options])

(defn create-discovery-message
  "Creates outbound marco discovery message"
  [lemma-name noam-room lemma-dialect lemma-version]
  ["marco" lemma-name noam-room lemma-dialect lemma-version])
