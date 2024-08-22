package org.example;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * The MongoDB class handles operations with a MongoDB database.
 * It provides methods to add a list of extracted News objects to a specified collection in the database, and retrieve titles from the MongoDB collection.
 */
public class MongoDB implements IMongoDB {

    // MongoDB's connection string
    private String connectionName;

    // Name of the MongoDB database
    private String databaseName;

    // Name of the MongoDB collection
    private String collectionName;

    /**
     * Constructs a MongoDB object with the provided connection string, database name, and collection name.
     *
     * @param connectionName - MongoDB's connection string
     * @param databaseName   - The name of the MongoDB database
     * @param collectionName - The name of the MongoDB collection
     */
    public MongoDB(String connectionName, String databaseName, String collectionName) {
        this.connectionName = connectionName;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    /**
     * Add a list of extracted News to MongoDB database
     *
     * @param newsList - List of News that needed to be inserted into MongoDB database
     * @return - True if insertion is successful, false otherwise
     */
    public boolean addDocumentToCollection(List<News> newsList) {

        // Make connection with mongodb database
        try (MongoClient mongoClient = MongoClients.create(this.connectionName)) {
            try {
                // Get the database name if exist otherwise create the database
                MongoDatabase database = mongoClient.getDatabase(this.databaseName);

                // Get the collection if exist otherwise create the collection
                MongoCollection<Document> collection = database.getCollection(this.collectionName);

                List<Document> documentList = new ArrayList<>();

                // Convert list of News to list of Document
                for (News news : newsList) {

                    // If news is empty and body tag is not empty then only add body into document
                    if (news.getTitle().isEmpty() && (!news.getBody().isEmpty())) {
                        documentList.add(new Document()
                                .append("body", news.getBody()));
                    }
                    // If body is empty and title is not empty then only add title into document
                    else if (news.getBody().isEmpty() && (!news.getTitle().isEmpty())) {
                        documentList.add(new Document()
                                .append("title", news.getTitle()));
                    } else if ((!news.getTitle().isEmpty()) && (!news.getBody().isEmpty())) {
                        documentList.add(new Document()
                                .append("title", news.getTitle())
                                .append("body", news.getBody()));
                    }
                }

                System.out.println("Number of news added to MongoDB collection: " + documentList.size());
                // Insert all documents to a collection
                collection.insertMany(documentList);

                return true;
            } catch (MongoException mongoException) {
                System.out.println(mongoException.getMessage());
                return false;
            } finally {
                mongoClient.close();
            }
        }
    }

    /**
     * Retrieves titles from the MongoDB collection.
     *
     * @return - List of titles retrieved from the MongoDB collection
     */
    public List<String> getTitleFromMongoDB() {

        List<String> titleList = new ArrayList<>();
        // Make connection with mongodb database
        try (MongoClient mongoClient = MongoClients.create(this.connectionName)) {
            // Get the database name if exist otherwise create the database
            MongoDatabase database = mongoClient.getDatabase(this.databaseName);

            // Get the collection if exist otherwise create the collection
            MongoCollection<Document> collection = database.getCollection(this.collectionName);

            // For each document get title and add into a list
            for (Document document : collection.find()) {
                String title = document.getString("title");
                titleList.add(title);
            }
        }

        return titleList;
    }
}
