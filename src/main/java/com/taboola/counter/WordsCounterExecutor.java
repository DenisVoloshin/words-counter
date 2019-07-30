package com.taboola.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * The class coordinates creation and execution of {@link WordsCounterWorker}
 */
class WordsCounterExecutor {

    private final List<String> failedFiles;
    private final WordsCounterAggregator wordsCounterAggregator;

    WordsCounterExecutor(WordsCounterAggregator wordsCounterAggregator) {
        this.wordsCounterAggregator = wordsCounterAggregator;
        this.failedFiles = new ArrayList<>();
    }

    /**
     * Executes the list of files, each file is process in the separate thread
     * @param fileNames the file names will be processed
     * @return  a list of successfully processed file names
     */
    List<String> execute(String[] fileNames) {
        ExecutorService executorService = new ThreadPoolExecutor(1, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        List<Pair<String, Future>> tasks = new ArrayList<>();
        List<String> successfullyProcessedFile = new ArrayList<>();

        for (String fileName : fileNames) {
            Future result = executorService.submit(new WordsCounterWorker(fileName, wordsCounterAggregator));
            tasks.add(new Pair<>(fileName, result));
        }

        for (Pair<String, Future> futureTask : tasks) {
            String fileName = futureTask.getKey();
            try {
                futureTask.getValue().get();
                successfullyProcessedFile.add(fileName);
            } catch (InterruptedException | ExecutionException e) {
                // a file processing failed
                failedFiles.add(fileName);
                System.err.println("Occurred error while processing file:" + fileName + ",error detail:" + e.getMessage());
            }

        }
        //shut down the executor service now
        executorService.shutdown();
        return successfullyProcessedFile;
    }

    /**
     * Returns a list of failed file names.
     * @return
     */
    List<String> getFailedFiles() {
        return failedFiles;
    }

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }
    }
}
