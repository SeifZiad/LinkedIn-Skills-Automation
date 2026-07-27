package utilis;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.awt.*;
import java.io.File;
import java.time.Duration;

public class WebDriverHandle {

    private static WebDriver driver;


    public static WebDriver initializeDriver(String browser) {

        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);
        System.setProperty("webdriver.chrome.silentOutput", "true");
        // Singleton: Don't create a new driver if one already exists
        if (driver != null) {
            return driver;
        }


        String extensionPath =
                "src/test/resources/extensions/adblocker.crx";


        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();


            /*
             * ATTACH TO EXISTING CHROME
             *
             * Chrome must already be started using:
             *
             * chrome.exe
             * --remote-debugging-port=9222
             *
             * Selenium attaches to that browser instead
             * of creating a new Chrome session.
             */
            options.setExperimentalOption(
                    "debuggerAddress",
                    "127.0.0.1:9222"
            );


            /*
             * Do NOT use:
             *
             * options.addArguments("--user-data-dir=...");
             *
             * here.
             *
             * The browser is already running and Selenium
             * is simply attaching to it.
             */


            driver = new ChromeDriver(options);


        }


        else if (browser.equalsIgnoreCase("edge")) {

            EdgeOptions options = new EdgeOptions();


            File extension =
                    new File(extensionPath);


            if (extension.exists()) {

                options.addExtensions(extension);
            }


            if (ConfigReader.isHeadless()) {

                options.addArguments(
                        "--headless=new"
                );
            }


            driver = new EdgeDriver(options);


        }


        else if (browser.equalsIgnoreCase("brave")) {

            ChromeOptions braveOptions =
                    new ChromeOptions();


            braveOptions.setBinary(
                    "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe"
            );


            braveOptions.addArguments(
                    "--disable-popup-blocking"
            );


            braveOptions.addArguments(
                    "--disable-notifications"
            );


            braveOptions.addArguments(
                    "--disable-blink-features=AutomationControlled"
            );


            braveOptions.addArguments(
                    "--log-level=3"
            );


            braveOptions.addArguments(
                    "--disable-logging"
            );


            if (ConfigReader.isHeadless()) {

                braveOptions.addArguments(
                        "--headless=new"
                );
            }


            driver =
                    new ChromeDriver(braveOptions);
        }


        else {

            throw new IllegalArgumentException(
                    "Browser not supported: " + browser
            );
        }


        /*
         * Browser window configuration
         *
         * This is not required when attaching to an
         * existing Chrome browser, but it is kept for
         * your other browser modes.
         */
        try {

            java.awt.Dimension screenSize =
                    Toolkit.getDefaultToolkit()
                            .getScreenSize();


            int fullWidth =
                    (int) screenSize.getWidth();


            int browserHeight =
                    (int) (
                            screenSize.getHeight()
                                    * 0.66
                    );


            driver.manage()
                    .window()
                    .setSize(
                            new Dimension(
                                    fullWidth,
                                    browserHeight
                            )
                    );

        } catch (Exception e) {

            System.out.println(
                    "Could not resize browser window."
            );
        }


        /*
         * Implicit wait
         */
        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(
                                ConfigReader
                                        .getImplicitWait()
                        )
                );


        return driver;
    }


    public static WebDriver getDriver() {

        if (driver == null) {

            throw new IllegalStateException(
                    "Driver not initialized. " +
                            "Call initializeDriver() first."
            );
        }


        return driver;
    }


    public static void quitDriver() {

        if (driver != null) {

            driver.quit();

            driver = null;
        }
    }
}