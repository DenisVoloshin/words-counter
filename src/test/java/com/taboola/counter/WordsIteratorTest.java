package com.taboola.counter;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class WordsIteratorTest {

    @Test
    public void openExistingFileTest() {
        WordsIterator wordsIterator = new WordsIterator(ClassLoader.getSystemResource("words1.txt").getPath());
        try {
            wordsIterator.open();
        } catch (FileNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(wordsIterator.hasNextWord());
        Assert.assertNotNull(wordsIterator.nextWord());
    }

    @Test
    public void closeIteratorTest() {
        WordsIterator wordsIterator = new WordsIterator(ClassLoader.getSystemResource("words1.txt").getPath());
        try {
            wordsIterator.open();
            wordsIterator.close();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertFalse(wordsIterator.hasNextWord());
    }

    @Test
    public void hasNextWordIteratorTest() {
        WordsIterator wordsIterator = new WordsIterator(ClassLoader.getSystemResource("words1.txt").getPath());
        try {
            wordsIterator.open();
        } catch (FileNotFoundException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(wordsIterator.hasNextWord());
        Assert.assertEquals(wordsIterator.nextWord(),"this");
    }
}