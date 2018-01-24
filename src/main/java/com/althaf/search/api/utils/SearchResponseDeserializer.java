package com.althaf.search.api.utils;

import com.althaf.search.api.Quote;
import com.althaf.search.api.SearchResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

/**
 * Class used for serializing the response class as json
 */
public class SearchResponseDeserializer extends JsonSerializer<SearchResponse> {

    @Override
    public void serialize(SearchResponse searchResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeArrayFieldStart("matchesFound");

        for(Map.Entry<Integer,Quote> q: searchResponse.getFoundQuotes().entrySet()){

            jsonGenerator.writeStartObject();

            jsonGenerator.writeStringField("position",q.getKey().toString());

            jsonGenerator.writeStringField("quote",q.getValue().getQuote() + " : " + q.getValue().getAuthor() );

            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();

    }
}
