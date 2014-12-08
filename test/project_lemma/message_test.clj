(ns project-lemma.message-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [project-lemma.message :refer :all]))

(deftest test-message-event-type-mapping
  (testing "Event message type mapping"
           (let [json-msg '["event","guest1","topic1", [1,2,"potato"]]]
             (is (= (map-message-type json-msg) {:type (get json-msg 0) :guest (get json-msg 1) :topic (get json-msg 2) :value (get json-msg 3)})))))

(deftest test-message-heartbeat-type-mapping
  (testing "Heartbeat message type mapping"
           (let [json-msg '["heartbeat","guest1"]]
             (is (= (map-message-type json-msg) {:type (get json-msg 0) :guest (get json-msg 1)})))))

(deftest test-message-event-type-mapping-failure
  (testing "Ensure malformed event message type mapping fails gracefully"
           (let [json-msg (json/read-str "[]")]
             (is (= (map-message-type json-msg) nil)))))

(deftest test-create-event-msg
         (testing "creating an event message payload"
                  (is (= (create-event-message "guest1" "topic1" "don't panic") ["event" "guest1" "topic1" "don't panic"]))))

(deftest test-create-registration-msg
         (testing "creating a registration message payload"
                  (is (= (create-registration-message "guest1" 4423 ["topic1" "topic2" "topic3"] [] "clojure" "1.6.0") ["register" "guest1" 4423 ["topic1" "topic2" "topic3"] [] "clojure" "1.6.0" nil]))))
