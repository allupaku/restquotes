package com.althaf.search.resources;

import com.althaf.search.api.Quote;
import com.althaf.search.api.SearchResponse;
import com.althaf.search.core.TrieSearcher;
import com.althaf.search.core.ds.Node;
import com.althaf.search.core.ds.Trie;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Iterator;
import java.util.List;

@Path("/quotes")
@Produces(MediaType.APPLICATION_JSON)
@Api(description = "Api to search for quotes in the text")
public class QuotesResource {

    Trie quotesTrie;

    public QuotesResource(Trie qtrie){

        this.quotesTrie = qtrie;
    }


    @POST
    @Path("/search")
    @Timed(name = "QuoteSearch")
    @Metered(name = "QuoteSearchMeter")
    @ApiOperation(value = "/search", notes = "Search for quotes in the provided text",response = SearchResponse.class, consumes = "application/x-www-form-urlencoded")
    public Response searchQuote(
            @ApiParam(value = "Text which need to be searched", required = true, type = "string")
            @FormParam("searchText") String searchText
    ) {

        TrieSearcher tsearch = new TrieSearcher(this.quotesTrie);

        SearchResponse finalresponse = tsearch.searchForQuotesInText(searchText);

        return Response.ok(finalresponse).build();
    }
}
