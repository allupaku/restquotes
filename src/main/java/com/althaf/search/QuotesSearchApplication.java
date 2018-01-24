package com.althaf.search;
import com.althaf.search.core.ds.Trie;
import com.althaf.search.healthcheck.TrieHealthCheck;
import com.althaf.search.resources.QuotesResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.swagger.jaxrs.config.BeanConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class QuotesSearchApplication extends Application<QuotesSearchConfiguration> {

    private static final Logger LOGGER =LoggerFactory.getLogger(QuotesSearchApplication.class);

//    @Valid
//    @NotNull
//    @JsonProperty
//    private String swaggerBasePath;
//    public String getSwaggerBasePath(){ return swaggerBasePath; }

    public static void main(final String[] args) throws Exception {
        new QuotesSearchApplication().run(args);
    }

    @Override
    public String getName() {
        return "QuotesSearch";
    }

    @Override
    public void initialize(final Bootstrap<QuotesSearchConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<QuotesSearchConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(QuotesSearchConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final QuotesSearchConfiguration configuration,
                    final Environment environment) {

        Trie defaultTrie = configuration.getBootstrapData().buildTrieFromInput();

        environment.jersey().register(new QuotesResource(defaultTrie));

        environment.healthChecks().register("TrieHealth", new TrieHealthCheck(defaultTrie) );


    }
}
