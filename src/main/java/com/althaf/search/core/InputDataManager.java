package com.althaf.search.core;
import com.althaf.search.api.Quote;
import com.althaf.search.core.ds.Trie;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Class to maange the importing of the data from files.
 */
public class InputDataManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputDataManager.class);
    Trie quotesTrie;

    @JsonProperty
    private List<String> inputs;

    public InputDataManager(){
        this.quotesTrie = new Trie();
    }

    /**
     * Constructor with a common trie
     * @param trieNode the trie node to use.
     */
    public InputDataManager(Trie trieNode){
        this.quotesTrie = trieNode;
    }

    /**
     * Get the inputs configured
     * @return list of inputs
     */
    public List<String> getInputs(){
        return this.inputs;
    }

    /**
     * Getting the trie build from input data.
     * @return the trie
     */
    public Trie getQuotesTrie(){
        return this.quotesTrie;
    }

    /**
     * Setting the trie where the data has to be added.
     * @param qtrie the trie to set
     */
    public void setQuotesTrie(Trie qtrie){
        this.quotesTrie = qtrie;
    }

    /**
     * Build trie from the input file.
     * @return the trie
     */
    public Trie buildTrieFromInput(){
        if(this.inputs !=null && !this.inputs.isEmpty()){
            for(String filename : this.inputs){
                this.readFromFile(filename);
            }
        }else{
            LOGGER.error("No input configured / invalid inputs");
        }
        return this.quotesTrie;
    }

    /**
     * Read the data from file line by line.
     * @param filename file name to parse / process
     */
    protected void readFromFile(String filename){
        String line;
        try {
            FileReader fileReader = new FileReader(filename);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int num_lines = 0;

            while((line = bufferedReader.readLine()) != null) {

                Quote q = getQuoteFromLine(line,filename);
                // If we are able to parse a valid quote from a line.
                if(q!=null){
                    this.quotesTrie.addQuote(q);
                    num_lines++;
                }else{
                    LOGGER.debug("Cannot parse properly from the line : " + line);
                }

            }

            LOGGER.debug("Added "+ num_lines + "Quotes");

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            LOGGER.error("Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            LOGGER.error("Error reading file '" + filename + "'");

        }
    }

    /**
     * Get / build a Quote from a line.
     * Expecting that the quote starts with author name and then quote
     * separated by tab.
     * @param line the line to parse
     * @param filename file name where the line is parsed.
     * @return the quote build from the line.
     */
    protected Quote getQuoteFromLine(String line, String filename){
        // Split the line with tab delimited characters.
        List<String> parts = Splitter.on(CharMatcher.is('\t')).trimResults().omitEmptyStrings().splitToList(line);

        Quote nQuote = null;
        // if there are two parts, or else ignore the quote.
        if(parts.size()>1){
            nQuote = new Quote(parts.get(1),parts.get(0));
        }else{
            LOGGER.error("Invalid line in the file " + line + " @ " + filename);
        }
        return nQuote;
    }

}
