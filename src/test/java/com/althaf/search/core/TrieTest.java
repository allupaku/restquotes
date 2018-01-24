package com.althaf.search.core;

import com.althaf.search.api.Quote;
import com.althaf.search.core.ds.Trie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TrieTest{

    protected Trie testTrie;

    @Before
    public void setUp() {
            this.testTrie = new Trie();
            this.testTrie.addQuote(new Quote("A test Quote", "Test Author"));
            this.testTrie.addQuote(new Quote("A test Quote2", "Test Author 2"));
    }

    /**
     * Test the basic tri building - this should never fail.
     */
    @Test
    public void testTrieBuilding() {

        Assert.assertEquals("2 Quotes Added with same beginning", this.testTrie.getNumberofChildrens(), 1);

        Assert.assertEquals("2 Quotes third word different beginning", this.testTrie.getHead().getChild("A").getChild("test").getChildrenLength(), 2);

    }

    /**
     *  Building with different beginning
     */
    @Test
    public void testTrieBuildingWithDifferentBeginning() {
        this.testTrie.addQuote(new Quote("B Quote", "New Author"));

        Assert.assertEquals("Quotes Added with two beginning words", this.testTrie.getNumberofChildrens(), 2);
    }

    /**
     * Testing the building with 3 different beginnings.
     */
    @Test
    public void testTrieBuildingWith3DifferentBeginning(){

        this.testTrie.addQuote(new Quote("B Quote", "New Author"));
        this.testTrie.addQuote(new Quote("C", "New Author"));
        Assert.assertEquals("3 Quotes with different words",this.testTrie.getNumberofChildrens(),3);
    }

    /**
     * Testing with null as the building quote.
     */
    @Test
    public void testTrieBuildingWithNullQuote(){

        this.testTrie.addQuote(null);

        Assert.assertEquals("With Null Quote",this.testTrie.getNumberofChildrens(),1);
    }

    /**
     * Test whether it will add a quote with empty or null string.
     */
    @Test
    public void testTrieBuildingWithNullQuoteText(){

        this.testTrie.addQuote(new Quote(null,"anonymous"));
        this.testTrie.addQuote(new Quote("","anonymous"));

        Assert.assertEquals("With Null Quote text and anonymous author",this.testTrie.getNumberofChildrens(),1);
    }

    /**
     * Test of building with empty author, but a valid quote.
     */
    @Test
    public void testTrieBuildingWithNullAuthor(){

        this.testTrie.addQuote(new Quote("TestAgain", null));

        Assert.assertEquals("With Null Quote",this.testTrie.getNumberofChildrens(),2);
    }
}
