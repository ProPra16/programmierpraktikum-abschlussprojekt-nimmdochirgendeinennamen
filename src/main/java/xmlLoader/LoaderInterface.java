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

import java.io.File;

public interface LoaderInterface {

    //load a catalog from an xml file.
    void loadCatalog(File file);

    //@return name of the class at passed IDX
    String getClassName(int idx, int idx2);

    //@return name of the test at passed IDX
    String getTestName(int idx, int idx2);

    //@return  class at passed IDX
    String getClass(int idx, int idx2);

    //@return amount of classes for current exercise
    public int getClassAmount(int idx);

    //@return amount of classes for current exercise
    public int getTestAmount(int idx);

    //@return test at passed IDX
    String getTest(int idx, int idx2);

    //@return  name of the exercise at passed idx
    String getExerciseName(int idx);

    //@return description of the node with passed IDX
    String getDescription(int idx);

    //@return true if babysteps is active for the node with passed IDX
    boolean isBabystepsActive(int idx);

    //@return available time if babysteps is active
    int getBabyStepsTime(int idx);

    //@return true if timetracker is active for the node with passed IDX
    boolean isTimetrackerActive(int idx);

    //@return total amount of nodes which have been loaded from the catalog file.
    int getNumberOfExercises();
}
