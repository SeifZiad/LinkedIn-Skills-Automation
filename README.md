# 🚀 LinkedIn Skills Automation & Test Framework

An end-to-end web UI test automation framework that incorporates a lightweight, web-based UI manager (**SkillForge**) to construct, validate, and automatically apply skills to a LinkedIn profile. Built on Java using Page Object Model (POM) principles, the framework leverages Selenium WebDriver for web automation, JSON data parameterization, and Allure reporting for execution insights.

---

## 🖥️ SkillForge UI Tool & Workflow

The framework provides **SkillForge**, an intuitive visual builder designed to convert manual LinkedIn updates into a repeatable, automated workflow.
<img width="1280" height="720" alt="Slide1" src="https://github.com/user-attachments/assets/fcf2de1f-86d3-4bcc-a4b2-fd3423f709ff" />


> 🎥 **Video Demo**: Watch the full interactive workflow in action [here on Google Drive](https://drive.google.com/file/d/1dJxfxOSHK72rGrN-Dxgm9f-t6amElPHF/view?usp=drive_link).

### 🔄 Workflow Process:

1. 🛠️ **Add Skills**: Enter target LinkedIn skills directly into the interface.
2. 🔗 **Attach Sources**: Connect each skill to relevant background items (e.g., *Diploma*, *ISTQB*, *METI*).
3. 📦 **Export JSON**: Automatically generate the parameterized `skills.json` test-data file.
4. ▶️ **Run Automation**: Launch the underlying Selenium + TestNG test suite with live terminal logging.

---

## ✨ Key Features

* 💻 **SkillForge Web Interface**: Interactive HTML/CSS/JS frontend for building skill lists, assigning certification sources, exporting JSON configurations, and launching execution.
* 🏗️ **Page Object Model (POM)**: Clean abstraction of web elements (`Pages/`) and user interactions to maximize reusability and maintainability.
* 🌐 **Selenium WebDriver & TestNG Integration**: Browser-driven execution engine with TestNG annotations, test management, and lifecycle hooks.
* 📊 **Data-Driven Parameterization**: Parameterized via structured JSON test data, mapped seamlessly into Java objects (`POJOClass/`) for flexible execution.
* 📈 **Allure Reporting**: Interactive test execution reports with step-level logging and failure screenshots.
* 🧩 **Custom Browser Extensions Support**: Loads customized Chrome extensions (located in `src/test/resources/extensions/`) to streamline browser execution.

---

## 🛠️ Tech Stack

* **Programming Language**: Java 17+
* **UI Automation**: Selenium WebDriver
* **Test Runner**: TestNG
* **Build Tool**: Apache Maven
* **Data Parsing**: JSON / Gson (POJO Mapping)
* **Reporting**: Allure Framework & Log4j
* **Frontend UI Manager**: HTML5, CSS3, JavaScript (ES6)

---

## 🔍 Deep Dive: Source Code Architecture (`src/` Breakdown)

```text
LinkedIn-Skills-Automation/
├── docs/                         # Project documentation and assets
├── ui/                           # SkillForge Web Frontend Manager (HTML/CSS/JS)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Pages/           # Page Object Model (POM) classes (UI interactions)
│   │   │   └── utilis/
│   │   │       └── POJOClass/   # Plain Old Java Objects for JSON data binding
│   │   └── resources/           # Application configuration files (config.properties)
│   └── test/
│       ├── java/
│       │   └── testdata/        # TestNG test classes and execution logic
│       └── resources/
│           └── extensions/      # Browser extensions (e.g., adblocker .crx files)
├── pom.xml                       # Maven build dependencies & plugins
├── testng.xml                    # Test execution suite configuration
└── README.md                     # Documentation

```

---

### 🏛️ Component Breakdown

#### 1. `src/main/java/Pages/` — Page Object Model (POM)

Contains web page encapsulation classes modeling LinkedIn components, login workflows, profile skill sections, and dialog elements to isolate locator logic from test execution.

#### 2. `src/main/java/utilis/POJOClass/` — Data Models

Defines Java model classes matching the JSON schema produced by **SkillForge**, enabling automated deserialization of skills, experience tags, and certification sources into Java objects.

#### 3. `src/main/resources/` — Project Resources

Houses environment properties (`config.properties`) for configuring browser parameters, wait thresholds, base URLs, and target credentials.

#### 4. `src/test/java/testdata/` — Test Execution

Houses TestNG test cases that parse the generated JSON payload, launch the web driver, iterate over skill lists, and interact with the LinkedIn UI.

#### 5. `src/test/resources/extensions/` — Extensions Setup

Contains custom Chrome extension files (`.crx`) pre-configured to load into the WebDriver instance during suite setup (e.g., suppressing overlays or popups).

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed locally:

* **JDK (Java Development Kit)**: `17` or higher
* **Apache Maven**: `3.8+`
* **Google Chrome**: Latest version
* **Git**: Installed and configured

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/SeifZiad/LinkedIn-Skills-Automation.git
cd LinkedIn-Skills-Automation

```


2. **Install dependencies:**
```bash
mvn clean install -DskipTests

```


3. **Configure environment settings:**
Update your configurations in `src/main/resources/config.properties`:
```properties
linkedin.url=https://www.linkedin.com
browser=chrome
implicitWait=10
explicitWait=15
headless=false

```



---

## 💡 Usage & Execution Guide

### 1. Generating Test Data with SkillForge UI

1. Launch `ui/index.html` in your browser.
2. Enter desired skills and select corresponding source tags (*Diploma*, *ISTQB*, *METI*).
3. Click **Download skills.json** and place the generated JSON file into your test resources directory.

### 2. Command Line Execution

* **Execute the TestNG automation suite:**
```bash
mvn clean test

```


* **Execute using a specific TestNG XML suite:**
```bash
mvn test -DsuiteXmlFile=testng.xml

```



### 3. Allure Reporting

Generate and view the execution report:

```bash
# Generate report files
mvn allure:report

# Serve live report locally
mvn allure:serve

```

---

## ⚙️ Test Data Example

SkillForge outputs structured JSON data read by `POJOClass` mapping:

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
