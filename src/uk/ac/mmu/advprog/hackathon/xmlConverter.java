package uk.ac.mmu.advprog.hackathon;

import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Converts eruption data into an XML format.
 */
public class xmlConverter {

    /**
     * Converts a list of eruptions into an XML string.
     * 
     * @param eruptionList The list of eruptions to be converted.
     * @return The XML string representation of the eruptions.
     */
    public String format(List<Eruption> eruptionList) {
        // StringWriter to capture the XML output
        StringWriter output = new StringWriter();
        
        try {
            // Initialize the DocumentBuilder
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();
            
            // Create the root element <Volcanoes>
            Element rootElement = doc.createElement("Volcanoes");
            doc.appendChild(rootElement);

            // Loop through the eruptionList to create XML elements for each eruption
            for (Eruption eruption : eruptionList) {
                // Get eruption details
                int volcanoID = eruption.getVolcanoID();  
                String name = eruption.getName();  
                String type = eruption.getType();  
                String lastErupted = eruption.getLastErupted();  
                Location location = eruption.getLocation(); 

                // Create <Volcano> element for each eruption
                Element volcano = doc.createElement("Volcano");
                volcano.setAttribute("id", String.valueOf(volcanoID));
                rootElement.appendChild(volcano);

                // Create <Name> element and append it to <Volcano>
                Element nameElement = doc.createElement("Name");
                nameElement.setTextContent(name);
                volcano.appendChild(nameElement);

                // Create <Type> element and append it to <Volcano>
                Element typeElement = doc.createElement("Type");
                typeElement.setTextContent(type);
                volcano.appendChild(typeElement);

                // Create <LastErupted> element and append it to <Volcano>
                Element lastEruptedElement = doc.createElement("LastErupted");
                lastEruptedElement.setTextContent(lastErupted);
                volcano.appendChild(lastEruptedElement);

                // Create <Location> element and append it to <Volcano>
                Element locationElement = doc.createElement("Location");
                volcano.appendChild(locationElement);
                
                // Get location details and append them as child elements
                double latitude = location.getLatitude(); 
                double longitude = location.getLongitude();  
                int elevation = location.getElevation(); 
                String country = location.getCountry();

                // Append location details to <Location>
                Element latitudeElement = doc.createElement("Latitude");
                latitudeElement.setTextContent(String.valueOf(latitude));
                locationElement.appendChild(latitudeElement);

                Element longitudeElement = doc.createElement("Longitude");
                longitudeElement.setTextContent(String.valueOf(longitude));
                locationElement.appendChild(longitudeElement);

                Element elevationElement = doc.createElement("Elevation");
                elevationElement.setTextContent(String.valueOf(elevation));
                locationElement.appendChild(elevationElement);

                Element countryElement = doc.createElement("Country");
                countryElement.setTextContent(country);
                locationElement.appendChild(countryElement);
            }

            // Convert Document to XML string
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(output));

        } catch (ParserConfigurationException | TransformerException e) {
            System.out.print("Error Creating XML: " + e.getMessage());
        }

        // Return XML as a string
        return output.toString();
    }
}
