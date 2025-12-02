(ns day2
  (:require [clojure.string :as str]))

(defn parse-item [x]
  (->>
    (str/split x #"-")
    (map parse-long)
    (vec)))

(def input
  (->>
    (str/split (slurp "inputs/day2") #",")
    (map parse-item)))

(defn check [x]
  (->>
    x
    (str)
    (re-find #"^(\d+)(\1)$")
    (nil?)
    (not)))

(defn check-range [start stop]
  (apply + (filter check (range start (+ stop 1)))))

(defn part1 [inputs]
  (apply + (map #(apply check-range %) inputs)))


(println (part1 input))

