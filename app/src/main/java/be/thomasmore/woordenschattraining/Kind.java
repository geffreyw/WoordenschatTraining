package be.thomasmore.woordenschattraining;

public class Kind {

    private long id;
    private String naam;
    private long groepId;

    public Kind(){
    }

    public Kind(long id, String naam, long groepId) {
        this.id = id;
        this.naam = naam;
        this.groepId = groepId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public long getGroepId() {
        return groepId;
    }

    public void setGroepId(long groepId) {
        this.groepId = groepId;
    }

    @Override
    public String toString () {
        return naam;
    }
}
