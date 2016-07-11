/**
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

/** This class implements the LoaderInterface.
  * It is used to parse an XML-Catalog of exercises for the Test Driven Development Trainer (TDDT).
  * @author Kai Holzinger
  * @version 1.13
  * */
public class XMLLoader implements LoaderInterface {

    private NodeList exercises;

    /**
     * @param file - The XML file which contains the exercises.
     */
    public XMLLoader(File file) {
        loadCatalog(file);
    }

    /**
     * The constructor gets a file and calls the method loadCatalog.
     * @param xmlCatalog  The XML file which contains the exercises.
     * @throws  InvalidFileException if the method was unable to parse the XML file.
     */
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

                                            /*EXERCISE GETTERS*/

    /**
     * @return the total amount of exercises
     * @throws InvalidFileException if the return value is null
     */
    @Override
    public int getNumberOfExercises() throws InvalidFileException {
        try {
            return exercises.getLength();
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not determine number of exercises. XML Syntax might be corrupt.", e);
        }
    }

    /**
     * @param idx the index of the exercise.
     * @return name of selected exercise
     * @throws InvalidFileException if a nonexistent exercise is called.
     */
    @Override
    public String getExerciseName(int idx) throws InvalidFileException {
        try {
            return getExercise(idx).getAttribute("name");
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Could not determine exercise name. XML Syntax might be corrupt or the exercise at IDX " + idx + " does not exist.", e);
        }
    }

    /**
     * @param idx the index of the exercise
     * @return the description of the selected exercise
     * @throws InvalidFileException if a nonexistent exercise is called.
     */
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

    /**
     * @return classname for the selected class
     * @param exerciseIDX index of the exercise within the NodeList
     * @param classIDX index of the class within the NodeList of classes
     * @throws InvalidFileException if a nonexistent exercise and / or class is called.
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

    /**
     * @param idx the index of the exercise
     * @return the amount of classes of the selected exercise
     * @throws InvalidFileException if the amount of classes is null.
     */
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

    /**
     * @return code of the selected class
     * @param exerciseIDX index of the exercise within the NodeList
     * @param classIDX index of the exercise within the NodeList of classes
     * @throws InvalidFileException if the called class is nonexistent
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

    /**
     * @return name of the selected showStage class
     * @param exerciseIDX index of the exercise within the NodeList
     * @param testIDX index of the showStage within the NodeList of tests
     * @throws InvalidFileException if a nonexistent exercise and / or test is called.
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

    /**
     * @param idx index of the exercise.
     * @return the amount of tests for the selected exercise.
     * @throws InvalidFileException if the test amount is null.
     */
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

    /**
     * @return code of the selected showStage class
     * @param exerciseIDX index of the exercise within the NodeList
     * @param testIDX index of the showStage within the NodeList of tests
     * @throws InvalidFileException if a nonexistent exercise and / or test is called.
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

    /**
     * @param idx Index of the exercise
     * @return True if babysteps value == true
     * @throws InvalidFileException If the return value is null.
     */
    @Override
    public boolean isBabystepsActive(int idx) throws InvalidFileException {
        try {
            return goToNode(getExercise(idx), "config", "babysteps").getAttribute("value").equalsIgnoreCase("true");
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Couldnt find a babysteps config at index "+idx+" . Check the catalog syntax.", e);
        }
    }

    /**
     * @param idx Index of the exercise.
     * @return The time the user has to switch the phase.
     * @throws InvalidFileException If the return value is null.
     */
    @Override
    public int getBabyStepsTime(int idx) throws InvalidFileException {
        try {
            return Integer.parseInt(goToNode(getExercise(idx), "config", "babysteps").getAttribute("time"));
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Error. Couldnt find a babysteps config at index "+idx+". Check the catalog syntax.", e);
        }
    }

    /**
     * @param idx Index of the exercise.
     * @return This will always return true, because we dont want timetracking to be disabled. Ever.
     * @throws InvalidFileException Practically never. But the Exception might be thrown if the developers decide to make the timetracker disablable.
     */
    @Override
    public boolean isTimetrackerActive(int idx) throws InvalidFileException {
        //try {
            //return goToNode(getExercise(idx), "config", "timetracking").getAttribute("value").equalsIgnoreCase("true");
            return true;
        //} catch (NullPointerException e) {
          //  throw new InvalidFileException
				//("Error. Couldnt find a timetracking config at index "+idx+". Check the catalog syntax.", e);
        //}
    }

                                            /*INTERNAL GETTERS*/

    /**
     *
     * @param n The index of the exercise.
     * @return The n-th exercise tree.
     * @throws InvalidFileException If the return value is null.
     */
    private Element getExercise(int n) throws InvalidFileException {
        try {
            return (Element) exercises.item(n);
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Internal error. Could not fetch element at index " + n + ".", e);
        }
    }

    /**
     * @param exerciseElement One exercise element.
     * @param n The name of the node which is returned.
     * @return Node n of the passed exercise element.
     * @throws InvalidFileException If the return value is null.
     */
    private Element goToNode(Element exerciseElement, String n) throws InvalidFileException {
        try {
            return (Element) exerciseElement.getElementsByTagName(n).item(0);
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Internal error. Could not select the specified node " + n + ".", e);
        }
    }

    /**
     * @param exerciseElement One exercise element.
     * @param n1 The name of the node which contains the second node.
     * @param n2 The name of the node which is returned.
     * @return If the return value is null.
     * @throws InvalidFileException
     */
    private Element goToNode(Element exerciseElement, String n1, String n2) throws InvalidFileException {
        try {
            return goToNode(goToNode(exerciseElement, n1), n2);
        } catch (NullPointerException e) {
            throw new InvalidFileException
				("Internal error. Could not select the child " + n2 + "of the node " + n1 + ".", e);
        }
    }
}
