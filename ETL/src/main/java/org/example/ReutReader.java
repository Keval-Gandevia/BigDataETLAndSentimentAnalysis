package org.example;

import java.util.List;

/**
 * Driver class
 */
public class ReutReader {
    public static void main(String[] args) {

        // Create the object of FileParser by passing input file name
        FileParser fileParser = new FileParser("InputOutputFiles/reut2-009.sgm");

        // Read the text from the given input file
        fileParser.readFromFile();

        // Fetch the News object (title and body) from file text
        List<News> newsList = fileParser.getNews();

        System.out.println("News are extracted from the input file.");
        System.out.println("Number of News: " + newsList.size());

        // Connection string of mongodb
        String connectionUri = "mongodb+srv://kevalgandevia2002:Captioncool%401605@cluster0.zmuddss.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        // Database name
        String database = "ReuterDb";

        // Collection name
        String collection = "News";

        // Create an object of MongoDB (custom class) by passing connection string, database name, and collection name
        MongoDB mongoDB = new MongoDB(connectionUri, database, collection);

        // Add list of News as documents to given collection
        System.out.println("Adding news to MongoDB collection.....");
        boolean status = mongoDB.addDocumentToCollection(newsList);

        // If documents are added, display success message
        if (status) {
            System.out.println("Documents are added to MongoDB collection.");
        }
    }
}