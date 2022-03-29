package searchEngineApp.Domain;

public class Lemma {
    private int id;
    private String lemmasName;
    private int frequency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLemmasName() {
        return lemmasName;
    }

    public void setLemmasName(String lemmasName) {
        this.lemmasName = lemmasName;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
