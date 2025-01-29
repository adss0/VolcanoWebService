package uk.ac.mmu.advprog.hackathon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Handles database access from within web services.
 * 
 */
public class DB implements AutoCloseable {

	// allows us to easily change the database used
	private static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:./data/volcanoes.db";

	// allows us to re-use the connection between queries if desired
	private Connection connection = null;

	/**
	 * Creates an instance of the DB object and connects to the database
	 */
	public DB() {
		try {
			connection = DriverManager.getConnection(JDBC_CONNECTION_STRING);
		} catch (SQLException sqle) {
			error(sqle);
		}
	}

	/**
	 * Returns the number of volcanoes in the database, by counting rows
	 * 
	 * @return The number of volcanoes in the database, or -1 if empty
	 */
	public int getNumberOfVolcanoes() {
		int result = -1;
		try {
			Statement s = connection.createStatement();
			ResultSet results = s.executeQuery("SELECT COUNT(*) AS count FROM volcanoes");
			while (results.next()) { // will only execute once, because SELECT COUNT(*) returns just 1 number
				result = results.getInt(results.findColumn("count"));
			}
		} catch (SQLException sqle) {
			error(sqle);

		}
		return result;
	}

	/**
	 * Returns the number of eruptions in the database, by counting rows
	 * 
	 * @return The number of eruptions in the database, or -1 if empty
	 */
	public int getNumberOfEruptions() {
		int result = -1;
		try {
			Statement s = connection.createStatement();
			ResultSet results = s.executeQuery("SELECT COUNT(*) AS count FROM eruptions");
			while (results.next()) { // will only execute once, because SELECT COUNT(*) returns just 1 number
				result = results.getInt(results.findColumn("count"));
			}
		} catch (SQLException sqle) {
			error(sqle);

		}
		return result;
	}

	/**
	 * Gets all the Volcanoes in a country
	 * @param search 
	 * @return The number of Volcanoes in a Country
	 */
	public int getNumberOfVolcanoesInACountry(String search) {
		int result = 0;
		try {
			String query = "SELECT COUNT(*) AS Number FROM volcanoes WHERE Country=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, search);
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				result = results.getInt(results.findColumn("Number"));
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		//return the results
		return result;
	}

	/**
	 * Gets all the eruptions in a specified year Range, by storing them in an Array List
	 * @param from (The year searching from)
	 * @param to (The year that you want to search till)
	 * @return The number of Eruptions in the given Year Range
	 */
	public List<Eruption> getEruptionsInYearRange(int from, int to) {

		ArrayList<Eruption> eruptionObjects = new ArrayList<Eruption>();

		try {
			Statement s = connection.createStatement();
			String query = "SELECT * " + "FROM Eruptions "
					+ "INNER JOIN Volcanoes ON Eruptions.Volcano_ID = Volcanoes.ID "
					+ "WHERE CAST(Eruptions.Date AS INTEGER) >= " + from + " AND CAST(Eruptions.Date AS INTEGER) <= "
					+ to + " ORDER BY Eruptions.Date ASC";

			ResultSet results = s.executeQuery(query);

			while (results.next()) {
				// Extract data from the result set
				String eruptionDate = results.getString("Date");
				String name = results.getString("Name");

				double latitude = results.getDouble("Latitude");
				double longitude = results.getDouble("Longitude");
				int elevation = results.getInt("Elevation");
				String country = results.getString("Country");

				int deaths = results.getInt("Deaths");
				int missing = results.getInt("Missing");
				int injuries = results.getInt("Injuries");
				
				//Pass in the parameters to location class
				Location location = new Location(latitude, longitude, elevation, country);
				//Pass in the parameters to Eruption class
				Eruption eruption = new Eruption(location, eruptionDate, name, deaths, missing, injuries);
				//Store the data in an array 
				eruptionObjects.add(eruption);
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		//return the result Array List
		return eruptionObjects;
	}

	/**
	 * This gets the Volcanoes according to their location and last eruption, by storing the data in an Array List
	 * @param latitude
	 * @param longitude
	 * @param eruptedSince
	 * @return The Volcanoes specified by Location and Last Eruption 
	 */
	public List<Eruption> getVolcanoesByLocationAndLastEruption(double latitude, double longitude,
			String eruptedSince) {
		ArrayList<Eruption> result = new ArrayList<>();

		try {
			Statement s = connection.createStatement();
			String query = "SELECT MAX(Date) AS Last_Erupted, Volcano_ID, Name, Country, Latitude, Longitude, Elevation, Type"
					+ " FROM Eruptions INNER JOIN Volcanoes ON Eruptions.Volcano_ID = Volcanoes.ID WHERE CAST(Date AS INTEGER) >= 1 "
					+ " GROUP BY Volcano_ID " + "ORDER BY(((53.472 - " + latitude + ") * (53.472 - " + latitude
					+ ")) + (0.595 * ((-2.244 - " + longitude + ") * (-2.244 -" + longitude + ")))) ASC LIMIT 10 ";

			ResultSet results = s.executeQuery(query);

			while (results.next()) {
				// Extracting data from the result set
				String name = results.getString("Name");
				String lastErupted = results.getString("Last_Erupted");
				int volcanoID = results.getInt("Volcano_ID");
				double latitude1 = results.getDouble("Latitude");
				double longitude1 = results.getDouble("Longitude");
				int elevation = results.getInt("Elevation");
				String type = results.getString("Type");
				String country = results.getString("Country");

				// Pass the parameters to Location class
				Location location = new Location(latitude1, longitude1, elevation, country);

				//Pass the parameters to the Eruption class
				Eruption eruption = new Eruption();
				eruption.setVolcanoID(volcanoID);
				eruption.setName(name);
				eruption.setType(type);
				eruption.setLastErupted(lastErupted);
				eruption.setLocation(location);

				// Add the data to the result list
				result.add(eruption);
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
		//return the result Array List
		return result;
	}

	/**
	 * Closes the connection to the database, required by AutoCloseable interface.
	 */
	@Override
	public void close() {
		try {
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException sqle) {
			error(sqle);
		}
	}

	/**
	 * Prints out the details of the SQL error that has occurred, and exits the
	 * programme
	 * 
	 * @param sqle Exception representing the error that occurred
	 */
	private void error(SQLException sqle) {
		System.err.println("Problem Accessing Database! " + sqle.getClass().getName());
		sqle.printStackTrace();
		System.exit(1);
	}

}
