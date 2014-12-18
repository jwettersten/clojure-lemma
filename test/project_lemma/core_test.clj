(ns project-lemma.core-test
  (:require [clojure.test :refer :all]
            [project-lemma.core :refer :all]))

(deftest topic-handlers
  (testing "topic-handler calls"
    (is (= (topic1-handler "topic1" "Beware of the leopard.") "topic1-handler received topic1 with value: Beware of the leopard."))))
