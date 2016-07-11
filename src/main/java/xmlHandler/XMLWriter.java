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

import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class XMLWriter {


    public void appendXMLFile
            (File file, String exerciseName, String aDescription, String newClass, String classname, String newTest, String testname, String babystepsvalue, String babystepstime, String trackervalue)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);
        //"exercises" Node
        Element rootElement = document.getDocumentElement();

        //Nodes for new exercise
        Element newExercise = document.createElement("exercise");
        Element description = document.createElement("description");
        Element classes = document.createElement("classes");
        Element aClass = document.createElement("class");
        Element tests = document.createElement("tests");
        Element test = document.createElement("test");
        Element config = document.createElement("config");
        Element babysteps = document.createElement("babysteps");
        Element timetracking = document.createElement("timetracking");

        //add values / bindings to new Nodes
        newExercise.setAttribute("name",exerciseName);
        description.appendChild(document.createTextNode(aDescription));

        classes.appendChild(aClass);
        aClass.setAttribute("name",classname);
        aClass.appendChild(document.createTextNode(newClass));

        tests.appendChild(test);
        test.setAttribute("name", testname);
        test.appendChild(document.createTextNode(newTest));

        config.appendChild(babysteps);
        babysteps.setAttribute("value", babystepsvalue);
        babysteps.setAttribute("time", babystepstime);
        config.appendChild(timetracking);
        timetracking.setAttribute("value", trackervalue);

        newExercise.appendChild(description);
        newExercise.appendChild(classes);
        newExercise.appendChild(tests);
        newExercise.appendChild(config);

        rootElement.appendChild(newExercise);

        //Write to XML file
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        StreamResult result = new StreamResult(file.getPath());
        transformer.transform(source, result);
    }


    public void newXMLFile(File file, String exerciseName, String aDescription, String newClass, String classname, String newTest, String testname, String babystepsvalue, String babystepstime, String trackervalue) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        Element rootElement = document.createElement("exercises");

        //Nodes for new exercise
        Element newExercise = document.createElement("exercise");
        Element description = document.createElement("description");
        Element classes = document.createElement("classes");
        Element aClass = document.createElement("class");
        Element tests = document.createElement("tests");
        Element test = document.createElement("test");
        Element config = document.createElement("config");
        Element babysteps = document.createElement("babysteps");
        Element timetracking = document.createElement("timetracking");

        //add values / bindings to new Nodes
        newExercise.setAttribute("name",exerciseName);
        description.appendChild(document.createTextNode(aDescription));

        classes.appendChild(aClass);
        aClass.setAttribute("name",classname);
        aClass.appendChild(document.createTextNode(newClass));

        tests.appendChild(test);
        test.setAttribute("name", testname);
        test.appendChild(document.createTextNode(newTest));

        config.appendChild(babysteps);
        babysteps.setAttribute("value", babystepsvalue);
        babysteps.setAttribute("time", babystepstime);
        config.appendChild(timetracking);
        timetracking.setAttribute("value", trackervalue);

        newExercise.appendChild(description);
        newExercise.appendChild(classes);
        newExercise.appendChild(tests);
        newExercise.appendChild(config);

        rootElement.appendChild(newExercise);
        document.appendChild(rootElement);

        //Write to XML file
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        StreamResult result = new StreamResult(file.getPath());
        transformer.transform(source, result);

    }

}


