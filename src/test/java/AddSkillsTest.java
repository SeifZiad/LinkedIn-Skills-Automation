import Pages.profilepage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilis.ConfigReader;
import utilis.HelperClass;
import utilis.POJOClass.SkillData;
import utilis.WebDriverHandle;

import java.io.FileNotFoundException;
import java.util.List;

public class AddSkillsTest {

    private WebDriver driver;
    private profilepage profilepage;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverHandle.initializeDriver(ConfigReader.getBrowser());
        driver.manage().window().maximize();
        profilepage = new profilepage(driver);
    }

    @DataProvider(name = "skillsProvider")
    public Object[][] skillsProvider() throws FileNotFoundException {
        SkillData[] skills = HelperClass.ReadSkills("skills.JSON");
        Object[][] data = new Object[skills.length][2];

        for (int i = 0; i < skills.length; i++) {
            data[i][0] = skills[i].name;
            data[i][1] = skills[i].sources; // List<String>
        }
        return data;
    }

    @Test(dataProvider = "skillsProvider")
    public void addSkillTest(String skillName, List<String> sources) {
        profilepage.openSkillsSection();
        profilepage.addSkill(skillName, sources);

        Assert.assertTrue(
                profilepage.isSkillAddedMessageDisplayed(),
                "Success message not shown after adding skill: " + skillName
        );

        System.out.println("✅ Skill \"" + skillName + "\" added successfully");
    }

    @AfterSuite(alwaysRun = true)
    public void openDetailsAfterAll() {
        if (profilepage != null) {
            profilepage.openSkillsDetailsPage();
            System.out.println("All skill tests finished.");
        }
    }
}