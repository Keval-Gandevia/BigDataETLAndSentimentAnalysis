package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class, SentimentSemantic, is responsible for performing sentiment analysis on news titles by comparing them against lists of positive and negative words.
 * It provides methods for comparing titles, calculating scores, and saving the analysis
 * results to a CSV file.
 */
public class SentimentSemantic implements ISentimentAnalysis {

    // Lists of positive words
    private List<String> positiveWords;

    // Lists of negative words
    private List<String> negativeWords;

    /**
     * Constructs a SentimentSemantic object with the provided lists of positive and negative words.
     *
     * @param positiveWords - List of positive words
     * @param negativeWords - List of negative words
     */
    public SentimentSemantic(List<String> positiveWords, List<String> negativeWords) {
        this.positiveWords = positiveWords;
        this.negativeWords = negativeWords;
    }

    /**
     * Compares the news titles against the provided list of positive and negative words.
     * Generates a list of Tuples containing analysis results.
     *
     * @param titleList - List of title to analyze
     * @return - List of Tuple objects representing analysis results
     */
    public List<Tuple> compareTitle(List<String> titleList) {

        List<Tuple> tupleList = new ArrayList<>();

        for (String title : titleList) {

            // Replace title which contains the comma in between with empty string
            title = title.toLowerCase().replace(",", "");

            // Split the tile by white space
            String[] listOfWords = title.split(" ");

            int score = 0;
            List<String> matchedWords = new ArrayList<>();

            // Analyze each word in the title
            for (String word : listOfWords) {

                // Check if word is in positive word list
                for (int i = 0; i < positiveWords.size(); i++) {
                    if (word.equals(positiveWords.get(i))) {
                        score++;
                        matchedWords.add(word);
                        break;
                    }
                }
                // Check if word is in negative word list
                for (int i = 0; i < negativeWords.size(); i++) {
                    if (word.equals(negativeWords.get(i))) {
                        score--;
                        matchedWords.add(word);
                        break;
                    }
                }
            }

            // Create Tuple object with analysis results
            Tuple tuple = new Tuple(score, title, matchedWords);
            tuple.updatePolarity();
            tupleList.add(tuple);

        }

        return tupleList;
    }

    /**
     * Saves the analysis results to a CSV file.
     *
     * @param - tupleList List of Tuple objects containing analysis results
     * @param - fileName Name of the CSV file to save
     * @return - True if saving is successful, false otherwise
     */
    public boolean saveAnalysiToCSVFile(List<Tuple> tupleList, String fileName) {

        try {
            // Create file and PrintWriter for writing
            File inputFile = new File(fileName);
            PrintWriter printWriter = new PrintWriter(inputFile);

            // StringBuilder for storing data into CSV
            StringBuilder stringBuilder = new StringBuilder();

            // Add first line of csv as headers
            stringBuilder.append("News#");
            stringBuilder.append(",");
            stringBuilder.append("Title Content");
            stringBuilder.append(",");
            stringBuilder.append("Match");
            stringBuilder.append(",");
            stringBuilder.append("Score");
            stringBuilder.append(",");
            stringBuilder.append("Polarity");
            stringBuilder.append("\n");

            // Iterate through tupleList to add analysis results to CSV
            for (int i = 0; i < tupleList.size(); i++) {
                // Add serial number
                stringBuilder.append(i + 1);
                // New cell
                stringBuilder.append(",");
                // Add title content
                stringBuilder.append("\"");
                stringBuilder.append(tupleList.get(i).getTitle());
                stringBuilder.append("\"");
                // New cell
                stringBuilder.append(",");
                // Add match words
                // Add " to add comma inside one single cell otherwise it will take new cell
                stringBuilder.append("\"");
                List<String> matchedWords = tupleList.get(i).getMatchedWords();
                for (int j = 0; j < matchedWords.size(); j++) {
                    stringBuilder.append(matchedWords.get(j));
                    if (j != matchedWords.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append("\"");
                // New cell
                stringBuilder.append(",");
                // Add score
                stringBuilder.append(tupleList.get(i).getScore());
                // New cell
                stringBuilder.append(",");
                // Add polarity
                stringBuilder.append(tupleList.get(i).getPolarity());
                // New cell
                stringBuilder.append("\n");

            }

            // Write CSV content to file and close PrintWriter
            printWriter.write(stringBuilder.toString());
            printWriter.close();

            System.out.println("Analysis result is saved into CSV file.");

            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
