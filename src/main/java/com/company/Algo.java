package com.company;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Algo {

    /**
     * Filter max occurrence words from given Map collection
     * @param allWords: Map collection contains all words red from all files
     * @param numOfWords: number of occurrence words given by user
     * @return: return Map collection contains max occurrence words
     */
    public static Map<String,Integer> getMaxOccurrenceWords(Map<String,Integer> allWords, int numOfWords){
        //filter max
        boolean flag = false;
        Map<String,Integer> maxOccurs = new HashMap<String, Integer>();
        for (Map.Entry<String,Integer> wordEntry : allWords.entrySet()){
            if (maxOccurs.size() < numOfWords){
                maxOccurs.put(wordEntry.getKey(),wordEntry.getValue());
            }else {
                flag = true;
            }
            if (flag){
                Map<String, Integer> sorted = getSorted(maxOccurs);
                for (Map.Entry<String,Integer> maxEntry : sorted.entrySet()){
                    if (wordEntry.getValue() > maxEntry.getValue()) {
                        maxOccurs.remove(maxEntry.getKey());
                        maxOccurs.put(wordEntry.getKey(), wordEntry.getValue());
                        break;
                    }
                }
            }
        }
        return maxOccurs;
    }
    static Map<String, Integer> getSorted(Map<String, Integer> coll){
        return  coll.entrySet().stream().sorted(comparingByValue()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }
}
