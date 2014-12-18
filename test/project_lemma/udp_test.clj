(ns project-lemma.udp-test
  (:require [clojure.test :refer :all]
            [project-lemma.udp :refer :all]))

(deftest empty-test
  (testing ""
           (is (= true true))))
