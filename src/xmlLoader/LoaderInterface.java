package xmlLoader;

import java.io.File;

public interface LoaderInterface {

    //load a catalog from an xml file.
    void loadCatalog(File file);

    //@return name of the class at passed IDX
    String getClassName(int idx);

    //@return name of the test at passed IDX
    String getTestName(int idx);

    //@return  class at passed IDX
    String getClass(int idx);

    //@return test at passed IDX
    String getTest(int idx);

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
