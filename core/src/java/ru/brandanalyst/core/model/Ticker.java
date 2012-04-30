package ru.brandanalyst.core.model;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:44 PM
 */
public enum Ticker {
    NEWS_REFERENCES("упоминания в новостях", 1);

    private final String name;
    private final long id;

    private Ticker(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public static Ticker getById(long id) {
        for (Ticker t : values()) {
            if (t.id == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("no tiker with id: " + id);
    }
}
