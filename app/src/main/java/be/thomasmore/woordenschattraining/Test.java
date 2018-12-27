package be.thomasmore.woordenschattraining;

import java.util.Date;

public class Test {

    private long id;
    private long kindId;
    private int conditie;
    private String datum;

    public Test() {
    }

    public Test(long id, long kindId, int conditie, String datum) {
        this.id = id;
        this.kindId = kindId;
        this.conditie = conditie;
        this.datum = datum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getKindId() {
        return kindId;
    }

    public void setKindId(long kindId) {
        this.kindId = kindId;
    }

    public int getConditie() {
        return conditie;
    }

    public void setConditie(int conditie) {
        this.conditie = conditie;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
