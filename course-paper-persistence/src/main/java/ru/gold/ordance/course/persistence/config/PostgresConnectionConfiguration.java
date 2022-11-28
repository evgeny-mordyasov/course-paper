package ru.gold.ordance.course.persistence.config;

public class PostgresConnectionConfiguration {
    private String url;
    private String username;
    private String password;

    public String url() {
        return url;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
