package uk.ac.mmu.advprog.hackathon;

import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

import java.time.Year;

/**
 * The VolcanoWebService class is the main entry point of the application. 
 * It defines all the routes and endpoints to interact with the database and 
 * provide information about volcanoes and eruptions.
 */
public class VolcanoWebService {

    public static void main(String[] args) {
        port(8088);

        // Route to test the service
        get("/test", (Request request, Response response) -> {
            try (DB db = new DB()) {
                return "Number of volcanoes: " + db.getNumberOfVolcanoes() + "<br>" +
                       "Number of eruptions: " + db.getNumberOfEruptions();
            }
        });

        // Route to get volcanoes by country
        get("/country", (Request request, Response response) -> {
            try (DB db = new DB()) {
                String search = request.queryParams("search");
                if (search == null || search.isEmpty() || !search.matches("[a-zA-Z ]+")) {
                    return "Invalid Country";
                } else {
                    String refinedSearchString = java.net.URLDecoder.decode(search, "UTF-8");
                    return db.getNumberOfVolcanoesInACountry(refinedSearchString);
                }
            }
        });

        // Route to get eruptions by year range
        get("/year", (Request request, Response response) -> {
            try (DB db = new DB()) {
                int from = Integer.parseInt(request.queryParams("from"));
                int to = Integer.parseInt(request.queryParams("to"));

                if (from > to || from < 0 || to < 0 || to > Year.now().getValue()) {
                    return "INVALID YEAR INPUT";
                } else {
                    response.type("application/json");
                    return new JsonConverter().format(db.getEruptionsInYearRange(from, to));
                }
            }
        });

     // Route to get volcanoes by location and last eruption date
        get("/location", (Request request, Response response) -> {
            try (DB db = new DB()) {
                String latitudeString = request.queryParams("latitude");
                String longitudeString = request.queryParams("longitude");
                String eruptedSince = request.queryParams("erupted_since");

                // Validate latitude
                if (latitudeString == null || !latitudeString.matches("[-+]?((1[0-7][0-9]|180|[1-9]?[0-9])(\\.[0-9]+)?)"
)) {
                    return "Invalid latitude";
                }
                if (longitudeString == null || !longitudeString.matches("[-+]?((1[0-7][0-9]|180|[1-9]?[0-9])(\\.[0-9]+)?)")) {
                    return "Invalid longitude";
                }

                // Validate eruptedSince as a year
                if (eruptedSince == null || !eruptedSince.matches("\\d+")) {
                    return "Invalid Year";
                }

                double latitude = Double.parseDouble(latitudeString);
                double longitude = Double.parseDouble(longitudeString);

                response.type("application/xml");
                response.header("Content-Type", "application/xml"); // optional redundancy
                return new xmlConverter().format(
                    db.getVolcanoesByLocationAndLastEruption(latitude, longitude, eruptedSince)
                );
            }
        });


    }
}
