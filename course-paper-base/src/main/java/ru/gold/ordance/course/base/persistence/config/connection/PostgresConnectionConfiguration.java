package ru.gold.ordance.course.base.persistence.config.connection;

public class PostgresConnectionConfiguration implements DbConnectionConfiguration {
    private final String url;
    private final String username;
    private final String password;

    public PostgresConnectionConfiguration(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }
}
