(defproject project-lemma "0.1.0-SNAPSHOT"
  :description "Clojure noam lemma project"
  :url "http://localhost"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]]
  :main ^:skip-aot project-lemma.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
