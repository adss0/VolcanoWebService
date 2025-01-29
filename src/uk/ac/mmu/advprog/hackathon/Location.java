package uk.ac.mmu.advprog.hackathon;

class Location {
	double latitude;
	double longitude;
	int elevation;
	String country;

	// Constructors
	Location() {

	}

	Location(double latitude, double longitude, int elevation, String country) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.country = country;
	}

	// Getters and setters
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
