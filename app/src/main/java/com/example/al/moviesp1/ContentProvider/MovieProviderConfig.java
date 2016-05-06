package com.example.al.moviesp1.ContentProvider;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Al on 2016-05-02.
 */
@SimpleSQLConfig(
        name = "MovieProvider",
        authority = "just.some.test_provider.authority",
        database = "Movies.db",
        version = 1)
public class MovieProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
