package uk.ac.mmu.advprog.hackathon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The DB class is responsible for interacting with the SQLite database to retrieve data about volcanoes and eruptions.
 * It provides methods for querying the database and returning results such as the number of volcanoes, eruptions,
 * and eruption details by year range or geographical location.
 */
public class DB implements AutoCloseable {

    private static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:./data/volcanoes.db";
    private Connection connection = null;

    /**
     * Constructor to initialize the database connection.
     */
    public DB() {
        try {
            connection = DriverManager.getConnection(JDBC_CONNECTION_STRING);
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    /**
     * Returns the total number of volcanoes in the database.
     * @return The count of volcanoes.
     */
    public int getNumberOfVolcanoes() {
        int result = -1;
        String query = "SELECT COUNT(*) AS count FROM volcanoes";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet results = ps.executeQuery()) {
            if (results.next()) {
                result = results.getInt("count");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return result;
    }

    /**
     * Returns the total number of eruptions in the database.
     * @return The count of eruptions.
     */
    public int getNumberOfEruptions() {
        int result = -1;
        String query = "SELECT COUNT(*) AS count FROM eruptions";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet results = ps.executeQuery()) {
            if (results.next()) {
                result = results.getInt("count");
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return result;
    }

    /**
     * Returns the number of volcanoes in a specific country.
     * @param search The country name to search for.
     * @return The count of volcanoes in the given country.
     */
    public int getNumberOfVolcanoesInACountry(String search) {
        int result = 0;
        String query = "SELECT COUNT(*) AS Number FROM volcanoes WHERE Country = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, search);
            try (ResultSet results = ps.executeQuery()) {
                if (results.next()) {
                    result = results.getInt("Number");
                }
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return result;
    }

    /**
     * Returns a list of eruptions that occurred between a given year range.
     * @param from The start year.
     * @param to The end year.
     * @return A list of eruptions that occurred within the specified year range.
     */
    public List<Eruption> getEruptionsInYearRange(int from, int to) {
        List<Eruption> eruptionObjects = new ArrayList<>();
        String query = "SELECT * FROM Eruptions INNER JOIN Volcanoes ON Eruptions.Volcano_ID = Volcanoes.ID " +
                       "WHERE CAST(Eruptions.Date AS INTEGER) >= ? AND CAST(Eruptions.Date AS INTEGER) <= ? " +
                       "ORDER BY Eruptions.Date ASC";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, from);
            ps.setInt(2, to);
            try (ResultSet results = ps.executeQuery()) {
                while (results.next()) {
                    String eruptionDate = results.getString("Date");
                    String name = results.getString("Name");
                    double latitude = results.getDouble("Latitude");
                    double longitude = results.getDouble("Longitude");
                    int elevation = results.getInt("Elevation");
                    String country = results.getString("Country");
                    int deaths = results.getInt("Deaths");
                    int missing = results.getInt("Missing");
                    int injuries = results.getInt("Injuries");

                    Location location = new Location(latitude, longitude, elevation, country);
                    Eruption eruption = new Eruption(location, eruptionDate, name, deaths, missing, injuries);
                    eruptionObjects.add(eruption);
                }
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return eruptionObjects;
    }

    /**
     * Returns volcanoes based on geographical location and the last eruption date.
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param eruptedSince The year after which the volcanoes should have erupted.
     * @return A list of volcanoes by location and last eruption date.
     */
    public List<Eruption> getVolcanoesByLocationAndLastEruption(double latitude, double longitude, String eruptedSince) {
        List<Eruption> result = new ArrayList<>();
        String query = "SELECT MAX(Date) AS Last_Erupted, Volcano_ID, Name, Country, Latitude, Longitude, Elevation, Type " +
                       "FROM Eruptions INNER JOIN Volcanoes ON Eruptions.Volcano_ID = Volcanoes.ID " +
                       "WHERE CAST(Date AS INTEGER) >= 1 GROUP BY Volcano_ID " +
                       "ORDER BY(((? - Latitude) * (? - Latitude)) + ((? - Longitude) * (? - Longitude))) ASC LIMIT 10";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, latitude);
            ps.setDouble(2, latitude);
            ps.setDouble(3, longitude);
            ps.setDouble(4, longitude);

            try (ResultSet results = ps.executeQuery()) {
                while (results.next()) {
                    String name = results.getString("Name");
                    String lastErupted = results.getString("Last_Erupted");
                    int volcanoID = results.getInt("Volcano_ID");
                    double lat = results.getDouble("Latitude");
                    double lon = results.getDouble("Longitude");
                    int elevation = results.getInt("Elevation");
                    String type = results.getString("Type");
                    String country = results.getString("Country");

                    Location location = new Location(lat, lon, elevation, country);
                    Eruption eruption = new Eruption();
                    eruption.setVolcanoID(volcanoID);
                    eruption.setName(name);
                    eruption.setType(type);
                    eruption.setLastErupted(lastErupted);
                    eruption.setLocation(location);
                    result.add(eruption);
                }
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
        return result;
    }

    /**
     * Closes the database connection when the object is no longer needed.
     */
    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqle) {
            error(sqle);
        }
    }

    /**
     * Handles database-related errors by printing the error details.
     * @param sqle The SQLException that occurred.
     */
    private void error(SQLException sqle) {
        System.err.println("Problem Accessing Database! " + sqle.getClass().getName());
        sqle.printStackTrace();
        System.exit(1);
    }
}
