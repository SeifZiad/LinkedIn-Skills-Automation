package utilis;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class Framework {
        private WebDriver webdriver;

        public Framework(WebDriver driver){
            this.webdriver=driver;
            implicitWait();
        }

        public void pressEnter() {
            new Actions(webdriver)
                    .sendKeys(Keys.ENTER)
                    .perform();
        }

        public void initializeBrowser() {
            this.webdriver =  WebDriverHandle.initializeDriver(ConfigReader.getBrowser());
            this.webdriver.manage().window().maximize();
            implicitWait();
        }

        public  void implicitWait() {
            webdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        }

        public void explicitWait(By locator){
            new WebDriverWait(webdriver,Duration.ofSeconds(ConfigReader.getExplicitWait()))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        }

        public void fluentWait(By locator, int pollingMillis, String
                timeoutMessage){
            new FluentWait<>(webdriver).withTimeout(Duration.ofSeconds(ConfigReader.getExplicitWait()))
                    .withMessage(timeoutMessage)
                    .pollingEvery(Duration.ofMillis(pollingMillis))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        }

        public void navigateToURL (String url){
            webdriver.get(url);
        }

        public String getPageTitle(){
            return webdriver.getTitle();
        }

         public boolean isDisplayed(By locator) {
               explicitWait(locator);
            return webdriver.findElement(locator).isDisplayed();}

    public String getCurrentURL(){
            return webdriver.getCurrentUrl();
        }
        public void click(By locator){
            explicitWait(locator);
            webdriver.findElement(locator).click();
        }
        public void rightClick(By locator){
            WebElement element=webdriver.findElement(locator);
            Actions a1= new Actions(webdriver);
            a1.contextClick(element).perform();
        }
        public void sendKeys(By locator, String text) {
            webdriver.findElement(locator).sendKeys(text);
        }
        public String getText(By locator){
           return webdriver.findElement(locator).getText();
        }


        public void selectDropdownByVisibleText(By locator, String visibleText){
            Select select = new Select(webdriver.findElement(locator));
            select.selectByVisibleText(visibleText);
        }
        public void selectDropdownByValue(By locator, String value){
            Select select = new Select(webdriver.findElement(locator));
            select.selectByValue(value);
        }
        public void selectDropdownByIndex(By locator, int index){
            Select select = new Select(webdriver.findElement(locator));
            select.selectByIndex(index);
        }
        public  void dragAndDrop(By sourceLocator, By targetLocator){
            Actions a1= new Actions(webdriver);
            a1.dragAndDrop(webdriver.findElement(sourceLocator),webdriver.findElement(targetLocator)).perform();
        }
        public void checkCheckbox(By locator){
            WebElement checkbox=webdriver.findElement(locator);
            if (!checkbox.isSelected()){
                click(locator);
            }
        }
        public void uncheckCheckbox(By locator){
            WebElement checkbox=webdriver.findElement(locator);
            if (checkbox.isSelected()){
                click(locator);
            }
        }

        public void selectRadioButton(By locator){
            WebElement radiobutton=webdriver.findElement(locator);
            if (!radiobutton.isSelected()){
                click(locator);
            }
        }

        public void switchToWindowByTitle(String windowTitle){
            String currentWindow = webdriver.getWindowHandle();
            Set<String> allWindows = webdriver.getWindowHandles();

            for (String window : allWindows) {
                webdriver.switchTo().window(window);
                if (webdriver.getTitle().equals(windowTitle)) {
                    System.out.println("Edges: Switched to window with title: " + windowTitle);
                    return;
                }
            }
                webdriver.switchTo().window(currentWindow);
        }

        public void switchToWindowByHandle(String windowHandle) {
            Set<String> allWindows = webdriver.getWindowHandles();
            if (allWindows.contains(windowHandle)) {
                webdriver.switchTo().window(windowHandle);
            }
        }

        public void closeCurrentWindow(){webdriver.close();}

        public void navigateBack(){ webdriver.navigate().back();}

        public void navigateForward(){webdriver.navigate().forward();}

        public void refreshPage(){webdriver.navigate().refresh();}

//        public void scrollToElement(By locator){
//            WebElement element = webdriver.findElement(locator);
//            Actions a1= new Actions(webdriver);
//            a1.scrollToElement(element).perform();
//            explicitWait(locator);
    //        }
        public void scrollUntilVisible(By locator) {

            JavascriptExecutor js =
                    (JavascriptExecutor) webdriver;

            WebDriverWait wait =
                    new WebDriverWait(
                            webdriver,
                            Duration.ofSeconds(30)
                    );

            wait.until(driver -> {

                try {

                    WebElement element =
                            driver.findElement(locator);

                    if (element.isDisplayed()) {

                        return true;
                    }

                    // Scroll down
                    js.executeScript(
                            "window.scrollBy(0, 500);"
                    );

                    return false;

                } catch (NoSuchElementException e) {

                    // Element not found yet → continue scrolling
                    js.executeScript(
                            "window.scrollBy(0, 500);"
                    );

                    return false;
                }
            });
            }


        public void acceptAlert(){
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(ConfigReader.getImplicitWait()));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }

        public void dismissAlert(){
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(ConfigReader.getImplicitWait()));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();

        }

        public String getAlertText(){
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(ConfigReader.getImplicitWait()));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();

            return text;
        }

        public void sendTextToAlert(String text){
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(ConfigReader.getImplicitWait()));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(text);
            alert.accept();

        }
    // take a Screenshot

        public void closeBrowser(){webdriver.quit();}

    public void hoverClickEnterNumber(By locator, int number) {
        Actions actions = new Actions(webdriver);
        WebElement element = webdriver.findElement(locator);

        actions.moveToElement(element).click().perform();
        actions.sendKeys(Keys.BACK_SPACE).perform();

        // Enter number digit by digit using NUMPAD keys
        String numberStr = String.valueOf(number);
        for (char digit : numberStr.toCharArray()) {
            switch (digit) {
                case '0': actions.sendKeys(Keys.NUMPAD0).perform(); break;
                case '1': actions.sendKeys(Keys.NUMPAD1).perform(); break;
                case '2': actions.sendKeys(Keys.NUMPAD2).perform(); break;
                case '3': actions.sendKeys(Keys.NUMPAD3).perform(); break;
                case '4': actions.sendKeys(Keys.NUMPAD4).perform(); break;
                case '5': actions.sendKeys(Keys.NUMPAD5).perform(); break;
                case '6': actions.sendKeys(Keys.NUMPAD6).perform(); break;
                case '7': actions.sendKeys(Keys.NUMPAD7).perform(); break;
                case '8': actions.sendKeys(Keys.NUMPAD8).perform(); break;
                case '9': actions.sendKeys(Keys.NUMPAD9).perform(); break;
            }
        }
    }

    public void screenshot() {
        // 1. Format timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(dtf);

        // 2. Folder name
        String folderName = "screenshots";
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 3. Capture screenshot
        File src = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
        File dest = new File(directory, "screenshot_" + timestamp + ".png");

        try {
            // 4. Save file locally
            FileHandler.copy(src, dest);

            // 5. Attach to Allure (use the saved file)
            Allure.addAttachment("Screenshot - " + timestamp, Files.newInputStream(dest.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }

    public void clickAndSendKeys(By locator, String text) {

        WebElement element = webdriver.findElement(locator);

        Actions actions = new Actions(webdriver);

        actions
                .moveToElement(element)
                .click()
                .sendKeys(text)
                .perform();
    }


}

