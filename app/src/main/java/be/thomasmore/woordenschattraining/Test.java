package be.thomasmore.woordenschattraining;

import java.util.Date;

public class Test {

    private long id;
    private long kindId;
    private int score;
    private String datum;

    public Test() {
    }

    public Test(long id, long kindId, int score, String datum) {
        this.id = id;
        this.kindId = kindId;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
