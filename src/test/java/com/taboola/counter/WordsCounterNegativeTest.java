package com.taboola.counter;

import org.junit.Assert;
import org.junit.Test;

public class WordsCounterNegativeTest {

    @Test
    public void testOneNotExistingFileShouldReturnListWithOneFailure() {
        WordsCounter wordsCounter = new WordsCounter();
        Assert.assertTrue(wordsCounter.getFailedFilesList().size()==0);
        wordsCounter.load("pathToNotExistingFile");
        Assert.assertTrue(wordsCounter.getFailedFilesList().size()==1);
    }
}
