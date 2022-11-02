(ns br.dev.zz.eql-destructure-test
  (:require [clojure.test :refer [deftest is]]
            [br.dev.zz.eql-destructure :as eql-destructure]))

(deftest hello
  (is (= {:type :root
          :children [{:type :prop, :dispatch-key :a, :key :a}
                     {:type :prop, :dispatch-key :b/c, :key :b/c}
                     {:type :prop, :dispatch-key :d/e, :key :d/e}
                     {:type :prop, :dispatch-key :f/g, :key :f/g}
                     {:type :prop, :dispatch-key :h/i, :key :h/i}
                     {:type :prop, :dispatch-key :j/k, :key :j/k}]}
        (-> '{:keys [a :b/c d/e]
              :f/keys [g :h/i j/k]}
          eql-destructure/->ast
          #_(doto clojure.pprint/pprint))))
  (is (= {:type :root, :children [{:type :prop, :dispatch-key :a, :key :a}]}
        (-> '{a :a}
          eql-destructure/->ast
          #_(doto clojure.pprint/pprint))))
  (is (= {:type :root
          :children [{:type :join
                      :dispatch-key :a
                      :key :a
                      :children [{:type :prop
                                  :dispatch-key :b
                                  :key :b}]}]}
        (-> '{{:keys [b]} :a}
          eql-destructure/->ast
          #_(doto clojure.pprint/pprint))))
  (is (= {:type :root
          :children [{:type :join
                      :dispatch-key :a
                      :key :a
                      :children [{:type :prop
                                  :dispatch-key :b
                                  :key :b}]}]}
        (-> '{[{:keys [b]}]
              :a}
          eql-destructure/->ast
          #_(doto clojure.pprint/pprint))))
  (is (= {:type :root, :children [{:type :prop, :dispatch-key :a, :key :a}]}
        (-> '{:keys [a]
              :as b}
          eql-destructure/->ast
          #_(doto clojure.pprint/pprint))))
  (is (= {:type :root,
          :children [{:type :prop, :dispatch-key :a, :key :a, :props {:default 42}}]}
        (-> '{:keys [a]
              :or {a 42}}
          eql-destructure/->ast
          (doto clojure.pprint/pprint)))))
