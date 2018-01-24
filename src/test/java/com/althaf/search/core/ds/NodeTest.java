package com.althaf.search.core.ds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {

    protected Node testNode;
    @Before
    public void setUp(){

        this.testNode = new Node("*");
    }

    /**
     * Testing the child is getting added.
     */
    @Test
    public void testAddingChildren(){
        this.testNode.addOrGetChild("A");
        Assert.assertNotNull("Child Added",this.testNode.getChild("A"));
        Assert.assertEquals("Length of the children",this.testNode.getChildrenLength(),1);
    }

    /**
     * test adding null value as the key.
     */
    @Test
    public void testAddingNullChildren(){
        this.testNode.addOrGetChild("");
        this.testNode.addOrGetChild(null);
        Assert.assertNull("Child should not be added with null / empty key",this.testNode.getChild(""));
        Assert.assertEquals("Length of the children should be zero",this.testNode.getChildrenLength(),0);
    }

    /**
     * test whether adding a copy is creating new child - it should not
     */
    @Test
    public void testAddingChildrenCopy(){
        this.testNode.addOrGetChild("A");
        this.testNode.addOrGetChild("A");
        this.testNode.addOrGetChild("B");
        Assert.assertNotNull("Child Added",this.testNode.getChild("A"));
        Assert.assertEquals("Length of the children",this.testNode.getChildrenLength(),2);
    }

    /**
     * test the nodes HasChild functionality
     */
    @Test
    public void testHasChild(){
        this.testNode.addOrGetChild("A");
        this.testNode.addOrGetChild("A");
        this.testNode.addOrGetChild("B");
        Assert.assertTrue("Has child",this.testNode.hasChild("A"));
        Assert.assertTrue("Has child",this.testNode.hasChild("B"));
    }

    /**
     * Test the node with author.
     */
    @Test
    public void testWithAuthor(){
        this.testNode.addOrGetChild("A");
        this.testNode.addOrGetChild("A","Author");
        this.testNode.addOrGetChild("B");
        Assert.assertTrue("Quote Ended",this.testNode.getChild("A").isQuoteEnd());
        Assert.assertFalse("Quote didnt end without author",this.testNode.getChild("B").isQuoteEnd());
    }




}
