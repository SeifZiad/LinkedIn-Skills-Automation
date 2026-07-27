package utilis;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import utilis.POJOClass.SkillData;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class HelperClass {

    private static final String TestPrjRoot =
            "src/test/java/";

    private static final String TestDataFolder =
            "testdata/";


    public static String ReadFromFile(
            String fileName,
            String Key

    ) throws FileNotFoundException {

        FileReader reader =
                new FileReader(
                        TestPrjRoot
                                + TestDataFolder
                                + fileName
                );

        JsonElement e1 =
                JsonParser.parseReader(reader);

        return e1
                .getAsJsonObject()
                .get(Key)
                .getAsString();
    }


  public static SkillData[] ReadSkills(String fileName)
        throws FileNotFoundException {

    FileReader reader = new FileReader(
            TestPrjRoot + TestDataFolder + fileName
    );

    JsonElement jsonElement = JsonParser.parseReader(reader);

    return new Gson().fromJson(
            jsonElement.getAsJsonObject().get("skills"),
            SkillData[].class
    );
        }
}