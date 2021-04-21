import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int count = 0; //used for name's creating

    public static void main(String[] args) throws Exception {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json);
        List<Employee> listXml = parseXML("data.xml");
        String jsonFromXml = listToJson(listXml);
        writeString(jsonFromXml);
    }

    static public List parseCSV(String[] columnMapping, String filename) {
        List<Employee> staff = null;
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            staff = csv.parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return staff;
    }

    static public String listToJson (List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String json) {
        try (FileWriter fileWriter = new FileWriter("data" + count + ".json")) {
            fileWriter.write(json);
            fileWriter.flush();
        }   catch (IOException ex) {
            ex.printStackTrace();
        }
        count++;
    }

    public static List parseXML (String dataFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(dataFile));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        List<Employee> staff = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodesNode = nodeList.item(i);
            if (Node.ELEMENT_NODE == nodesNode.getNodeType()) {
                Element employee = (Element) nodesNode;
                String stringId = employee.getElementsByTagName("id").item(0).getTextContent();
                long id = Integer.parseInt(stringId);
                String firstName = employee.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = employee.getElementsByTagName("lastName").item(0).getTextContent();
                String country = employee.getElementsByTagName("country").item(0).getTextContent();
                String stringAge = employee.getElementsByTagName("age").item(0).getTextContent();
                int age = Integer.parseInt(stringAge);
                Employee worker = new Employee(id, firstName, lastName, country, age);
                staff.add(worker);
            }
        }
        return staff;
    }
}


