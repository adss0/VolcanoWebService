package uk.ac.mmu.advprog.hackathon;

import static spark.Spark.get;
import static spark.Spark.port;

import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;

import org.json.JSONArray;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles the setting up and starting of the web service You will be adding
 * additional routes to this class, and it might get quite large Feel free to
 * distribute some of the work to additional child classes, like I did with DB
 * 
 * @author You, Mainly!
 */
public class VolcanoWebService {

	/**
	 * Main program entry point, starts the web service
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		port(8088);

		// check the web service is working by loading http://localhost:8088/test in
		// your browser
		get("/test", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					return "Number of volcanoes: " + db.getNumberOfVolcanoes() + "<br>" + "Number of eruptions: "
							+ db.getNumberOfEruptions();
				}
			}
		});
		// Get the number of volcanos located in a specific country
		get("/country", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					String search = request.queryParams("search");

					// Check if the search query is valid
					if (search == null || search.isEmpty() ||!search.matches("[a-zA-Z +]+")) {
						return "Invalid Country";
					} else {
						// if the search query is valid, get the number of volcanoes by the country
						String refinedSearchString = java.net.URLDecoder.decode(search, StandardCharsets.UTF_8.name());
						return db.getNumberOfVolcanoesInACountry(refinedSearchString);
					}
				}
			}

		});
		// Get Eruption details from a year rage
		get("/year", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					int from = Integer.parseInt(request.queryParams("from"));
					int to = Integer.parseInt(request.queryParams("to"));

					// Validate if the year input
					int year = Year.now().getValue();
					if (from > to || from < 0 || to < 0 || to > year) {
						return "INVALID YEAR INPUT";
					} else {
						// if the year is valid, get the details of the eruptions
						response.type("application/json");
						return new JsonConverter().format(db.getEruptionsInYearRange(from, to));
					}
				}
			}
		});

		// Search by Location and Eruption date
		get("/location", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					// Store it initially as String for validation
					String latitudeString = request.queryParams("latitude");
					String longitudeString = request.queryParams("longitude");
					String eruptedSince = request.queryParams("erupted_since");

					// Validation to see if the query's contain any special or unwanted characters
					if (latitudeString == null || !latitudeString.matches("[-+]?([1-8]?[0-9]|90)(\\.[0-9]+)?")) {
						return "Invalid latitude";
					} else if (longitudeString == null
							|| !longitudeString.matches("[-+]?([1-8]?[0-9]|90)(\\.[0-9]+)?")) {
						return "Invalid longitude";
					} else if (eruptedSince == null || !eruptedSince.matches("-?\\d+")) {
						return "Invalid Year";
					} else {
						// if the query's are valid, get the Eruption data

						// Parse the strings to pass it to the SQL Query
						double latitude = Double.parseDouble(latitudeString);
						double longitude = Double.parseDouble(longitudeString);

						response.type("application/xml");
						return new xmlConverter()
								.format(db.getVolcanoesByLocationAndLastEruption(latitude, longitude, eruptedSince));
					}
				}
			}
		});

		System.out.println("Web Service Started!");
	}

}
