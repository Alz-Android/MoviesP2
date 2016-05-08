package com.example.al.moviesp1.ContentProvider;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Al on 2016-05-06.
 */

@SimpleSQLConfig(
        name = "TrailerProvider",
        authority = "just.some.test_provider.authority1",
        database = "Trailer.db",
        version = 1)
public class TrailerProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}

