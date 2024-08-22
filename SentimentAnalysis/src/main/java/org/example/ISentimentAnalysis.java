package org.example;

import java.util.List;

public interface ISentimentAnalysis {


    /**
     * Compares the news titles against the provided list of positive and negative words.
     * Generates a list of Tuples containing analysis results.
     *
     * @param titleList - List of title to analyze
     * @return - List of Tuple objects representing analysis results
     */
    public List<Tuple> compareTitle(List<String> titleList);

    /**
     * Saves the analysis results to a CSV file.
     *
     * @param - tupleList List of Tuple objects containing analysis results
     * @param - fileName Name of the CSV file to save
     * @return - True if saving is successful, false otherwise
     */
    public boolean saveAnalysiToCSVFile(List<Tuple> tupleList, String fileName);
}
