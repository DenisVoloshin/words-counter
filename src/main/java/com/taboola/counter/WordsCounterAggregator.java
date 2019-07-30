package com.taboola.counter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread safe words counters aggregator
 */
public class WordsCounterAggregator {
    private final ConcurrentMap<String, AtomicInteger> wordsCounters = new ConcurrentHashMap<>();

    void countWord(String word) {
        wordsCounters.putIfAbsent(word, new AtomicInteger(0));
        wordsCounters.get(word).incrementAndGet();
    }

    /**
     * Returns the total occurrence of a given word
     * @param word the total occurrence
     * @return occurrence of a word
     */
    public int getWordCounter(String word) {
        return wordsCounters.get(word).get();
    }

    /**
     * Composes a string which holds the counting result.
     * @return counting status as a string
     */
    public String generateStatus() {
        StringBuffer status = new StringBuffer();
        AtomicInteger total = new AtomicInteger();
        wordsCounters.forEach((key, value) -> {
            status.append(key).append(" ").append(value).append("\n");
            total.incrementAndGet();

        });
        status.append("Total:").append(" ").append(total);
        return status.toString();
    }

    /**
     * @return the total number for unique word
     */
    public int getUniqueWordsCounter() {
        return wordsCounters.size();
    }
}
