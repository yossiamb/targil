package com.company.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class FileUtils {
    private final static Logger logger = Logger.getLogger(FileUtils.class);
    /**
     * create concurrent List object to make it thread safe
     */
    private static Map<String,Integer> allWords = new ConcurrentHashMap<String, Integer>();

    /**
     * read files from disk
     * @param dir: directory to read from disk
     */
    public static void getFiles(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    getFiles(file);
                } else {
                    if (file.getName().endsWith(".txt")) {
                        getWordsFromFile(file);
                    }
                }
            }
        } catch (Exception e) {
           logger.error(e.fillInStackTrace());
        }
    }

    /**
     * get lines from file and collect words
     * @param file: file to to extract words from
     */
    private static void getWordsFromFile(File file){
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            logger.error("File: "+file+" not found!");
            logger.error(e.getStackTrace());
        }
        /**
         * get lines of file
         */
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            addWordsOfFileLineToCollection(line);
        }
    }

    /**
     * collect words from each line of file to List object
     * @param line: line of file
     */
    private static void addWordsOfFileLineToCollection(String line) {
        String[] lineWords = line.split(" ");

        for (int i = 0; i < lineWords.length; i++) {
            if (lineWords[i].trim().length() != 0) {
                if (allWords.containsKey(lineWords[i])) {
                    for (Map.Entry<String, Integer> entry : allWords.entrySet()) {
                        if (entry.getKey().equals(lineWords[i])) {
                            allWords.put(entry.getKey(), entry.getValue() + 1);
                            break;
                        }
                    }
                } else
                    allWords.put(lineWords[i], 1);
            }
        }
    }
    public static  Map<String,Integer> getAllWords(){
        return allWords;
    }
}
