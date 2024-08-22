package org.example;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyCounter {
    public static void main(String[] args) {

        // Create a SparkSession
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("FrequencyCounter")
                .master("local")
                .getOrCreate();

        // Read the data from the reut2-009.sgm and stores it into lines RDD.
        JavaRDD<String> lines = sparkSession
                .read()
                .textFile(args[0])
                .javaRDD();

        // Regex to remove tags from the data so that we can avoid them while finding the frequencies for highest and lowest occuring file.
        Pattern pattern = Pattern.compile("<[^>]+>");

        // Remove tags from each line
        JavaRDD<String> linesAfterTagRemoved = lines.map(line -> {
            Matcher matcher = pattern.matcher(line);
            return matcher.replaceAll("");
        });

        // Read the stop words
        JavaRDD<String> stopWordsLines = sparkSession
                .read()
                .textFile(args[1])
                .javaRDD();

        // Split the stop words string to get stop words
        JavaRDD<String> stopWords = stopWordsLines
                .flatMap(line -> Arrays.asList(line.split("\\s+")).iterator())
                .map(String::toLowerCase);

        // Perform the filter on the lines
        // Split the lines to get each word
        // Convert each word to lowercase
        // Consider only the alphabetic letters
        // Remove the word if it matches with any of the stop word
        // Remove word if it is empty
        JavaRDD<String> words = linesAfterTagRemoved
                .flatMap(line -> Arrays.asList(line.split("\\s+")).iterator())
                .map(String::toLowerCase)
                .map(word -> word.replaceAll("[^a-zA-Z]", ""))
                .subtract(stopWords)
                .filter(word -> !word.isEmpty());

        // Find the frequency of each word
        JavaPairRDD<String, Integer> countResult = words.mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((value1, value2) -> value1 + value2);

        // Find the distinct word's count
        long distinctWords = countResult.keys().distinct().count();

        System.out.println("Total number of distinct words: " + distinctWords);

        // Find the word with the highest frequency and the lowest frequency
        Map<String, Integer> map = countResult.collectAsMap();

        Map.Entry<String, Integer> highestFrequency = Collections.max(map.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<String, Integer> lowestFrequency = Collections.min(map.entrySet(), Map.Entry.comparingByValue());

        System.out.println("Highest Frequency word: " + highestFrequency.getKey());
        System.out.println("Highest Frequency: " + highestFrequency.getValue());
        System.out.println("Lowest Frequency word: " + lowestFrequency.getKey());
        System.out.println("Lowest Frequency: " + lowestFrequency.getValue());

    }
}