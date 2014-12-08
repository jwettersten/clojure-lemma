(ns project-lemma.message-handler-json-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [clojure.java.io :as io :refer [file input-stream]]
            [project-lemma.message-handler-json :refer :all]))

(import '[java.io BufferedReader StringReader])

(deftest test-msg-reader-read-payload-length
         (testing "reading in message byte length from reader"
                  (let [in (BufferedReader. (StringReader. "000042[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]"))]
                    (is (= 42 (read-payload-length in))))))

(deftest test-msg-reader-read-payload
         (testing "reading in message byte length from reader"
                  (let [in (BufferedReader. (StringReader. "[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]"))]
                    (is (= "[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]" (read-payload in 42))))))

(deftest test-create-payload-values
  (testing "JSON msg data extraction"
           (is (= (parse-payload (str "[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")) (json/read-str "[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")))))

(deftest test-create-payload-values-fail
  (testing "JSON msg data extraction"
           (is (= (parse-payload (str "event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]")) nil ))))

(deftest test-read
         (testing "the complete read function"
                  (let [in (BufferedReader. (StringReader. "000042[\"event\",\"guest1\",\"topic1\",[1,2,\"potato\"]]"))]
                    (is (= {:type "event" :guest "guest1" :topic "topic1" :value [1 2 "potato"]} (project-lemma.message-handler-json/read in))))))

(deftest test-create-event-msg
         (testing "creating a json event message payload"
                  (is (= (create-event-msg "guest1" "topic1" "don't panic") "000041[\"event\",\"guest1\",\"topic1\",\"don't panic\"]"))))

(deftest test-create-payload-length
         (testing "creation of the message payload count and 0 based formatting"
                  (is (= "000041" (create-payload-length "[\"event\",\"guest1\",\"topic1\",\"don't panic\"]")))))

(deftest test-create-registration-msg
         (testing "creating a json registration message payload"
                  (is (= (create-registration-msg "guest1" 4423 ["topic1" "topic2" "topic3"] [] "clojure" "1.6.0") "000081[\"register\",\"guest1\",4423,[\"topic1\",\"topic2\",\"topic3\"],[],\"clojure\",\"1.6.0\",null]"))))
