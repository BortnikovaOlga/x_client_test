package ext.prop;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${env}.properties"})
public interface DatabaseConfig extends Config {
    @Key("hibernate.connection.driver_class")
    String hibernateConnectionDriverClass();

    @Key("hibernate.connection.url")
    String hibernateConnectionUrl();

    @Key("hibernate.connection.username")
    String hibernateConnectionUsername();

    @Key("hibernate.connection.password")
    String hibernateConnectionPassword();

    @Key("hibernate.dialect")
    String hibernateDialect();

    @Key("hibernate.show_sql")
    String hibernateShowSql();

    @Key("hibernate.format_sql")
    String hibernateFormatSql();

    @Key("hibernate.connection.autocommit")
    String hibernateConnectionAutocommit();

    @Key("hibernate.hbm2ddl.auto")
    String hibernateHbm2ddlAuto();

//    hibernate.connection.driver_class=org.postgresql.Driver
//    hibernate.connection.url=jdbc:postgresql://dpg-cj94hf0eba7s73bdki80-a.oregon-postgres.render.com/x_clients_db_r06g
//    hibernate.connection.username=x_clients_db_r06g_user
//    hibernate.connection.password=0R1RNWXMepS7mrvcKRThRi82GtJ2Ob58
//    hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
//    hibernate.show_sql=true
//    hibernate.format_sql=true
//    hibernate.connection.autocommit=true
//    hibernate.hbm2ddl.auto=validate
}