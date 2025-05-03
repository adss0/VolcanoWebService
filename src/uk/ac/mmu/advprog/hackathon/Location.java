package uk.ac.mmu.advprog.hackathon;

/**
 * Represents the geographical location of a volcano.
 */
class Location {
    double latitude;
    double longitude;
    int elevation;
    String country;

    /**
     * Default constructor for Location.
     */
    Location() {

    }

    /**
     * Constructs a Location object with the specified latitude, longitude, elevation, and country.
     * 
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param elevation The elevation of the location.
     * @param country The country where the location is situated.
     */
    Location(double latitude, double longitude, int elevation, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.country = country;
    }

    // Getters and setters for the properties

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
