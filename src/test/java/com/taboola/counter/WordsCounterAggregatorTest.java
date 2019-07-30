package com.taboola.counter;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class WordsCounterAggregatorTest {

    @Test
    public void populateAggregatorWithParallelThreadsTest() {
        WordsCounterAggregator wordsCounterAggregator = new WordsCounterAggregator();
        List<String> testWords = new ArrayList<>();
        testWords.add("word1");
        testWords.add("word2");
        testWords.add("word3");
        int expectedUniqueCounter = 100;

        IntStream.range(0, expectedUniqueCounter).parallel().forEach(i -> testWords.stream().parallel().forEach(wordsCounterAggregator::countWord));

        Assert.assertEquals(wordsCounterAggregator.getUniqueWordsCounter(), testWords.size());
        testWords.forEach(word -> Assert.assertEquals(wordsCounterAggregator.getWordCounter(word), expectedUniqueCounter));
    }
}