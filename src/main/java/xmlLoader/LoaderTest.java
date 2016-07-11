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

package xmlLoader;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;

public class LoaderTest {
    XMLLoader testLoader = new XMLLoader(new File("src/xmlLoader/catalog.xml"));

    @Test
    public void testBabysteps() {
        assertEquals("Must be true.", true, testLoader.isBabystepsActive(0));
        assertEquals("Must be false.", false, testLoader.isBabystepsActive(1));
        assertEquals("Must be 10.", 10, testLoader.getBabyStepsTime(0));
        assertEquals("Must be 20.", 20, testLoader.getBabyStepsTime(1));
    }

    @Test
    public void testTimetracker() {
        assertEquals("Must be true.", true, testLoader.isTimetrackerActive(0));
        assertEquals("Must be false.", false, testLoader.isTimetrackerActive(1));
    }

    @Test
    public void testClassAmount() {
        assertEquals("Must be 2.", 2, testLoader.getClassAmount(0));
        assertEquals("Must be 1.", 1, testLoader.getClassAmount(1));
    }

    @Test
    public void testTestAmount() {
        assertEquals("Must be 2.", 2, testLoader.getTestAmount(0));
        assertEquals("Must be 1.", 1, testLoader.getTestAmount(1));
    }

    @Test
    public void testClassNames() {
        assertEquals("Must be TestClass.", "TestClass", testLoader.getClassName(0,0));
        assertEquals("Must be TestClass2.", "TestClass2", testLoader.getClassName(0,1));
        assertEquals("Must be TestClass2.1.", "TestClass2.1", testLoader.getClassName(1,0));
    }

    @Test
    public void testTestNames() {
        assertEquals("Must be TestExTester.", "TestExTester", testLoader.getTestName(0,0));
        assertEquals("Must be TestExTester2.", "TestExTester2", testLoader.getTestName(0,1));
        assertEquals("Must be ex2test.", "ex2test", testLoader.getTestName(1,0));
    }

    @Test
    public void testClassCode() {
        assertEquals("testable ClassCode", "testable ClassCode", testLoader.getClass(0,0));
        assertEquals("testable ClassCode2", "testable ClassCode2", testLoader.getClass(0,1));
        assertEquals("Testable classcode2.1", "Testable classcode2.1", testLoader.getClass(1,0));
    }

    @Test
    public void testTestCode() {
        assertEquals("Testable testtext", "Testable testtext", testLoader.getTest(0,0));
        assertEquals("Testable testtext2", "Testable testtext2", testLoader.getTest(0,1));
        assertEquals("Testable testtext2.1", "Testable testtext2.1", testLoader.getTest(1,0));
    }

    @Test
    public void testExerciseName() {
        assertEquals("TestEx", "TestEx", testLoader.getExerciseName(0));
        assertEquals("TestEx2", "TestEx2", testLoader.getExerciseName(1));
    }

    @Test
    public void testDescription() {
        assertEquals("Description comes here", "Description comes here", testLoader.getDescription(0));
        assertEquals("Description comes here2", "Description comes here2", testLoader.getDescription(1));
    }


}