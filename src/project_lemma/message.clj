(ns project-lemma.message
  (:gen-class))

(require '[clojure.data.json :as json])

(defmulti message
  "Maps the message values to the appropriate message type"
  (fn [msg-type-val payload-values] (:type msg-type-val)))

(defmethod message "event"
  [msg-type-val payload-values]
  {:type (:type msg-type-val) :guest (get payload-values 1) :topic (get payload-values 2) :value (get payload-values 3)})

(defmethod message :default
  [msg-type-val payload-values]
  nil)
