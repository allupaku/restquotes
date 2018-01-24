package com.althaf.search.core.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic node to hold the words and children relationship.
 */
public class Node {

    // Logger for any exception or debug statement
    private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);

    protected String myKey;

    protected boolean isQuoteEnd = false; // did the quote end with this word ?

    protected String QuoteAuthor; // Quote author - will not be empty if the word is an end word.

    protected Map<String,Node> children; // child words.

    /**
     * Constructor with the key as the word.
     * @param key - the representing word.
     */
    public Node(String key){

        this.myKey = key;
        // initialize and emptu hash map.
        children = new HashMap<>();
    }

    /**
     * Constructor to create an end node
     * @param key the key word
     * @param author - the author if it is and end word
     *
     */
    public Node(String key, String author){

        this(key);

        this.QuoteAuthor = author;

        this.isQuoteEnd = true; // make the quote end.
    }

    /**
     * setting the quote end.
     */
    public void setQuoteEnd(){
        this.isQuoteEnd = true;
    }

    /**
     * Getter method for quote end
     * @return true if quote end.
     */
    public boolean isQuoteEnd(){
        return this.isQuoteEnd;
    }

    /**
     * Getter method for the key.
     * @return the word as string.
     */
    public String getKey(){
        return this.myKey;
    }

    /**
     * Setter method for the author.
     * this will also make this word/node as an end word.
     * @param author the author of the quote.
     */
    public void setQuoteAuthor( String author){
        this.QuoteAuthor = author;
        this.isQuoteEnd = true;
    }

    /**
     * Getter for quote author.
     * @return the author name
     */
    public String getQuoteAuthor(){
        return this.QuoteAuthor;
    }

    /**
     * Get the childrens length
     * @return length of the children
     */
    public int getChildrenLength(){
        return this.children.size();
    }


    /**
     * Method to add a child, if not exists.
     * @param childKey the child key to look for
     * @return return the node found or addded.
     */
    public Node addOrGetChild(String childKey){

        if(childKey==null || childKey.equals("")){
            return null;
        }

        Node child = this.children.get(childKey);
        if(child == null){
            try {
                child = new Node(childKey);
            }catch(Exception e){
                LOGGER.debug( e.getMessage() );
                return null;
            }
            this.children.put(childKey, child);
        }
        return child;
    }

    /**
     * Method to add a child if not exists with the author.
     * It sets the author, if it exists.
     * @param childKey the child key to look for.
     * @param author the authot to set.
     * @return the node found or added if not found.
     */
    public Node addOrGetChild(String childKey,String author){

        Node child = this.addOrGetChild(childKey);

        if(child !=null && author!=null && !author.equals(""))
            child.setQuoteAuthor(author);

        return child;
    }

    /**
     * Method to get the child with the said key.
     * @param childKey child key to look for
     * @return the found Node.
     */
    public Node getChild(String childKey){

        if(!this.children.containsKey(childKey)){
            return null;
        }
        return this.children.get(childKey);
    }

    /**
     * Check whether this node have a child with the key passed in.
     * @param childKey child key to look for
     * @return boolean whether it has the child or not.
     */
    public boolean hasChild(String childKey){

        return this.children.containsKey(childKey);
    }
}
