package com.company.app;

import com.company.Algo;
import com.company.thread.FileReaderRunnable;
import com.company.utils.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    private final static Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {


        /**
         * parse input and put to collection
         */
        int maxWords = 0;
        boolean flag = true;
        /**
         * Collect paths into Set object to prevent duplicate files
         */
        Set<File> parsedPaths = null;
        String input = "";
        Scanner scanner = null;
       do {
           try {
               System.out.println("Please enter max words and files in following format (Example for windows): ");
               System.out.println("Max-words 4 c:\\dir1\\file1 c:\\dir2\\file2");
               scanner = new Scanner(System.in);
               input = scanner.nextLine();
               parsedPaths = parseUserPaths(input);
               maxWords = Integer.valueOf(input.split(" ")[1]);
               if (maxWords <= 0){
                   System.out.println("Number should be greater than 0!");
                   System.out.println("Please try again: ");
                   input = scanner.nextLine();
               }
           } catch (Exception e) {
               logger.error(e.fillInStackTrace());
               flag = false;
           }
       }while (!flag || parsedPaths == null);

        /**
         * init executor with paths size to make sure every path threaten in parallel by different thread
         */
        ExecutorService executor = Executors.newFixedThreadPool(parsedPaths.size());
        for (File path : parsedPaths) {
            Runnable worker = new FileReaderRunnable(path);
            executor.execute(worker);
        }
        executor.shutdown();
        //wait until all jobs done and threads terminated
        while (!executor.isTerminated()) {
        }
        //FileUtils fileUtils = new FileUtils();
        Map<String,Integer> allWords = FileUtils.getAllWords();
        Map<String,Integer> max = Algo.getMaxOccurrenceWords(allWords,maxWords);
        System.out.println("=========================================================");
        for (Map.Entry<String,Integer> m : max.entrySet()){
            System.out.println("Word "+m.getKey()+" occured: "+m.getValue()+" times");
        }
    }
    static Set<File> parseUserPaths(String inpt) throws Exception{
        Set<File> filesCollection = new HashSet<File>();
        if (inpt.substring(12).split(" ") == null){
            return null;
        }
        String[]paths = inpt.substring(12).split(" ");
        for (String path: paths)
        filesCollection.add(new File(path));

        return filesCollection;
    }
}
