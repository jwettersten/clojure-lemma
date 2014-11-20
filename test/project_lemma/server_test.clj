(ns project-lemma.server-test
  (:require [clojure.test :refer :all]
            [project-lemma.server :refer :all]))

(deftest test-hello
  (testing "hello"
           (assert (= (hello "jw") "Hello, jw"))))

(deftest test-helloJSON
  (testing "helloJSON"
           (assert (= (helloJSON "{\"name\": \"jw\"}") "{\"greeting\":\"Hello!\",\"greeting-name\":\"jw\"}"))))
