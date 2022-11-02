(ns br.dev.zz.eql-destructure)


(defn ->ast
  [x]
  (if (map? x)
    {:type :root
     :children (into []
                 (mapcat (fn [[k v]]
                           (case (when (ident? k)
                                   (name k))
                             "as" []
                             "or" []
                             "keys" (if-let [ns (some-> k
                                                  namespace
                                                  str)]
                                      (for [prop v
                                            :let [dispatch-key (if (simple-ident? prop)
                                                                 (keyword ns (name prop))
                                                                 (keyword prop))]]
                                        {:type :prop
                                         :dispatch-key dispatch-key
                                         :key dispatch-key})
                                      (for [prop v
                                            :let [dispatch-key (keyword prop)
                                                  default? (contains? (:or x)
                                                             prop)]]
                                        (cond-> {:type :prop
                                                 :dispatch-key dispatch-key
                                                 :key dispatch-key}
                                          default? (assoc-in [:props :default] (get (:or x) prop)))))
                             (if (symbol? k)
                               [{:type :prop
                                 :dispatch-key v
                                 :key v}]
                               [(assoc (->ast k)
                                  :type :join
                                  :dispatch-key v
                                  :key v)]))))


                 x)}
    (->ast (first x))))
