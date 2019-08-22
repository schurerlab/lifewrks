package edu.miami.ccs.life.gui;

/**
 *
 */

public class Index {

    private String query;

    private String label;

    private int level;

    private String ns;

    private int dbId;

    public Index(String label, String query, String ns, int level) {
        this.query = query;
        this.label = label;
        this.level = level;
        this.ns = ns;
    }

    public Index(String label, String query, int level) {
        this(label, query, "", level);
    }

    @Override
    public String toString() {
        return label;
    }

    public String getQuery() {
        String tmp = query != null ? query : "\n";
        return ns + tmp;
    }

    public int getLevel() {
        return level;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getLabel() {
        return label;
    }
}
