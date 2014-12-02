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
