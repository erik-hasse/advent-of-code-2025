(ns day5
  (:require [clojure.string :as str]))

(def parts
  (->
    "inputs/day5"
    slurp
    (str/split #"\n\n")))

(defn parse-range [x]
  (->>
    (str/split x #"-")
    (mapv parse-long)))

(def ranges
  (->>
    (first parts)
    str/split-lines
    (mapv parse-range)))

(def ids
  (->>
    (second parts)
    str/split-lines
    (mapv parse-long)))

(defn in-range [id [start stop]]
  (and (>= id start) (<= id stop)))

(defn in-any [id]
  (some true? (map (partial in-range id) ranges)))

(count (keep  in-any ids))