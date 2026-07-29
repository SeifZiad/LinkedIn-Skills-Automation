# 🚀 LinkedIn Skills Automation & Test Framework

An enterprise-grade, end-to-end web UI & API test automation framework that incorporates a lightweight, web-based UI manager (**SkillForge**) to construct, validate, and automatically apply skills to a LinkedIn profile. Built on Java 17 using standard Page Object Model (POM) principles, the framework combines Selenium WebDriver for UI automation, REST Assured for backend validation, dynamic JSON-driven data parameterization, interactive Allure reporting, and Jira/Zephyr test management integration.

---

## 🖥️ SkillForge UI Tool & Workflow

The framework provides **SkillForge**, an intuitive visual builder designed to convert manual LinkedIn updates into a repeatable, automated workflow:

## 🖥️ SkillForge UI Tool & Workflow

The framework provides **SkillForge**, an intuitive visual builder designed to convert manual LinkedIn updates into a repeatable, automated workflow.

<p align="center">
  <img src="https://github.com/user-attachments/assets/c9557500-58b1-4ad0-81b5-631c659f1015" alt="SkillForge UI Tool" width="100%" />
</p>

> 🎥 **Video Demo**: Watch the full interactive workflow in action [here on Google Drive](https://drive.google.com/file/d/1dJxfxOSHK72rGrN-Dxgm9f-t6amElPHF/view?usp=drive_link).

### 🔄 Workflow Process:

1. 🛠️ **Add Skills**: Enter target LinkedIn skills directly into the interface.
2. 🔗 **Attach Sources**: Connect each skill to relevant background items (e.g., *Diploma*, *ISTQB*, *METI*).
3. 📦 **Export JSON**: Automatically generate the parameterized `skills.json` test-data file.
4. ▶️ **Run Automation**: Launch the underlying Selenium + TestNG test suite with live terminal logging.

---

## ✨ Key Features

* 💻 **SkillForge Web Interface**: Interactive HTML/CSS/JS frontend for building skill lists, assigning certification sources, exporting JSON configurations, and triggering test execution.
* 🏗️ **Page Object Model (POM)**: Clean abstraction of web elements and user actions ensuring high maintainability, low duplication, and strict object encapsulation.
* 🌐 **Selenium WebDriver & TestNG Integration**: Robust, browser-driven UI execution with flexible test suite management, parallel execution support, and lifecycle annotations.
* ⚡ **REST Assured API Validation**: Backend integration for testing API endpoints, schema validation, HTTP response assertions, and profile state verifications.
* 📊 **Data-Driven Architecture**: Fully parameterized via structured `JSON` files, allowing complete separation of test data from test execution logic.
* 📈 **Allure & Jira / Zephyr Integration**: Interactive HTML reporting with request/response logs, automatic failure screenshots, and seamless synchronization with Jira/Zephyr scale test management.
* ⚙️ **Multi-Environment Configuration**: Centralized environment properties to easily switch between local, staging, or CI/CD headless execution modes.

---

## 🛠️ Tech Stack

* **Programming Language**: Java 17+
* **UI Automation**: Selenium WebDriver 4.16.1
* **API Testing**: REST Assured 5.3.2
* **Test Framework**: TestNG 7.8.0
* **Build Tool**: Apache Maven
* **Data Parser**: Jackson Databind / Gson
* **Reporting**: Allure Test Report 2.24.0 & Log4j2
* **Frontend UI Manager**: HTML5, CSS3, JavaScript (ES6)
* **Test Management**: Jira / Zephyr Squad

---

## 🔍 Deep Dive: Source Code Architecture (`src/` Breakdown)

The repository follows standard Maven project architecture with clear separation between core test engines (`src/main`) and test cases/listeners (`src/test`).

```text
Automation-API-Testing-Project/
├── ui/                           # SkillForge Web Frontend (HTML/CSS/JS)
├── src/
│   ├── main/
│   │   ├── java/com/qa/
│   │   │   ├── api/             # REST Assured Engine & Endpoint Specifications
│   │   │   │   ├── builder/     # Request/Response Spec Builders
│   │   │   │   ├── clients/     # API Client abstractions
│   │   │   │   └── models/      # POJO / DTO classes for serialization
│   │   │   ├── config/          # Centralized Property & Environment Loaders
│   │   │   ├── driver/          # Thread-safe WebDriver Factory & Lifecycle
│   │   │   ├── pages/           # Page Object Model (POM) Web Page classes
│   │   │   │   ├── base/        # BasePage wrapper with explicit waits
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── ProfilePage.java
│   │   │   │   └── SkillsPage.java
│   │   │   └── utils/           # Helper Utilities (JSON Reader, Screenshot, Allure)
│   │   └── resources/           # Configuration files (config.properties, log4j2.xml)
│   └── test/
│       ├── java/com/qa/
│       │   ├── listeners/       # TestNG & Allure Execution Listeners
│       │   └── tests/           # Test Suites & Test Cases
│       │       ├── base/        # BaseTest class (Driver setup & tearDown)
│       │       ├── api/         # Backend API Verification Tests
│       │       └── ui/          # End-to-End Skill Automation UI Tests
│       └── resources/           # Test Data & Execution XML Suites
│           ├── testdata/        # skills.json test data input
│           └── suites/          # testng.xml, testng-api.xml, testng-ui.xml

```

---

### 🏛️ `src/main/java` Core Component Modules

#### 1. `com.qa.driver` — Thread-Safe Driver Management

* **`DriverManager.java`**: Manages `ThreadLocal<WebDriver>` instances to ensure safe parallel execution across multiple browser instances.
* **`DriverFactory.java`**: Instantiates and configures `ChromeDriver`, `FirefoxDriver`, or `EdgeDriver` based on property flags (e.g., Headless mode, Window Maximization, Incognito).

#### 2. `com.qa.pages` — Page Object Model (POM) Architecture

* **`BasePage.java`**: Base encapsulation class containing reusable explicit wait strategies (`ExpectedConditions`), web element interactions (`safeClick`, `typeText`, `scrollToElement`), and JS Executer utilities.
* **`LoginPage.java`**: Encapsulates LinkedIn login interactions, credentials input fields, and multi-factor/captcha detection assertions.
* **`ProfilePage.java`**: Models LinkedIn user profile navigation, direct access to skill modal dialogues, and skill verification panels.
* **`SkillsPage.java`**: Contains logic for finding skill search fields, attaching certification sources/tags (*Diploma*, *ISTQB*, *METI*), saving skill updates, and verifying DOM updates.

#### 3. `com.qa.api` — REST Assured Testing Core

* **`SpecBuilder.java`**: Provides pre-configured `RequestSpecBuilder` and `ResponseSpecBuilder` instances (Base URI, Headers, OAuth Tokens/Cookies, Content-Type `application/json`).
* **`SkillsApiClient.java`**: Wrapper around REST Assured HTTP methods (`GET`, `POST`, `DELETE`) to query or modify profile data programmatically via backend endpoints.
* **`models/`**: Strongly typed Java POJOs (Plain Old Java Objects) used for Jackson serialization and deserialization of JSON payloads.

#### 4. `com.qa.utils` — Helper & Data Utilities

* **`JsonDataReader.java`**: Reads `skills.json` and parses JSON objects directly into TestNG `@DataProvider` dynamic arrays or Java List Data Structures.
* **`AllureReportListener.java`**: Intercepts TestNG event hooks to attach step logs, request payloads, and full-resolution failure screenshots directly to Allure reports.
* **`ConfigReader.java`**: Singleton reader for fetching keys from `config.properties`.

---

### 🧪 `src/test/java` Test Implementation Breakdown

#### 1. `tests.base.BaseTest`

Serves as the root class for all UI and API test classes:

* `@BeforeMethod`: Initializes the `WebDriver` via `DriverFactory`, navigates to the base URL, and sets implicit wait bounds.
* `@AfterMethod`: Takes a screenshot on test failure via `AllureReportListener`, attaches logs, and safely closes the driver session using `DriverManager.quitDriver()`.

#### 2. `tests.ui.LinkedInSkillsTest`

* Execution entry point for LinkedIn automation.
* Reads inputs from `skills.json`.
* Executes login sequence, navigates to the Skills section, iteratively inputs skills and links sources, then validates successful skill addition on the UI.

#### 3. `tests.api.SkillsApiTest`

* Validates endpoint response codes (`200 OK`, `201 Created`, `400 Bad Request`).
* Performs JSON schema validation against API responses.
* Verifies skill payload integrity independently of the web browser.

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed on your local machine:

* **JDK (Java Development Kit)**: `17` or higher
* **Apache Maven**: `3.8+`
* **Google Chrome**: Latest version (or matching browser driver)
* **Git**: CLI installed

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/SeifZiad/Automation-API-Testing-Project.git
cd Automation-API-Testing-Project

```


2. **Install dependencies:**
```bash
mvn clean install -DskipTests

```


3. **Configure Environment Parameters:**
Create or edit `src/main/resources/config.properties`:
```properties
linkedin.url=https://www.linkedin.com
linkedin.username=YOUR_LINKEDIN_EMAIL
linkedin.password=YOUR_LINKEDIN_PASSWORD
browser=chrome
headless=false
timeout=10

```



---

## 💡 Usage & Execution Guide

### 1. Using SkillForge UI Manager

1. Launch `ui/index.html` in your browser.
2. Build your target list of skills and assign certification sources (*Diploma*, *ISTQB*, *METI*).
3. Click **Download skills.json** and place the generated file into `src/test/resources/testdata/skills.json`.
4. Run the automated suite via command line.

### 2. Command Line Execution

* **Run all automated tests:**
```bash
mvn clean test

```


* **Run specific TestNG Suite (UI or API):**
```bash
# Run UI Skills Automation Suite
mvn test -DsuiteXmlFile=src/test/resources/suites/testng-ui.xml

# Run API Validation Suite
mvn test -DsuiteXmlFile=src/test/resources/suites/testng-api.xml

```


* **Run in Headless Mode:**
```bash
mvn test -Dheadless=true

```



### 3. Reporting & Visualization

Generate and open the interactive Allure HTML report:

```bash
# Generate report files
mvn allure:report

# Serve live Allure report on local server
mvn allure:serve

```

---

## ⚙️ Configuration & Test Data Example

The framework consumes structured JSON generated by **SkillForge** located at `src/test/resources/testdata/skills.json`:

```json
[
  {
    "skillName": "Software Testing",
    "sources": [
      "Software Testing Diploma - ISTQB - METI"
    ]
  },
  {
    "skillName": "Test Automation",
    "sources": [
      "Software Testing Diploma - ISTQB - METI"
    ]
  }
]

```

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

1. **Fork** the repository.
2. Create a feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a **Pull Request**.

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](https://www.google.com/search?q=LICENSE) file for details.
