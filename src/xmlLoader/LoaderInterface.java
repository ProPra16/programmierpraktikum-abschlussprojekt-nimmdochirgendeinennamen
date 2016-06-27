package xmlLoader;

public interface LoaderInterface {

    //load a catalog from an xml file.
    void loadCatalog();

    //@return Name of the node with passed IDX
    String getName(int idx);

    //@return description of the node with passed IDX
    String getDescription(int idx);

    //@return all classes to be found for the node with passed IDX
    String[] getClasses(int idx);

    //@return all tests to be found for the node with passed IDX
    String[] getTests(int idx);

    //@return true if babysteps is active for the node with passed IDX
    boolean isBabystepsActive(int idx);

    //@return true if timetracker is active for the node with passed IDX
    boolean isTimetrackerActive(int idx);

    //@return total amount of nodes which have been loaded from the catalog file.
    int getNumberOfExercises();
}
