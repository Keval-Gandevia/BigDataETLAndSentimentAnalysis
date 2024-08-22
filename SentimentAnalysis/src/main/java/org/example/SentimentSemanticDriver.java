package org.example;

import java.util.List;

/**
 * Driver class for sentiment analysis
 */
public class SentimentSemanticDriver {
    public static void main(String[] args) {

        // Connection string of mongodb
        String connectionUri = "mongodb+srv://kevalgandevia2002:Captioncool%401605@cluster0.zmuddss.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        // Database name
        String database = "ReuterDb";

        // Collection name
        String collection = "News";

        // Create an object of MongoDB (custom class) by passing connection string, database name, and collection name
        MongoDB mongoDB = new MongoDB(connectionUri, database, collection);

        // Get the list of titles from MongoDB collection
        System.out.println("Reading titles from MongoDB collection.....");
        List<String> titleList = mongoDB.getTitleFromMongoDB();

        // Create the object of FileParser by passing positive words file
        FileParser fileParserPositiveWords = new FileParser("InputOutputFiles/positive-words.txt");

        System.out.println("Reading positive words.....");
        // Read the text from the positive words file
        fileParserPositiveWords.readFromFile();

        // Split the positive words and store it into list
        List<String> positiveWords = fileParserPositiveWords.splitFileData();

        // Create the object of FileParser by passing negative words file
        FileParser fileParserNegativeWords = new FileParser("InputOutputFiles/negative-words.txt");

        System.out.println("Reading negative words.....");
        // Read the text from the negative words file
        fileParserNegativeWords.readFromFile();

        // Split the negative words and store it into list
        List<String> negativeWords = fileParserNegativeWords.splitFileData();

        // Create an object of the SentimentSemantic class
        SentimentSemantic sentimentSemantic = new SentimentSemantic(positiveWords, negativeWords);

        System.out.println("Sentiment analysis in progress.....");
        // Get all analysis
        List<Tuple> tupleList = sentimentSemantic.compareTitle(titleList);

        // Save a list of tuples into CSV
        sentimentSemantic.saveAnalysiToCSVFile(tupleList, "InputOutputFiles/Sentiment-Semantic-Analysis.csv");

    }
}