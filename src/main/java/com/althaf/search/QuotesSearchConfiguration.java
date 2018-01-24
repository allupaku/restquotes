package com.althaf.search;

import com.althaf.search.core.InputDataManager;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.constraints.*;

public class QuotesSearchConfiguration extends Configuration {


    @NotNull
    InputDataManager bootstrapData = new InputDataManager();

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty
    public InputDataManager getBootstrapData(){

        return this.bootstrapData;
    }
    @JsonProperty
    public void setBootstrapData(InputDataManager inputData){
        this.bootstrapData = inputData;
    }

}
