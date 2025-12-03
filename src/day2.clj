(ns day2
  (:require [clojure.string :as str]))

; data
(defn parse-item [x]
  (->>
    (str/split x #"-")
    (mapv parse-long)))

(def input
  (->>
    (str/split (slurp "inputs/day2") #",")
    (mapv parse-item)))

; part 1
(defn check [pattern x]
  (boolean (re-find pattern (str x))))

(defn check-range [pattern start stop]
  (->>
    (range start (inc stop))
    (filter (partial check pattern))
    (reduce +)))

(defn solve [pattern inputs]
  (->>
    inputs
    (map (fn [[start stop]] (check-range pattern start stop)))
    (reduce +)))

(println "Part 1:" (time (solve #"^(\d+)(\1)$"  input)))

; part 2
(println "Part 2:" (time (solve #"^(\d+)(\1)+$" input)))
