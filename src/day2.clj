(ns day2
  (:require [clojure.string :as str]))

; data
(defn parse-item [x]
  (->>
    (str/split x #"-")
    (map parse-long)
    (vec)))

(def input
  (->>
    (str/split (slurp "inputs/day2") #",")
    (map parse-item)))

; part 1
(defn check [pattern x]
  (->>
    x
    (str)
    (re-find pattern)
    (nil?)
    (not)))

(defn check-range [pattern start stop]
  (apply + (filter (partial check pattern) (range start (+ stop 1)))))

(defn solve [pattern inputs]
  (apply + (map #(apply (partial check-range pattern) %) inputs)))

(println "Part 1: " (solve #"^(\d+)(\1)$"  input))

; part 2
(println "Part 2: " (solve #"^(\d+)(\1)+$" input))
