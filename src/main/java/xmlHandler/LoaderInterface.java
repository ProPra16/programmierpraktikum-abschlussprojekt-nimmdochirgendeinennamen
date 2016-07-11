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

import java.io.File;
/**This is the interface for the XML Loader.
 * Proper documentation for the methods can be cound in the implementing class.
 * @author Kai Holzinger
 * @version 1.1
 * */
public interface LoaderInterface {

    //load a catalog from an xml file.
    void loadCatalog(File file);

    boolean isTimetrackerActive(int idx);
    boolean isBabystepsActive(int idx);

    String getClassName(int idx, int idx2);
    String getTestName(int idx, int idx2);
    String getClass(int idx, int idx2);
    String getTest(int idx, int idx2);
    String getExerciseName(int idx);
    String getDescription(int idx);

    int getClassAmount(int idx);
    int getTestAmount(int idx);
    int getBabyStepsTime(int idx);
    int getNumberOfExercises();
}
