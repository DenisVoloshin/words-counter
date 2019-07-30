package com.taboola.counter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *  The main class which starts the words counter process.
 *  The class leverage two internal classes
 *  1. Static {@link WordsCounterAggregator} to aggregate words counter data which is populated
 *     from multiple threads
 *  2. {@link WordsCounterExecutor} is in change for init and coordinate the execution of  multiple threads.
 *     the class is able to return the list of file names the countering process failed.
 */
class WordsCounter {

    private final WordsCounterAggregator wordsCounterAggregator;
    private final WordsCounterExecutor wordsCounterExecutor;

    public static void main(String[] args) {

        // parse app input arguments
        if (args.length != 1) {
            String usage = "Wrong or missing required argument: \n" +
                    "\n" +
                    "usage: com.taboola.counter.WordsCounter " +
                    "  <arg>  path to the input directory " +
                    "  or path to the input file";
            System.out.println(usage);
            System.exit(0);
        }

        WordsCounter wc = new WordsCounter();
        wc.load(getInputFilePaths(args[0]));
        wc.displayStatus();
    }

    /**
     * Util method for providing the list of file names form the given location
     * @param input input folder | file
     * @return files list
     */
    public static String[] getInputFilePaths(String input) {
        File file = new File(input);
        if (file.exists()) {
            if (file.isFile()) {
                return new String[]{file.getAbsolutePath()};
            } else {
                return getFilesFromDirectory(input);
            }
        }
        return new String[0];
    }

    private static String[] getFilesFromDirectory(String directory) {
        List<String> fileNames = new ArrayList<>();
        try {
            Files.walk(Paths.get(directory))
                    .filter(Files::isRegularFile)
                    .forEach(file -> fileNames.add(file.toAbsolutePath().toString()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return fileNames.toArray(new String[0]);
    }

    public WordsCounter() {
        wordsCounterAggregator = new WordsCounterAggregator();
        wordsCounterExecutor = new WordsCounterExecutor(wordsCounterAggregator);
    }

    /**
     * The main method which initializes the countering process
     * @param fileNames the list for file name which should be processed.
     */
    public void load(String... fileNames) {
        wordsCounterExecutor.execute(fileNames);
    }

    /**
     * The file names which failed while counting process
     * @return the file names list
     */
    public List<String> getFailedFilesList(){
        return wordsCounterExecutor.getFailedFiles();
    }

    /**
     *  Prints counting result into STDOUT
     */
    private void displayStatus() {
        System.out.println(wordsCounterAggregator.generateStatus());
    }

    /**
     * Getter
     * @return
     */
    public WordsCounterAggregator getWordsCounterAggregator() {
        return wordsCounterAggregator;
    }
}
