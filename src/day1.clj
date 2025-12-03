(ns day1
  (:require [clojure.string :as str]))

; data
(defn parse-line [x]
  (->
    x
    (str/replace #"R|L" {"R" "" "L" "-"})
    (parse-long)))

(def input
  (->>
    (slurp "inputs/day1")
    (str/split-lines)
    (map parse-line)
    vec))

; part 1
(defn step [current move]
  (mod (+ current move) 100))

(defn part1 [start steps]
  (->>
    (reductions step start steps)
    (filter zero?)
    (count)))

(println "Part 1:" (time (part1 50 input)))

; part 2
(defn gen-steps [steps move]
  (let [
        current (last steps)
        dir (compare move 0)
        start (+ current dir)
        end (+ start move)]
     (map #(mod % 100) (range start end dir))))

(defn part2 [start steps]
  (->>
    (reductions gen-steps [start] steps)
    (flatten)
    (filter zero?)
    (count)))


(println "Part 2:" (time (part2 50 input)))
