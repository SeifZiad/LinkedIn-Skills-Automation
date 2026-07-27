package Pages;

import utilis.Framework;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class profilepage {
    protected Framework framework;
    protected final String baseUrl="https://www.linkedin.com/in/seif-ziad/";

    public profilepage(WebDriver driver) {
        framework = new Framework(driver);
    }

    protected final By skillbtn = By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div/main/div/div/section/div/div/div[7]/div/div/div/div/section/div/div/div[1]/div/a[1]/span");
    protected final By skillshead= By.xpath("//h2[contains(normalize-space(.),'Skills')]");
    protected final By skillstxt = By.cssSelector("input[placeholder^=\"Skill\"]");
    protected final By savebtn = By.xpath("//button[contains(., 'Save')]");
    
    protected final By sw_diploma_checkbox = By.xpath("//p[contains(., 'Software Testing Diploma')]/preceding::label[1]");
    protected final By istqb_checkbox =By.xpath("//p[contains(., 'Certified Tester Foundation Level')]/preceding::label[1]");
    protected final By meti_checkbox = By.xpath("//p[contains(., 'Simulation Testing Engineer')]/preceding::label[1]");    
    protected final By skillAddedMsg =By.xpath("//p[contains(., 'Your skill has been added')]");

    ////////////////////////////////// sources for ui purposes //////////////////////////////////
    protected final By ainshams = By.xpath("//p[contains(., 'Ain Shams')]/preceding::label[1]");
    protected final By lowvoltage = By.xpath("//p[contains(., 'Low Voltage')]/preceding::label[1]");
    protected final By designleader = By.xpath("//p[contains(., 'Design Team Leader')]/preceding::label[1]");
    protected final By vicecaptain = By.xpath("//p[contains(., 'Vice Team Captain')]/preceding::label[1]");
    protected final By aerodyanmics = By.xpath("//p[contains(., 'Aerodynamics')]/preceding::label[1]");
    protected final By embeddedsystems = By.xpath("//p[contains(., 'Embedded Systems')]/preceding::label[1]");
    
    
    public boolean isSkillAddedMessageDisplayed() {
        framework.explicitWait(skillAddedMsg);
        return framework.isDisplayed(skillAddedMsg);
    }

    public void clickSkillsOption() {
        framework.click(skillbtn);
    }

    public void openSkillsSection() {
        framework.navigateToURL("https://www.linkedin.com/in/seif-ziad/skills/edit/forms/new/");
    }

    public void selectSource(String source) {
        switch (source.toLowerCase()) {
            case "diploma":
                framework.scrollUntilVisible(sw_diploma_checkbox);
                framework.click(sw_diploma_checkbox);
                break;
            case "meti":
                framework.scrollUntilVisible(meti_checkbox);
                framework.click(meti_checkbox);
                break;
            case "istqb":
                framework.scrollUntilVisible(istqb_checkbox);
                framework.click(istqb_checkbox);
                break;
            case "ainshams":
                framework.scrollUntilVisible(ainshams);
                framework.click(ainshams);
                break;
            case "lowvoltage":
                framework.scrollUntilVisible(lowvoltage);
                framework.click(lowvoltage);
                break;
            case "designleader":
                framework.scrollUntilVisible(designleader);
                framework.click(designleader);
                break;
            case "vicecaptain":
                framework.scrollUntilVisible(vicecaptain);
                framework.click(vicecaptain);
                break;
            case "aerodyanmics":
            case "aerodynamics":
                framework.scrollUntilVisible(aerodyanmics);
                framework.click(aerodyanmics);
                break;
            case "embeddedsystems":
                framework.scrollUntilVisible(embeddedsystems);
                framework.click(embeddedsystems);
                break;
            default:
                throw new IllegalArgumentException("Unknown source: " + source);
        }
    }

    public void selectSources(List<String> sources) {
        if (sources == null) {
            return;
        }
        for (String source : sources) {
            selectSource(source);
        }
    }

    public void addSkill(String skill, List<String> sources) {
        framework.clickAndSendKeys(skillstxt, skill);
        framework.pressEnter();
        if (sources != null && !sources.isEmpty()) {
            selectSources(sources);
        }
        framework.click(savebtn);
    }
    public void openSkillsDetailsPage() {
    framework.navigateToURL(
            "https://www.linkedin.com/in/seif-ziad/details/skills/"
    );
    }

}

