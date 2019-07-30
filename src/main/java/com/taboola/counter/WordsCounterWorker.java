package com.taboola.counter;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Defines a logic of the countering unit, which in charge to count words in the given file.
 */
class WordsCounterWorker implements Callable<Object> {
    private final String fileName;
    private final WordsCounterAggregator wordsCounterAggregator;

    WordsCounterWorker(String fileName, WordsCounterAggregator wordsCounterAggregator) {
        this.fileName = fileName;
        this.wordsCounterAggregator = wordsCounterAggregator;
    }

    @Override
    public Object call() throws IOException {
        WordsIterator wordsIterator = new WordsIterator(this.fileName);

        wordsIterator.open();
        while (wordsIterator.hasNextWord()) {
            wordsCounterAggregator.countWord(wordsIterator.nextWord());
        }
        wordsIterator.close();

        // return value is ignored
        return null;
    }
}


