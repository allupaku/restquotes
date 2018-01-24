package com.althaf.search.core;

import com.althaf.search.api.Quote;
import com.althaf.search.api.SearchResponse;
import com.althaf.search.core.ds.Trie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Class to search the trie for a blob of text passed in.
 */
public class TrieSearcherTest {

    Trie testTrie;

    TrieSearcher searchTrie;
    HashMap<Integer,Quote> quotemap;

    @Before
    public void setUp() throws Exception {
        this.testTrie = new Trie();
        this.quotemap = new HashMap<>();
        // 20 quotes are added to be searched for
        // this should make 10 words at the first level
        for(int i = 1; i <= 20;i++){
            Quote tQuote = new Quote((i / 2)+ " Amazing " + i +  " Quote q" + i,"Anonymous Anuthor"+i);
            quotemap.put(i,tQuote);
            this.testTrie.addQuote(tQuote);
        }

        this.searchTrie = new TrieSearcher(this.testTrie);
    }

    @Test
    public void searchQuoteInBeginning(){

        SearchResponse response = this.searchTrie.searchForQuotesInText(this.quotemap.get(4).getQuote() + " ending random text");

        Assert.assertEquals("Test the quote at beginning of the string",response.getFoundQuotes().size(),1);

        Assert.assertTrue("test the number of quotes found with one quote",response.getFoundQuotes().containsKey(0));

    }
    @Test
    public void searchQuoteInEnd(){

        SearchResponse response = this.searchTrie.searchForQuotesInText("Begin random text "+this.quotemap.get(2).getQuote() );

        Assert.assertEquals("Test the quote at end is found",response.getFoundQuotes().size(),1);
    }

    @Test
    public void searchQuoteInMiddle(){

        SearchResponse response = this.searchTrie.searchForQuotesInText("Begin random text "+this.quotemap.get(8).getQuote() + " Random ending string " );

        Assert.assertEquals("Found the result middle quotes",response.getFoundQuotes().size(),1);
    }

    @Test
    public void searchMultipleQuotesInText(){

        String searchText = " Random Beginning ";

        for(Quote q: this.quotemap.values()){

            searchText += q.getQuote() + " random ness divided ";
        }

        SearchResponse response = this.searchTrie.searchForQuotesInText(searchText);

        Assert.assertEquals("Found all quotes in search text ", response.getFoundQuotes().size(), this.quotemap.size());
    }

    @Test
    public void searchOverlappingQuotes(){

        String searchText = this.quotemap.get(1).getQuote()+"MADEINVALID "+ this.quotemap.get(10).getQuote();

        SearchResponse response = this.searchTrie.searchForQuotesInText(searchText);

        Assert.assertEquals("Excluded the quote which overlapped with invalid characters", response.getFoundQuotes().size(),1);
    }

    @Test
    public void searchIncompleteQuote(){

        String searchText = this.quotemap.get(1).getQuote().substring(0,4) +" MADEINVALID "+ this.quotemap.get(3).getQuote().substring(0,5);

        SearchResponse response = this.searchTrie.searchForQuotesInText(searchText);

        Assert.assertEquals("Excluded the quotes which are not complete", response.getFoundQuotes().size(),0);
    }


}
