package uk.ac.mmu.advprog.hackathon;

class Eruption {

	String eruptionDate;
     String name;
     int deaths ;
     int missing;
     int injuries;
     String lastErupted;
	 int volcanoID ;
     String type;
	Location location;
	//Constructors 
	Eruption() {
		
	}
    
     Eruption(Location location, String eruptionDate, String name, int deaths, int missing, int injuries){
    	 this.location=location;
    	 this.eruptionDate= eruptionDate;
    	 this.name= name;
    	 this.deaths=deaths;
    	 this.missing=missing;
    	 this.injuries=injuries;
     }
     
     //Getters and Setters 
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
