(ns project-lemma.tcp-client-test
  (:require [clojure.test :refer :all]
            [project-lemma.tcp-client :refer :all]))

(deftest test-send-registration
         (testing "send-registration message from lemma to noam server"
                  (is (= 42 (send-registration "127.0.0.1" 8081)))))
