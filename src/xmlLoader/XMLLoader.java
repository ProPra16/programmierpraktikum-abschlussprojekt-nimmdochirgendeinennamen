package xmlLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLLoader implements LoaderInterface{

    private NodeList exercises;

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
        } catch (ParserConfigurationException | SAXException | IOException ignored){}
    }

                                            /*EXERCISE GETTERS*/

    //@return the total amount of exercises
    @Override
    public int getNumberOfExercises() {
        return exercises.getLength();
    }

    //@return name of selected exercise
    @Override
    public String getExerciseName(int idx) {
        return getExercise(idx).getAttribute("name");
    }

    //@return the description of the selected exercise
    @Override
    public String getDescription(int idx) {
        return goToNode(getExercise(idx),"description").getTextContent();
    }

                                            /*CLASS GETTERS*/

    /*@return classname for the selected class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param classIDX index of the exercise within the NodeList of classes
     */
    @Override
    public String getClassName(int exerciseIDX, int classIDX) {
        Element element = (Element) goToNode(getExercise(exerciseIDX),"classes").getElementsByTagName("class").item(classIDX);
        return element.getAttribute("name");
    }

    //@return amount of classes for current exercise
    @Override
    public int getClassAmount(int idx){
        NodeList nl = (goToNode(getExercise(idx),"classes")).getElementsByTagName("class");
        return nl.getLength();
    }

    /*@returns code of the selected class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param classIDX index of the exercise within the NodeList of classes
     */
    @Override
    public String getClass(int exerciseIDX, int classIDX) {
        Element element = (Element) goToNode(getExercise(exerciseIDX),"classes").getElementsByTagName("class").item(classIDX);
        return element.getTextContent().trim();
    }

                                            /*TEST GETTERS*/

    /*@returns name of the selected test class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param testIDX index of the test within the NodeList of tests
     */
    @Override
    public String getTestName(int exerciseIDX, int testIDX) {
        Element element = (Element) goToNode(getExercise(exerciseIDX),"tests").getElementsByTagName("test").item(testIDX);
        return element.getAttribute("name");
    }

    //@return amount of classes for current exercise
    @Override
    public int getTestAmount(int idx){
        NodeList nl = (goToNode(getExercise(idx),"tests")).getElementsByTagName("test");
        return nl.getLength();
    }

    /*@returns code of the selected test class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param testIDX index of the test within the NodeList of tests
     */
    @Override
    public String getTest(int exerciseIDX, int testIDX) {
        Element element = (Element) goToNode(getExercise(exerciseIDX),"tests").getElementsByTagName("test").item(testIDX);
        return element.getTextContent().trim();
    }

                                            /*CONFIG GETTERS*/

    //@return true if babystep value == true
    @Override
    public boolean isBabystepsActive(int idx) {
        return goToNode(getExercise(idx),"config","babysteps").getAttribute("value").equalsIgnoreCase("true");
    }

    //@return time window if babysteps is turned on
    @Override
    public int getBabyStepsTime(int idx) {
        return Integer.parseInt(goToNode(getExercise(idx),"config","babysteps").getAttribute("time"));
    }

    //@return true if timetracking == true
    @Override
    public boolean isTimetrackerActive(int idx) {
        return goToNode(getExercise(idx),"config","timetracking").getAttribute("value").equalsIgnoreCase("true");
    }

                                            /*INTERNAL GETTERS*/

    //@return n-th exercise tree
    private Element getExercise(int n){
        return (Element) exercises.item(n);
    }

    //@return Element n of passed exercise tree
    private Element goToNode(Element exerciseElement, String n){
        return (Element) exerciseElement.getElementsByTagName(n).item(0);
    }

    //@return Element-tree n2 of Element-tree n1 of the passed exercise tree
    private Element goToNode(Element exerciseElement, String n1, String n2){
        return goToNode(goToNode(exerciseElement,n1),n2);
    }
}
