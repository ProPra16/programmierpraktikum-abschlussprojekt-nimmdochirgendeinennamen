/*
 * Copyright (c) 2016. Caro Jachmann, Dominik Kuhnen, Jule Pohlmann, Kai Brandt, Kai Holzinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package main.java.xmlHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLLoader implements LoaderInterface {

    private Document document;
    private NodeList exercises;

    public XMLLoader(File file) {
        loadCatalog(file);
    }

    //load a catalog from an xml file.
    @Override
    public void loadCatalog(File xmlCatalog) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(xmlCatalog);
            d.getDocumentElement().normalize();
            exercises = d.getElementsByTagName("exercise");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new InvalidFileException
				("Error. The specified file either contains no catalog or its syntax is corrupt.", e);
        }
    }

    public Document getDocument(){
        return this.document;
    }

                                            /*EXERCISE GETTERS*/

    //@return the total amount of exercises
    @Override
    public int getNumberOfExercises() throws InvalidFileException {
        try {
            return exercises.getLength();
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not determine number of exercises. XML Syntax might be corrupt.", e);
        }
    }

    //@return name of selected exercise
    @Override
    public String getExerciseName(int idx) throws InvalidFileException {
        try {
            return getExercise(idx).getAttribute("name");
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not determine exercise name. XML Syntax might be corrupt or the exercise at IDX " + idx + " does not exist.", e);
        }
    }

    //@return the description of the selected exercise
    @Override
    public String getDescription(int idx) throws InvalidFileException {
        try {
            return goToNode(getExercise(idx), "description").getTextContent();
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not determine exercise description. XML Syntax might be corrupt or the exercise at IDX" + idx + "does not exist.", e);
        }
    }

                                            /*CLASS GETTERS*/

    /*@return classname for the selected class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param classIDX index of the exercise within the NodeList of classes
     */
    @Override
    public String getClassName(int exerciseIDX, int classIDX) throws InvalidFileException {
        try {
            Element element = (Element) goToNode(getExercise(exerciseIDX), "classes").getElementsByTagName("class").item(classIDX);
            return element.getAttribute("name");
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not determine class name. XML syntax might be corrupt or the exercise at IDX " + exerciseIDX + ", respectively class index " + classIDX + " does not exist", e);
        }
    }

    //@return amount of classes for current exercise
    @Override
    public int getClassAmount(int idx) throws InvalidFileException {
        try {
            NodeList nl = (goToNode(getExercise(idx), "classes")).getElementsByTagName("class");
            return nl.getLength();
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not get class amount of the passed exercise. XML syntax might be corrupt or the exercise at idx "+idx+" doesnt exist.", e);
        }
    }

    /*@return code of the selected class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param classIDX index of the exercise within the NodeList of classes
     */
    @Override
    public String getClass(int exerciseIDX, int classIDX) throws InvalidFileException {
        try {
            Element element = (Element) goToNode(getExercise(exerciseIDX), "classes").getElementsByTagName("class").item(classIDX);
            return element.getTextContent().trim();
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not load class text. XML syntax might be corrupt or the exercise at idx "+exerciseIDX+" / class at idx "+classIDX+" does not exist.", e);
        }
    }

                                            /*TEST GETTERS*/

    /*@return name of the selected showStage class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param testIDX index of the showStage within the NodeList of tests
     */
    @Override
    public String getTestName(int exerciseIDX, int testIDX) throws InvalidFileException {
        try {
            Element element = (Element) goToNode(getExercise(exerciseIDX), "tests").getElementsByTagName("test").item(testIDX);
            return element.getAttribute("name");
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not determine showStage name. XML syntax might be corrupt or the exercise at idx "+exerciseIDX+", respectively showStage at idx "+testIDX+" does not exist.", e);
        }
    }

    //@return amount of classes for current exercise
    @Override
    public int getTestAmount(int idx) throws InvalidFileException {
        try {
            NodeList nl = (goToNode(getExercise(idx), "tests")).getElementsByTagName("test");
            return nl.getLength();
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not get class amount of the passed exercise. XML syntax might be corrupt or the exercise at idx "+idx+" does not exist.", e);
        }
    }

    /*@return code of the selected showStage class
     *@param exerciseIDX index of the exercise within the NodeList
     *@param testIDX index of the showStage within the NodeList of tests
     */
    @Override
    public String getTest(int exerciseIDX, int testIDX) throws InvalidFileException {
        try {
            Element element = (Element) goToNode(getExercise(exerciseIDX), "tests").getElementsByTagName("test").item(testIDX);
            return element.getTextContent().trim();
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not load showStage code. XML syntax might be corrupt or the exercise at idx "+exerciseIDX+" / showStage at idx "+testIDX+" does not exist.", e);
        }
    }

                                            /*CONFIG GETTERS*/

    //@return true if babystep value == true
    @Override
    public boolean isBabystepsActive(int idx) throws InvalidFileException {
        try {
            return goToNode(getExercise(idx), "config", "babysteps").getAttribute("value").equalsIgnoreCase("true");
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Couldnt find a babysteps config at index "+idx+" . Check the catalog syntax.", e);
        }
    }

    //@return time window if babysteps is turned on
    @Override
    public int getBabyStepsTime(int idx) throws InvalidFileException {
        try {
            return Integer.parseInt(goToNode(getExercise(idx), "config", "babysteps").getAttribute("time"));
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Couldnt find a babysteps config at index "+idx+". Check the catalog syntax.", e);
        }
    }

    //@return true if timetracking == true
    @Override
    public boolean isTimetrackerActive(int idx) throws InvalidFileException {
        try {
            return goToNode(getExercise(idx), "config", "timetracking").getAttribute("value").equalsIgnoreCase("true");
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Couldnt find a timetracking config at index "+idx+". Check the catalog syntax.", e);
        }
    }

                                            /*INTERNAL GETTERS*/

    //@return n-th exercise tree
    private Element getExercise(int n) throws InvalidFileException {
        try {
            return (Element) exercises.item(n);
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Internal error. Could not fetch element at index " + n + ".", e);
        }
    }

    //@return Element n of passed exercise tree
    private Element goToNode(Element exerciseElement, String n) throws InvalidFileException {
        try {
            return (Element) exerciseElement.getElementsByTagName(n).item(0);
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Internal error. Could not select the specified node " + n + ".", e);
        }
    }

    //@return Element-tree n2 of Element-tree n1 of the passed exercise tree
    private Element goToNode(Element exerciseElement, String n1, String n2) throws InvalidFileException {
        try {
            return goToNode(goToNode(exerciseElement, n1), n2);
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Internal error. Could not select the child " + n2 + "of the node " + n1 + ".", e);
        }
    }
}
