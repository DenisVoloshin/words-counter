package com.taboola.counter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


/**
 * The custom iterator knows to traverse through the give file
 * and returns tokens as a single alphanumeric words
 */
class WordsIterator {

    private final String path;
    private Scanner scanner;
    private FileInputStream inputStream;
    private Iterator<String> currentLineWordsIterator;

    WordsIterator(String path) {
        this.path = path;
    }

    void open() throws FileNotFoundException {
        inputStream = new FileInputStream(path);
        scanner = new Scanner(inputStream, "UTF-8");
    }

    void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (scanner != null) {
            scanner.close();
        }
        scanner = null;
    }

    boolean hasNextWord() {
        if (currentLineWordsIterator != null && currentLineWordsIterator.hasNext()) {
            return true;
        } else {
            while (scanner != null && scanner.hasNextLine()) {
                currentLineWordsIterator = parseLine(scanner.nextLine()).iterator();
                if (currentLineWordsIterator.hasNext()) {
                    return true;
                }
            }
        }
        return false;
    }

    String nextWord() {
        if (currentLineWordsIterator != null && currentLineWordsIterator.hasNext()) {
            return currentLineWordsIterator.next();
        }
        return null;
    }

    private Collection<String> parseLine(String line) {
        List<String> list = new ArrayList<>();
        int pos = 0, end;
        while ((end = line.indexOf(' ', pos)) >= 0) {
            list.add(line.substring(pos, end));
            pos = end + 1;
        }
        return list;
    }
}
