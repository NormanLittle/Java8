package com.sandbox;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;

public class DistanceToTheScore {
    public static void main(String[] args) {
        /*              <Norman>      <Chris>       <Neil>        <Dave>        <Iain>       vs          <result>    */
        Stream.of(aGame(aScore(0, 0), aScore(2, 0), aScore(2, 0), aScore(2, 0), aScore(3, 1)).withResult(aResult(2, 1)),
                  aGame(aScore(1, 0), aScore(2, 0), aScore(2, 0), aScore(2, 0), aScore(2, 0)).withResult(aResult(3, 0)),
                  aGame(aScore(0, 1), aScore(1, 1), aScore(2, 0), aScore(1, 2), aScore(0, 3)).withResult(aResult(0, 4)))
              .map(Game::toResult)
              .forEach(System.out::println);
    }

    private static Score aScore(int lhs, int rhs) {
        return new Score(lhs, rhs);
    }

    private static Score aResult(int lhs, int rhs) {
        return aScore(lhs, rhs);
    }

    public static Game aGame(Score... scores) {
        return new Game(scores);
    }

    private static class Score {
        private int lhs;
        private int rhs;

        public Score(int lhs, int rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        public int getDistanceFromResult(Score result) {
            int distance = abs(lhs - result.lhs) + abs(rhs - result.rhs);
            if ((lhs > rhs && result.lhs > result.rhs) ||
                (lhs < rhs && result.lhs < result.rhs) ||
                (lhs == rhs && result.lhs == result.rhs)) {
                // matched result
            } else {
                distance++;
            }
            return distance * 2;
        }
    }

    private static class Game {
        private Stream<Score> scores;
        private Score result;

        public Game(Score... scores) {
            this.scores = Stream.of(scores);
        }

        public Game withResult(Score result) {
            this.result = result;
            return this;
        }

        public String toResult() {
            return scores.map(s -> valueOf(s.getDistanceFromResult(result)))
                         .collect(Collectors.joining(","));
        }
    }
}