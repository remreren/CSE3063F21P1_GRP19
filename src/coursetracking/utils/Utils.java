package coursetracking.utils;

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
}
