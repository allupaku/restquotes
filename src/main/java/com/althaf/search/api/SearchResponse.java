package com.althaf.search.api;
import com.althaf.search.api.Quote;
import com.althaf.search.api.utils.SearchResponseDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the class representing the response of search result.
 */
@JsonSerialize(using=SearchResponseDeserializer.class)
public class SearchResponse{

    Map<Integer,Quote> foundQuotes;

    public SearchResponse(){
        this.foundQuotes = new HashMap<>();
    }

    public SearchResponse(Map<Integer, Quote> fquotes){
        this.foundQuotes = fquotes;
    }

    public Map<Integer, Quote> getFoundQuotes() {
        return foundQuotes;
    }

    public void addQuote(int pos, Quote qte){

        this.foundQuotes.put(pos,qte);
    }


}
