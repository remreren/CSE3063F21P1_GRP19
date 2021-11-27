package coursetracking.utils;

import java.util.HashMap;

public class Utils {

    private static Utils utils;

    public static Utils getInstance() {
        if (utils == null) utils = new Utils();
        return utils;
    }

    private HashMap<String, Float> lettersHashMap = new HashMap<>(){{
        put("AA", 4.0f);
        put("BA", 3.5f);
        put("BB", 3.0f);
        put("CB", 2.5f);
        put("CC", 2.0f);
        put("DC", 2.5f);
        put("DD", 1.0f);
        put("FD", 0.5f);
        put("FF", 0.0f);
    }};

    public float getGPAOfLetterGrade(String letterGrade) {
        return lettersHashMap.get(letterGrade);
    }
}
