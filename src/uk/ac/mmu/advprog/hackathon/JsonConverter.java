package uk.ac.mmu.advprog.hackathon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Converts eruption data into JSON format.
 */
public class JsonConverter {

    /**
     * Converts a list of eruptions into a JSON string.
     * 
     * @param eruptionList The list of eruptions to be converted into JSON.
     * @return The JSON string representation of the eruptions.
     */
    public String format(List<Eruption> eruptionList) {
        JSONArray jsonArray = new JSONArray();

        // Loop through the eruptionList and create JSON objects for each eruption
        for (Eruption eruption : eruptionList) {
            JSONObject eruptionObject = new JSONObject();
            eruptionObject.put("date", eruption.getEruptionDate());
            eruptionObject.put("name", eruption.getName());

            // Create the location JSON object
            JSONObject locationObject = new JSONObject();
            locationObject.put("latitude", eruption.getLocation().getLatitude());
            locationObject.put("longitude", eruption.getLocation().getLongitude());
            locationObject.put("elevation", eruption.getLocation().getElevation());
            locationObject.put("country", eruption.getLocation().getCountry());

            eruptionObject.put("location", locationObject);
            eruptionObject.put("deaths", eruption.getDeaths());
            eruptionObject.put("missing", eruption.getMissing());
            eruptionObject.put("injuries", eruption.getInjuries());

            // Add the eruption object to the JSON array
            jsonArray.put(eruptionObject);
        }

        // Return the JSON array as a string
        return jsonArray.toString();
    }
}
