package org.example;

import java.util.List;

/**
 * The Tuple class represents an object that stores sentiment analysis results for a news title.
 * It contains the score calculated based on matched positive and negative words, the polarity
 * indicating whether the sentiment is positive, negative, or neutral, the list of matched words, and the title of the news.
 */

public class Tuple {

    // Score representing the sentiment analysis result
    private int score;

    // Polarity indicating whether sentiment is positive, negative, or neutral
    private Polarity polarity;

    // List of words matched in the news title during sentiment analysis
    private List<String> matchedWords;

    // Title of the news
    private String title;

    /**
     * Constructs a Tuple object with the provided score, title, and list of matched words.
     *
     * @param score        - The sentiment score calculated for the news title
     * @param title        - The title of the news
     * @param matchedWords - The list of words matched in the news title
     */
    public Tuple(int score, String title, List<String> matchedWords) {
        this.score = score;
        this.matchedWords = matchedWords;
        this.title = title;
    }

    /**
     * Updates the polarity of the Tuple based on the score.
     * If score is negative, polarity is set to NEGATIVE.
     * If score is positive, polarity is set to POSITIVE.
     * If score is zero, polarity is set to NEUTRAL.
     */
    public void updatePolarity() {
        if (this.score < 0) {
            this.polarity = Polarity.NEGATIVE;
        } else if (this.score > 0) {
            this.polarity = Polarity.POSITIVE;
        } else {
            this.polarity = Polarity.NEUTRAL;
        }
    }

    /**
     * Gets the sentiment score of the Tuple.
     *
     * @return - The sentiment score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the list of matched words in the news title.
     *
     * @return - The list of matched words
     */
    public List<String> getMatchedWords() {
        return this.matchedWords;
    }

    /**
     * Gets the polarity of the sentiment analysis result.
     *
     * @return The polarity (POSITIVE, NEGATIVE, or NEUTRAL)
     */
    public Polarity getPolarity() {
        return polarity;
    }

    /**
     * Gets the title of the news.
     *
     * @return - The title of the news
     */
    public String getTitle() {
        return title;
    }

}
