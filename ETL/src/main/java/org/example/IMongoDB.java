package org.example;

import java.util.List;


/**
 * The IMongoDB interface defines interaction with a MongoDB database.
 * It declares methods for adding a list of News objects to a MongoDB collection,
 * and for retrieving titles from the MongoDB collection.
 */
public interface IMongoDB {

    /**
     * Add a list of extracted News to MongoDB database
     *
     * @param newsList - List of News that needed to be inserted into MongoDB database
     * @return - True if insertion is successful, false otherwise
     */
    public boolean addDocumentToCollection(List<News> newsList);

    /**
     * Retrieves titles from the MongoDB collection.
     *
     * @return - List of titles retrieved from the MongoDB collection
     */
    public List<String> getTitleFromMongoDB();
}
