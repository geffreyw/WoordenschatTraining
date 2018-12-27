package be.thomasmore.woordenschattraining;

public class GetestWoord {

    private long id;
    private long testId;
    private String oefening;
    private String woord;
    private boolean antwoord;

    public GetestWoord(){
    }

    public GetestWoord(long id, long testId, String oefening, String woord, boolean antwoord) {
        this.id = id;
        this.testId = testId;
        this.oefening = oefening;
        this.woord = woord;
        this.antwoord = antwoord;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getOefening() {
        return oefening;
    }

    public void setOefening(String oefening) {
        this.oefening = oefening;
    }

    public String getWoord() {
        return woord;
    }

    public void setWoord(String woord) {
        this.woord = woord;
    }

    public boolean getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(boolean antwoord) {
        this.antwoord = antwoord;
    }


    @Override
    public String toString () {
         return oefening + " " + woord + " = " + antwoord;
    }
}
