package es.iessaladillo.pedrojoya.pr167.bd;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

@SimpleSQLConfig(
        name = Instituto.PROVIDER_NAME,
        authority = Instituto.PROVIDER_AUTHORITY,
        database = Instituto.BD_NOMBRE,
        version = Instituto.BD_VERSION)
public class InstitutoProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}