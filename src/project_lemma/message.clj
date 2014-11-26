(ns project-lemma.message
  (:gen-class))

(require '[clojure.data.json :as json])

; consider functions per type
(defmulti message
  "Maps the message values to the appropriate message type"
  (fn [msg-payload] (first msg-payload)))

(defmethod message "event"
  [[msg-type guest topic value]]
  {:type msg-type :guest guest :topic topic :value value})

(defmethod message "heartbeat"
  [[msg-type guest]]
  {:type msg-type :guest guest})

(defmethod message :default
  [[]]
  nil)
