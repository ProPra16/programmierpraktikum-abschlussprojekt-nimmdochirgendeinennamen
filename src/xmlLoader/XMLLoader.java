package xmlLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLLoader implements LoaderInterface{

    NodeList exercises;

    public XMLLoader(File file){
        loadCatalog(file);
    }

    //load a catalog from an xml file.
    @Override
    public void loadCatalog(File xmlCatalog){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(xmlCatalog);
            d.getDocumentElement().normalize();
            exercises = d.getElementsByTagName("exercise");
        } catch (ParserConfigurationException | SAXException | IOException e){}
    }

    //@return amount of classes for current exercise
    public int getClassAmount(int idx){
        Node node = exercises.item(idx);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("classes").item(0);
        NodeList nl = element.getElementsByTagName("class");
        return nl.getLength();
    }

    /*@return classname for the selected class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param classIDX index of the exercise within the NodeList of classes
     */
    @Override
    public String getClassName(int exerciseIDX, int classIDX) {
        Node node = exercises.item(exerciseIDX);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("classes").item(0);
        element = (Element) element.getElementsByTagName("class").item(classIDX);
        return element.getAttribute("name");
    }

    /*@returns name of the selected test class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param testIDX index of the test within the NodeList of tests
     */
    @Override
    public String getTestName(int exerciseIDX, int testIDX) {
        Node node = exercises.item(exerciseIDX);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("tests").item(0);
        element = (Element) element.getElementsByTagName("test").item(testIDX);
        return element.getAttribute("name");
    }

    /*@returns code of the selected class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param classIDX index of the exercise within the NodeList of classes
     */
    @Override
    public String getClass(int exerciseIDX, int classIDX) {
        Node node = exercises.item(exerciseIDX);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("classes").item(0);
        element = (Element) element.getElementsByTagName("class").item(classIDX);
        return element.getTextContent().trim();
    }

    /*@returns code of the selected test class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param testIDX index of the test within the NodeList of tests
     */
    @Override
    public String getTest(int exerciseIDX, int testIDX) {
        Node node = exercises.item(exerciseIDX);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("tests").item(0);
        element = (Element) element.getElementsByTagName("test").item(testIDX);
        return element.getTextContent().trim();
    }

    //@return name of selected exercise
    @Override
    public String getExerciseName(int idx) {
        Node node = exercises.item(idx);
        Element element = (Element) node;
        return element.getAttribute("name");
    }

    //@return the description of the selected exercise
    @Override
    public String getDescription(int idx) {
        Node node = exercises.item(idx);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("description").item(0);
        return element.getTextContent();
    }

    //@return true if babystep value == true
    @Override
    public boolean isBabystepsActive(int idx) {
        Node node = exercises.item(idx);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("config").item(0);
        element = (Element) element.getElementsByTagName("babysteps").item(0);
        return (element.getAttribute("value").equalsIgnoreCase("true"));
    }

    //@return time window if babysteps is turned on
    @Override
    public int getBabyStepsTime(int idx) {
        Node node = exercises.item(idx);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("config").item(0);
        element = (Element) element.getElementsByTagName("babysteps").item(0);
        return Integer.parseInt(element.getAttribute("time"));
    }

    //@return true if timetracking == true
    @Override
    public boolean isTimetrackerActive(int idx) {
        Node node = exercises.item(idx);
        Element element = (Element) node;
        element = (Element) element.getElementsByTagName("config").item(0);
        element = (Element) element.getElementsByTagName("timetracking").item(0);
        return (element.getAttribute("value").equalsIgnoreCase("True"));
    }

    //@return the total amount of exercises
    @Override
    public int getNumberOfExercises() {
        return exercises.getLength();
    }
}
