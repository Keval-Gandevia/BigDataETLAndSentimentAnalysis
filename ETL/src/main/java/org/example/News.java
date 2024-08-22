package org.example;

/**
 * The News class defines the structure of data needed to be inserted into MongoDB.
 * It represents a news article with a title and a body.
 */
public class News {

    // Title of the news
    private String title;

    // Body of the news
    private String body;

    /**
     * Constructs a News object with the provided title and body.
     *
     * @param title - The title of the news article
     * @param body  - The body of the news article
     */
    public News(String title, String body) {
        this.title = title;
        this.body = body;
    }

    /**
     * Returns the title variable
     *
     * @return - Title variable
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the body variable
     *
     * @return - Body variable
     */
    public String getBody() {
        return this.body;
    }
}
