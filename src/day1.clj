(ns day1
  (:require [clojure.string :as str]))

; data
(defn parse-line [x]
  (->
    x
    (str/replace "R" "")
    (str/replace "L" "-")
    (parse-long)))

(def input
  (->>
    (slurp "inputs/day1")
    (str/split-lines)
    (map parse-line)
    vec))

(Math/floorMod -106 100)
(mod -106 100)
; part 1
(defn step [current move]
  (mod (+ current move) 100))

(defn part1 [start steps]
  (->>
    (reductions step start steps)
    (filter zero?)
    (count)))

(part1 50 input)

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


(part2 50 input)

