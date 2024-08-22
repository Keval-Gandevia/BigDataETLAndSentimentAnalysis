package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The FileParser class handles operations related to file parsing.
 * It reads data from a file, extracts news content from REUTERS tags,
 * and provides methods to split the file data into words.
 */
public class FileParser implements IFileParser {

    // Name of the file
    private String fileName;

    // Text of the file
    private String fileData;

    /**
     * Constructs a FileParser object with the provided file name.
     *
     * @param fileName - The name of the file to be parsed
     */
    public FileParser(String fileName) {
        this.fileName = fileName;
        this.fileData = "";
    }

    /**
     * Reads the data from the file and stores it into fileData variable
     *
     * @return - Status of read operation
     */
    public boolean readFromFile() {
        try {

            // create an object of File and BufferedReader to read text from the file
            File inputFile = new File(this.fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

            StringBuilder stringBuilder = new StringBuilder();
            String line = "";

            while (line != null) {
                // Read a line
                line = bufferedReader.readLine();
                if (line != null) {
                    // Append line to StringBuilder
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
            }
            // Convert StringBuilder to string and stores it fileData variable
            this.fileData = stringBuilder.toString();

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract the TITLE and BODY tag from REUTERS tag
     *
     * @return - List of all News
     */
    public List<News> getNews() {
        String regex = "<REUTERS.*?>(.*?)</REUTERS>";

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(this.fileData);

        List<News> newsList = new ArrayList<>();

        while (matcher.find()) {

            // Need to group with value of 1, that remove REUTERS start and end tag
            String tag = matcher.group(1);

            // Get the data between TITLE tag
            String title = getTagData(tag, "TITLE");

            // Get the data between BODY tag
            String body = getTagData(tag, "BODY");

            newsList.add(new News(title, body));
        }

        return newsList;
    }


    /**
     * Returns the text between given tag after removing some special characters
     *
     * @param reuter - reuter tag from which we need to extract given tag
     * @param tag    - the tag which we need to extract
     * @return - The text between given tag
     */
    public String getTagData(String reuter, String tag) {
        String regex = "<" + tag + ">([\\s\\S]*?)</" + tag + ">";

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(reuter);

        if (matcher.find()) {
            String title = matcher.group(1);

            // Remove special characters
            title = title.replaceAll("&lt;", "")
                    .replaceAll("<", "")
                    .replaceAll("&gt;", "")
                    .replaceAll(">", "")
                    .replaceAll("&#3;", "")
                    .replaceAll("Reuter$", "");
            return title;
        }
        return "";
    }

    /**
     * Splits the file data and list of words
     *
     * @return - List of words extracted from the file data
     */
    public List<String> splitFileData() {
        List<String> words = new ArrayList<>();

        String regex = "\\b\\w+([*-]+\\w+)*\\b";

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(this.fileData);

        while (matcher.find()) {
            words.add(matcher.group());
        }

        return words;
    }

}
