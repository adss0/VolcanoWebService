package uk.ac.mmu.advprog.hackathon;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class to convert the data into JSON
 */
class JsonConverter {

	/**
	 * @param eruptionList
	 * @return A Json Array as a String
	 */
	public String format(List<Eruption> eruptionList) {
		// JSONArray to hold the JSON objects
		JSONArray jsonArray = new JSONArray();
		
		//Loop through the eruption List to get the JSONObjects
		for (Eruption eruption : eruptionList) {

			// JSONObject to hold the information of the eruption
			JSONObject eruptionObject = new JSONObject();
			eruptionObject.put("date", eruption.getEruptionDate());
			eruptionObject.put("name", eruption.getName());

			// JSONObject to hold the information of location of Eruption
			JSONObject locationObject = new JSONObject();
			locationObject.put("latitude", eruption.getLocation().getLatitude());
			locationObject.put("longitude", eruption.getLocation().getLongitude());
			locationObject.put("elevation", eruption.getLocation().getElevation());
			locationObject.put("country", eruption.getLocation().getCountry());
			// Add the location to the eruption Object
			eruptionObject.put("location", locationObject);

			eruptionObject.put("deaths", eruption.getDeaths());
			eruptionObject.put("missing", eruption.getMissing());
			eruptionObject.put("injuries", eruption.getInjuries());

			// Add the eruptionObject to the Array
			jsonArray.put(eruptionObject);
		}

		// Return the JSON array as a string
		return jsonArray.toString();
	}
}
