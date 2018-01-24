package com.althaf.search.core;

import com.althaf.search.api.Quote;
import com.althaf.search.api.SearchResponse;
import com.althaf.search.core.ds.Node;
import com.althaf.search.core.ds.Trie;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import java.util.Iterator;
import java.util.List;

/**
 * This is a utility class used to search in a particular trie.
 */
public class TrieSearcher {

    protected Trie quotesTrie; // local varaible to hold the trie to be searched for.
    /**
     * Constructor with trie parameter.
     * @param trie - the trie to use to search for,
     */
    public TrieSearcher(Trie trie){

        this.quotesTrie = trie;
    }

    /**
     *
     * @param inputText text to be searched in.
     * @return SearchResponse.
     */
    public SearchResponse searchForQuotesInText(String inputText){
        // Input words as list, after splitting.
        List<String> words = this.splitInput(inputText);
        // Temp variable storing the word considered
        Node validWord = null;
        // storing the word count.
        int position = 0;
        // store the starting word count
        int start_pos = 0;
        // to store and build the response, which has to be returned
        SearchResponse sresponse = new SearchResponse();
        // buffer to build the quote back.
        StringBuffer quote_buffer = new StringBuffer();
        // Iterator to iterate through the string.
        Iterator<String> iter = words.iterator();

        while(iter.hasNext()){

            String cword = ""; // temporary variable to store the word to be considered.
            // loop till we find the valid beginning of a quote
            while(validWord == null && iter.hasNext()){
                cword = iter.next(); // word to consider for the beginning of the quote
                // store the position as the starting position.
                start_pos = position;
                // Check whether this is a valid beginning word
                validWord = this.quotesTrie.getChildWord(cword);
                position += 1; // increment the word count

                if(validWord != null) {
                    quote_buffer.setLength(0);
                    break;
                }
            }

            if(validWord == null) continue; // Should reach here when the string ends without a match.
            // Add the word to the buffer - this usually will be to add the first word in quote.
            quote_buffer.append(validWord.getKey() + " ");

            position +=  1; // increment the word count
            // Loop till we have the quote end or an invalid child, or end of the input text.
            while(validWord!=null && !validWord.isQuoteEnd() && iter.hasNext()){

                cword  = iter.next();                   // next word to consider
                validWord = validWord.getChild(cword);  // check whether it is a valid child or not.
                // If not valid word, then this loop should exit.
                if(validWord == null) {
                    break;
                }
                // Add the current word to the resulting quote buffer.
                quote_buffer.append(validWord.getKey() + " ");
                position += 1; // increment the word count
            }

            if(validWord != null && validWord.isQuoteEnd()){ // should reach here when the QuoteEnd reached.
                // build the response
                sresponse.addQuote(start_pos, new Quote(quote_buffer.toString(),validWord.getQuoteAuthor()));
                validWord = null;
            }
            // If we dont have valid word here, it means that we have a quote ended in this iteration.
            // hence reset the required fields and test for the current word in the biginning.
            if(validWord == null){
                validWord = this.quotesTrie.getChildWord(cword);
                quote_buffer.setLength(0);
            }

        }
        return sresponse;

    }

    /**
     * Split the input into words, avoiding all special characters.
     * @param input the input text to be split
     * @return list of words after splitting.
     */
    protected List<String> splitInput(String input){

        return Splitter.on(CharMatcher.whitespace())
                .omitEmptyStrings()
                .trimResults(CharMatcher.anyOf(" ,.:"))
                .splitToList(input);
    }
}
