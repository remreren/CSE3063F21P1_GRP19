package coursetracking.utils;

import java.io.File;
import java.util.HashMap;

public class Utils {

    private static Utils utils;

    public static Utils getInstance() {
        if (utils == null) utils = new Utils();
        return utils;
    }

    private HashMap<String, Float> lettersHashMap = new HashMap<>();

    public float getGPAOfLetterGrade(String letterGrade) {
        return lettersHashMap.get(letterGrade);
    }

    private File resources;
    public File getResource(String filename){ 
        if (resources == null) resources = new File("./resources");
        return new File(resources,filename);
    }

}
