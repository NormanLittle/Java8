package com.sandbox.interview;

import java.util.regex.Pattern;

public class MaxWordInSentenceDemo {

    public static void main(String[] args) {
        String sentence = "This is a sentence and I want the length of the largest word please.";
        System.out.println(Pattern.compile("\\W")
                                  .splitAsStream(sentence)
                                  .map(String::length)
                                  .max(Integer::compareTo)
                                  .orElse(0));
    }
}
