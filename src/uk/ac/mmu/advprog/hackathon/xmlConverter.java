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

public class xmlConverter {

	        public String format(List<Eruption> eruptionList) {
	        	//String Writer
	            StringWriter output = new StringWriter();
	            
	            try {
	                // A new DocumentBuilderFactory and DocumentBuilder
	                DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	                DocumentBuilder build = dFact.newDocumentBuilder();
	                Document doc = build.newDocument();
	                
	                //<Volcanoes> element which is the root
	                Element rootElement = doc.createElement("Volcanoes");
	                doc.appendChild(rootElement);

	                //create XML elements for each eruption by looping through the eruptionList
	                for (Eruption eruption : eruptionList) {
	                    // Get the values from the eruption Object Class
	                    int volcanoID = eruption.getVolcanoID();  
	                    String name = eruption.getName();  
	                    String type = eruption.getType();  
	                    String lastErupted = eruption.getLastErupted();  
	                    Location location = eruption.getLocation(); 

	                    //<Volcano> element
	                    Element volcano = doc.createElement("Volcano");
	                    volcano.setAttribute("id", String.valueOf(volcanoID));
	                    // Add the <Volcano> element to Volcanoes
	                    rootElement.appendChild(volcano);
	                    
	                    
	                    //<Name> element
	                    Element nameElement = doc.createElement("Name");
	                    nameElement.setTextContent(name);
	                    // Add the <Name> element to volcano
	                    volcano.appendChild(nameElement);

	                    //<Type> element
	                    Element typeElement = doc.createElement("Type");
	                    typeElement.setTextContent(type);
	                    // Add the <Type> element to volcano
	                    volcano.appendChild(typeElement);

	                    //<LastErupted> element
	                    Element lastEruptedElement = doc.createElement("LastErupted");
	                    lastEruptedElement.setTextContent(lastErupted);
	                    // Add the <LastErupted> element to volcano
	                    volcano.appendChild(lastEruptedElement);

	                    //<Location> element
	                    Element locationElement = doc.createElement("Location");
	                    // Add the <Location> element to volcano
	                    volcano.appendChild(locationElement);
	                    
	                    // Get location details from the location class
	                    double latitude = location.getLatitude(); 
	                    double longitude = location.getLongitude();  
	                    int elevation = location.getElevation(); 
	                    String country = location.getCountry(); 

	                    // Add the location details into the location element
	                    
	                    //<Latitude> element
	                    Element latitudeElement = doc.createElement("Latitude");
	                    latitudeElement.setTextContent(String.valueOf(latitude));
	                    locationElement.appendChild(latitudeElement);
	                    //<Longitude> element
	                    Element longitudeElement = doc.createElement("Longitude");
	                    longitudeElement.setTextContent(String.valueOf(longitude));
	                    locationElement.appendChild(longitudeElement);
	                    //<Elevation> element
	                    Element elevationElement = doc.createElement("Elevation");
	                    elevationElement.setTextContent(String.valueOf(elevation));
	                    locationElement.appendChild(elevationElement);
	                    //<Country> element
	                    Element countryElement = doc.createElement("Country");
	                    countryElement.setTextContent(country);
	                    locationElement.appendChild(countryElement);
	                }

	                //output the XML
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
