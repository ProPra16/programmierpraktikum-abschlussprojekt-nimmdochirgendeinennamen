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

package xmlHandler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class WriterTest {


    XMLWriter w = new XMLWriter();
    XMLLoader l;

    @Test
    public void testappend() {
        try {
            w.appendXMLFile(new File("src/main/java/xmlHandler/catalog.xml") ,"exercisename", "description", "class", "classname", "test", "testname", "true", "50", "true");
            l = new XMLLoader(new File("src/main/java/xmlHandler/catalog.xml"));
            String exercisename = l.getExerciseName(l.getNumberOfExercises()-1);
            assertEquals("Must be exercisename", "exercisename", exercisename);
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNew() {
        try {
            w.newXMLFile(new File("src/main/java/xmlHandler/tests.xml") ,"exercisename", "description", "class", "classname", "test", "testname", "true", "50", "true");
            l = new XMLLoader(new File("src/main/java/xmlHandler/tests.xml"));
            String exercisename = l.getExerciseName(l.getNumberOfExercises()-1);
            assertEquals("Must be exercisename", "exercisename", exercisename);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
