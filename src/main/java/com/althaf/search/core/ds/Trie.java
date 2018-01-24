package com.althaf.search.core.ds;

import com.althaf.search.api.Quote;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class representing a trie of words.
 */
public class Trie {

    private static final Logger LOGGER = LoggerFactory.getLogger(Trie.class);

    private Node head; // temp variable to store the head.

    private Node currentHead; // temp variable to store the current traversal.

    private int longestString = 0;

    /**
     * Constructor, where we assign a head with string *,
     * We are assuming that there will not be any quote with * in it.
     */
    public Trie(){

        currentHead = head = new Node("*");
    }

    /**
     * Add a quote from the Quote to the trie.
     * @param qte the quote to be added
     */
    public void addQuote(Quote qte){

        if(qte == null || qte.getQuote() == null || qte.getQuote().length() == 0){
            return;
        }

        List<String> wordsList = Splitter.on(CharMatcher.whitespace())
                .omitEmptyStrings()
                .trimResults(CharMatcher.anyOf(" .,|:"))
                .splitToList(qte.getQuote());

        Node curHead = head;

        // Add each wods in the quote as a new level in the trie.
        for(String w : wordsList){
            curHead = curHead.addOrGetChild(w);
        }

        if(wordsList.size() > longestString) longestString = wordsList.size();

        curHead.setQuoteEnd();
        curHead.setQuoteAuthor(qte.getAuthor());
    }

    /**
     * Get the head of the trie for traversal.
     * @return the head node
     */
    public Node getHead(){
        return this.head;
    }

    /**
     * Get the immediate number of childrens for the trie.
     * @return the number of children at the first level
     */
    public int getNumberofChildrens(){
        return this.head.getChildrenLength();
    }



    /**
     * Get the first level child word.
     * @param word the word to look for
     * @return the word, if found.
     */
    public Node getChildWord(String word){

        return this.currentHead.getChild(word);
    }
}
