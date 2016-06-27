package xmlLoader;

public interface LoaderInterface {

    //load a catalog from an xml file.
    void loadCatalog();

    //@return Name of the node with passed ID
    String getNameByID(int id);

    //@return description of the node with passed ID
    String getDescriptionByID(int id);

    //@return all classes to be found for the node with passed ID
    String[] getClassesByID(int id);

    //@return all tests to be found for the node with passed ID
    String[] getTestsByID(int id);

    //@return true if babysteps is active for the node with passed ID
    boolean isBabystepsActive(int id);

    //@return true if timetracker is active for the node with passed ID
    boolean isTimetrackerActive(int id);

    //@return total amount of nodes which have been loaded from the catalog file.
    int getNumberOfExercises();
}
