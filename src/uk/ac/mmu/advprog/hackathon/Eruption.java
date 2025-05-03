package uk.ac.mmu.advprog.hackathon;

/**
 * Represents an eruption event associated with a volcano.
 */
class Eruption {

    String eruptionDate;
    String name;
    int deaths;
    int missing;
    int injuries;
    String lastErupted;
    int volcanoID;
    String type;
    Location location;

    /**
     * Default constructor for Eruption.
     */
    Eruption() {

    }

    /**
     * Constructs an Eruption object with the specified location, eruption date, name, deaths, missing, and injuries.
     * 
     * @param location The location of the eruption.
     * @param eruptionDate The date of the eruption.
     * @param name The name of the eruption.
     * @param deaths The number of deaths caused by the eruption.
     * @param missing The number of missing persons due to the eruption.
     * @param injuries The number of injuries caused by the eruption.
     */
    Eruption(Location location, String eruptionDate, String name, int deaths, int missing, int injuries) {
        this.location = location;
        this.eruptionDate = eruptionDate;
        this.name = name;
        this.deaths = deaths;
        this.missing = missing;
        this.injuries = injuries;
    }

    // Getters and setters for the properties

    public String getLastErupted() {
        return lastErupted;
    }

    public void setLastErupted(String lastErupted) {
        this.lastErupted = lastErupted;
    }

    public int getVolcanoID() {
        return volcanoID;
    }

    public void setVolcanoID(int volcanoID) {
        this.volcanoID = volcanoID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEruptionDate() {
        return eruptionDate;
    }

    public void setEruptionDate(String eruptionDate) {
        this.eruptionDate = eruptionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getMissing() {
        return missing;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    public int getInjuries() {
        return injuries;
    }

    public void setInjuries(int injuries) {
        this.injuries = injuries;
    }
}
