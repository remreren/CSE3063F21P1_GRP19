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

    private File output;

    public File getOutputPath() {
        if (output == null) output = new File("./output");
        output.mkdir();
        return output;
    }
}
