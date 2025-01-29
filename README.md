# Volcano Web Service

The **Volcano Web Service** is a Java-based RESTful API that provides information about volcanoes and eruptions. It allows users to query data based on country, year range, and geographical location.

---

## Features
- Get the total number of volcanoes and eruptions.
- Retrieve the number of volcanoes in a specific country.
- Get eruption details within a specified year range in JSON format.
- Search for volcanoes based on latitude, longitude, and last eruption date in XML format.

---

## Technologies Used
- **Java 8+**
- **Spark Java (Lightweight Web Framework)**
- **JSON Processing (org.json)**
- **XML Processing**
- **JDBC (For database operations)**

---

## Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or later
- Eclipse IDE (or any Java IDE of your choice)
- Apache Maven (for dependency management)
- Database setup with volcano and eruption data

### Steps to Run
1. Clone the repository:
   ```sh
   git clone <https://github.com/adss0/VolcanoWebService.git>
   ```
2. Open the project in **Eclipse** or any Java IDE.
3. Ensure dependencies are set up correctly (e.g., Spark Java, JSON, XML handling, JDBC).
4. Run the `VolcanoWebService.java` file as a **Java Application**.
5. The service will start on `http://localhost:8088/`.

---

## API Endpoints

### Test the Service
**GET** `/test`
- Example: `http://localhost:8088/test`
- Response:
  ```text
  Number of volcanoes: 1234
  Number of eruptions: 5678
  ```

### Get Number of Volcanoes in a Country
**GET** `/country?search={country_name}`
- Example: `http://localhost:8088/country?search=Japan`
- Response: `50` (Number of volcanoes in Japan)

### Get Eruptions by Year Range
**GET** `/year?from={start_year}&to={end_year}`
- Example: `http://localhost:8088/year?from=1900&to=2000`
- Gives Response in JSON format

### Get Volcanoes by Location and Last Eruption Date
**GET** `/location?latitude={lat}&longitude={lon}&erupted_since={year}`
- Example: `http://localhost:8088/location?latitude=35&longitude=13&erupted_since=1800`
- Gives Response in XML format

---

## License
This project is licensed under the **MIT License**.