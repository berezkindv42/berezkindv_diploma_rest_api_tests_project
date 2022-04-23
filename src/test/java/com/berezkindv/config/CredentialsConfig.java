package com.berezkindv.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/credentials.properties"
})
public interface CredentialsConfig extends Config {

    @Key("create.user.name")
    String createUserName();

    @Key("create.user.job")
    String createUserJob();

    @Key("update.user.job")
    String updateUserJob();

    @Key("register.user.email")
    String registerUserEmail();

    @Key("register.user.password")
    String registerUserPassword();

    @Key("login.user.password")
    String loginUserPassword();

}