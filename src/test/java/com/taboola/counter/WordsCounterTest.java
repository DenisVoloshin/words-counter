package com.taboola.counter;

import org.junit.Assert;
import org.junit.Test;

public class WordsCounterTest {

    @Test
    public void testOneFile() {
        WordsCounter wordsCounter = new WordsCounter();

        wordsCounter.load(ClassLoader.getSystemResource("words1.txt").getPath());
        String status = wordsCounter.getWordsCounterAggregator().generateStatus().replaceAll("\n", "");
        Assert.assertEquals("the 2one 1this 2is 2first 1second 1Total: 6", status);
    }

    @Test
    public void testFlatDirectory() {
        WordsCounter wordsCounter = new WordsCounter();

        wordsCounter.load(WordsCounter.getInputFilePaths(ClassLoader.getSystemResource("input_dir").getPath()));
        String status = wordsCounter.getWordsCounterAggregator().generateStatus().replaceAll("\n", "");
        Assert.assertEquals("the 2one 1this 2is 2first 1second 1Total: 6", status);
    }

    @Test
    public void testDirectoryRecursively() {
        WordsCounter wordsCounter = new WordsCounter();

        wordsCounter.load(WordsCounter.getInputFilePaths(ClassLoader.getSystemResource("input_dir").getPath()));
        String status = wordsCounter.getWordsCounterAggregator().generateStatus().replaceAll("\n", "");
        Assert.assertEquals("the 2one 1this 2is 2first 1second 1Total: 6", status);
    }

    @Test
    public void testSingleBigFile() {
        WordsCounter wordsCounter = new WordsCounter();

        wordsCounter.load(ClassLoader.getSystemResource("book.txt").getPath());
        Assert.assertEquals(4711, wordsCounter.getWordsCounterAggregator().getUniqueWordsCounter());
    }
}