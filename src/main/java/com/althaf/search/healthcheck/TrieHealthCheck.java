package com.althaf.search.healthcheck;

import com.althaf.search.core.ds.Trie;
import com.codahale.metrics.health.HealthCheck;

public class TrieHealthCheck extends HealthCheck {

    Trie quoteTrie;

    public TrieHealthCheck(Trie qtrie){
        this.quoteTrie = qtrie;
    }

    @Override
    protected Result check() throws Exception {

        if(this.quoteTrie.getNumberofChildrens() > 0){

            return Result.healthy();
        }else{
            return Result.unhealthy("No quotes to search for");
        }

    }
}
