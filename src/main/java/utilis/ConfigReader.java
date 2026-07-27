package utilis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

    public class ConfigReader {

        private static Properties properties;

        static {
            try {
                FileInputStream file = new FileInputStream("src/main/resources/config.properties");
                properties = new Properties();
                properties.load(file);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config.properties file");
            }
        }

        public static String getBrowser() {
            return properties.getProperty("browser");
        }

        public static String getBaseUrl() {
            return properties.getProperty("baseUrl");
        }

        public static int getImplicitWait() {
            return Integer.parseInt(properties.getProperty("implicitWait"));
        }

        public static int getExplicitWait() {
            return Integer.parseInt(properties.getProperty("explicitWait"));
        }

        public static boolean isHeadless() { return Boolean.parseBoolean (properties.getProperty("headless"));}
    }

