# LinkedIn Skills Automation

> A data-driven Selenium automation project that transforms the process of managing LinkedIn profile skills into a repeatable, configurable, and maintainable workflow.

![Java](https://img.shields.io/badge/Java-25-orange)
![Selenium](https://img.shields.io/badge/Selenium-WebDriver-green)
![TestNG](https://img.shields.io/badge/TestNG-Testing-red)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-C71A36)
![Gson](https://img.shields.io/badge/Gson-JSON%20Processing-blue)
![Node.js](https://img.shields.io/badge/Node.js-UI%20Component-green)
![License](https://img.shields.io/badge/License-Educational-lightgrey)

---

## 📌 Project Overview

Managing a large number of professional skills manually can be repetitive and time-consuming.

This project automates the process of adding skills to a LinkedIn profile using a structured, data-driven approach.

Instead of hardcoding individual skills inside the test, the automation reads the required skills from an external JSON file and processes them sequentially.

The project was designed with a focus on:

- Maintainability
- Reusability
- Data-driven testing
- Separation of test data from automation logic
- Page Object Model architecture
- Reusable Selenium framework utilities
- Practical automation workflow design

---

## ✨ Key Features

### 🤖 Selenium Web Automation

Automates browser interaction with the LinkedIn profile and skills-management workflow.

### 📄 Data-Driven Skill Management

Skills are stored externally in JSON, allowing the data to be updated without modifying the automation logic.

### 🧩 Page Object Model

Page-specific locators and actions are separated from test execution logic.

### 🛠️ Reusable Automation Framework

Common browser operations are centralized inside reusable framework utilities.

### 🌐 Multi-Browser Support

The WebDriver management layer supports configurable browser execution.

### 🖥️ Custom Skills UI

The project also includes a dedicated UI component for managing and presenting the skills data in a more structured way.

### 📚 Documentation Support

The project includes documentation utilities to help generate project-related documentation.

---

# 🏗️ Project Architecture

```text
LinkedIn-Skills-Automation
│
├── Data/
│   └── Project data and supporting resources
│
├── docs/
│   └── Documentation generation utilities
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Pages/
│   │   │   │   └── profilepage.java
│   │   │   │
│   │   │   └── utilis/
│   │   │       ├── ConfigReader.java
│   │   │       ├── Framework.java
│   │   │       ├── HelperClass.java
│   │   │       └── WebDriverHandle.java
│   │   │
│   │   └── resources/
│   │       └── config.properties
│   │
│   └── test/
│       ├── java/
│       │   └── test.java
│       │
│       ├── testdata/
│       │   └── skills.JSON
│       │
│       └── resources/
│           └── extensions/
│
├── ui/
│   ├── skills-dashboard.html
│   ├── server.js
│   ├── package.json
│   └── README.md
│
├── pom.xml
├── testng.xml
└── .gitignore

# Automation Workflow
┌─────────────────────────┐
│   Start Test Execution  │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Initialize WebDriver    │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Open LinkedIn Profile   │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Navigate to Skills Page │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Read Skills from JSON   │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Process Current Skill   │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Enter Skill & Save      │
└────────────┬────────────┘
             │
             ▼
      ┌──────────────┐
      │ More Skills?  │
      └──────┬───────┘
             │
       Yes   │   No
        ┌────▼────┐  ▼
        │ Next    │  End
        │ Skill   │
        └─────────┘

📊 Data-Driven Design

The skill data is maintained separately from the automation logic.

Example:

{
  "skills": [
    {
      "name": "Software Testing",
      "sources": [
        "ISTQB Foundation Level",
        "Software Testing Diploma"
      ]
    },
    {
      "name": "Selenium WebDriver",
      "sources": [
        "Software Testing Diploma",
        "Automation Project"
      ]
    }
  ]
}

This approach provides several advantages:

Skills can be added or removed without changing Java code.
The same automation flow can process different datasets.
Skill information can be extended with additional metadata.
Test data remains independent from test implementation.

🖥️ UI Component

The repository also includes a dedicated UI component for presenting and interacting with the skills data.

The UI layer provides a more visual way to organize the skills workflow and complements the Selenium automation project.

UI Layer
    │
    ▼
Skills Dashboard
    │
    ▼
Skills Data
    │
    ▼
Automation Workflow
