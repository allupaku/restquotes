package com.althaf.search.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Structure class for Quote.
 */
public class Quote {

    String quote; // quote text

    String author; // author information

    /**
     * Constructor with quote and author.
     * @param quote the quote text
     * @param author the author
     */
    public Quote(String quote,String author){
        this.quote = quote;
        this.author = author;
    }

    @JsonProperty
    public String getQuote(){
        return this.quote;
    }

    @JsonProperty
    public String getAuthor(){
        return this.author;
    }

    @JsonProperty
    public void setQuote(String quote){
        this.quote = quote;
    }

    @JsonProperty
    public void setAuthor(String author){
        this.author = author;
    }

    @Override
    public String toString(){
        return this.quote + " by " + this.author;
    }
}
