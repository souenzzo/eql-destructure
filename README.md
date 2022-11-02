# eql-destructure

Utilities to represent [clojure's destructuring format](https://clojure.org/guides/destructuring)
as [eql](https://github.com/edn-query-language)

# Example

```clojure
(require '[br.dev.zz.eql-destructure :as eql-destructure])
(-> '{:keys [a]
      :or {a 42}}
  eql-destructure/->ast
  eql/ast->query)
=> [(:a {:default 42})] 
```

# TODO

- [ ] Allow to customize of `:default` as `:as` keys.
- [ ] Support aliases.
