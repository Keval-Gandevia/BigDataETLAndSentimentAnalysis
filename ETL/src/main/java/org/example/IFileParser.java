package org.example;

import java.util.List;

/**
 * The IFileParser interface defines the interaction of file parsing operations.
 * It declares methods for reading data from a file, extracting news content,
 * extracting data between specified tags, and splitting file data into words.
 */
public interface IFileParser {

    /**
     * Reads the data from the file and stores it into fileData variable
     *
     * @return - Status of read operation
     */
    public boolean readFromFile();

    /**
     * Extract the TITLE and BODY tag from REUTERS tag
     *
     * @return - List of all News
     */
    public List<News> getNews();

    /**
     * Returns the text between given tag after removing some special characters
     *
     * @param reuter - reuter tag from which we need to extract given tag
     * @param tag    - the tag which we need to extract
     * @return - The text between given tag
     */
    public String getTagData(String reuter, String tag);

    /**
     * Splits the file data and list of words
     *
     * @return - List of words extracted from the file data
     */
    public List<String> splitFileData();
}
