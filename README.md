# LinkedIn Skills Automation

> A data-driven Selenium automation project — with its own local web UI — that turns updating LinkedIn profile skills into a repeatable, one-click workflow instead of a manual, repetitive chore.

![Java](https://img.shields.io/badge/Java-25-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.41.0-43B02A)
![TestNG](https://img.shields.io/badge/TestNG-7.11.0-red)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-C71A36)
![Gson](https://img.shields.io/badge/Gson-2.13.2-blue)
![Node.js](https://img.shields.io/badge/Node.js-Local%20Runner-339933)
![Allure](https://img.shields.io/badge/Allure-Screenshot%20Attachments-FF6A00)
![License](https://img.shields.io/badge/license-Educational%2FPortfolio-lightgrey)

▶️ **[Watch the full demo](https://drive.google.com/file/d/1dJxfxOSHK72rGrN-Dxgm9f-t6amElPHF/view?usp=drive_link)** — the SkillForge UI, live `mvn test` streaming, and the LinkedIn profile being updated in real time.

## 📌 Project Overview

Manually adding professional skills to a LinkedIn profile — typing each one in, checking the right certification/experience boxes, saving, repeating — is tedious once you have more than a handful. This project automates that entire flow with Selenium, driven by an external JSON skill list instead of hardcoded test steps, and wraps it in **SkillForge**, a small local web dashboard that lets you build that skill list visually and launch the automation with one click.

The project was designed with a focus on:

- Data-driven testing (skills live in JSON, not in Java)
- Page Object Model architecture
- Reusable Selenium framework utilities
- Separation of automation logic from a friendly control surface (the UI)
- Practical, demo-able automation workflow design

---

## ✨ Key Features

### 🤖 Selenium Automation Against Your Real, Logged-In Profile

`AddSkillsTest` drives a real LinkedIn session — not a fresh, anonymous browser. See [important note on how Chrome mode works](#-important-chrome-mode-attaches-to-an-existing-browser) below.

### 📄 Data-Driven Skill Management

Skills and their supporting sources (certifications, diplomas, experience entries) are stored in `src/test/java/testdata/skills.JSON` — 31 skills are pre-populated out of the box — and parsed into a `SkillData` POJO (`name` + `List<String> sources`) via Gson. Add or remove a skill by editing JSON; no Java changes required.

### 🖥️ SkillForge — a Local Web UI for the Whole Workflow

A genuinely nice touch: instead of hand-editing JSON, `ui/skills-dashboard.html` + `ui/server.js` (a tiny Node.js HTTP server, no framework) give you a dashboard to:

1. Type a skill name and hit **Add**.
2. Pick sources from a dropdown (Diploma, ISTQB, METI, plus a few personal ones like Ain Shams / Aerodynamics) — each becomes a removable chip.
3. **Download skills.JSON** to export the list, or
4. Click **Run automation** — the server overwrites `src/test/java/testdata/skills.JSON` with your list, spawns `mvn test`, and **streams the live console output** straight into an in-browser terminal.

<img width="1280" height="720" alt="Slide1" src="https://github.com/user-attachments/assets/e1596053-3c77-4c10-b7f6-f4e809b3d155" />

### 🧩 Page Object Model

`profilepage.java` centralizes every LinkedIn locator and interaction (opening the Skills form, typing a skill, checking source checkboxes, reading the confirmation toast) behind a clean `addSkill(name, sources)` API — the test itself only calls high-level page methods.

### 🛠️ Reusable Selenium Framework

`Framework.java` wraps 30+ common Selenium operations (explicit/fluent waits, clicks, dropdowns, checkboxes, alerts, window switching, drag-and-drop, numeric keypad entry, and screenshot capture with automatic Allure attachment) so page objects stay short and readable.

### 🌐 Configurable, Multi-Browser WebDriver Layer

`WebDriverHandle` supports **Chrome** (attach mode), **Edge**, and **Brave**, selected entirely through `config.properties` — no code changes to switch browsers.

### 📚 Auto-Generated Architecture Guide

`docs/generate_guide_docx.py` is a Python (`python-docx`) script that builds `LinkedIn_Skills_Automation_Architecture_Guide.docx` — a formatted Word document describing the project architecture, generated from code rather than maintained by hand.

---

## ⚠️ Important: Chrome mode attaches to an existing browser

This is the single most important thing to know before running this project. Unlike a typical Selenium setup, **Chrome mode does not launch a fresh, isolated browser.** `WebDriverHandle` connects to an **already-running** Chrome instance over its DevTools protocol:

```java
options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
driver = new ChromeDriver(options);
```

This is intentional — automating LinkedIn from a brand-new, logged-out Selenium session runs straight into LinkedIn's bot detection and login/CAPTCHA walls. Attaching to a real Chrome window that's already logged in sidesteps that entirely.

**What this means for you:** before running any test (directly or via the SkillForge UI), you must manually start Chrome with remote debugging enabled and be logged into LinkedIn in that window:

```bash
# Windows
chrome.exe --remote-debugging-port=9222

# macOS
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --remote-debugging-port=9222

# Linux
google-chrome --remote-debugging-port=9222
```

If `browser=edge` or `browser=brave`, this doesn't apply — those launch a normal, independent browser session instead.

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 25 |
| Build Tool | Apache Maven |
| UI Automation | Selenium WebDriver 4.41.0 |
| Test Runner | TestNG 7.11.0 |
| JSON Handling | Gson 2.13.2 |
| Reporting | Allure (`allure-testng`, `allure-java-commons`, `allure-rest-assured` 2.31.0) — used here for screenshot attachments via `Framework.screenshot()` |
| Local Control UI | Node.js (built-in `http` module, no external dependencies), vanilla HTML/CSS/JS |
| Documentation | Python 3 + `python-docx` (`docs/generate_guide_docx.py`) |
| Test Execution | Maven Surefire Plugin 3.1.2 + `surefire-testng` |

## Getting Started

### Prerequisites

- **JDK 25** (or lower `maven.compiler.source`/`target` in `pom.xml` to match your installed JDK)
- **Apache Maven 3.8+**
- **Chrome, Edge, or Brave** — see the [Chrome attach-mode note](#-important-chrome-mode-attaches-to-an-existing-browser) above if using `browser=chrome`
- **Node.js** (any recent LTS) — only needed if you want to use the SkillForge UI runner
- A **LinkedIn account** you're comfortable running UI automation against (this modifies your real profile's Skills section)

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/SeifZiad/LinkedIn-Skills-Automation.git
   cd LinkedIn-Skills-Automation
   ```

2. **Install dependencies**

   ```bash
   mvn clean install -DskipTests
   ```

3. **Configure your environment**

   Edit `src/main/resources/config.properties`:

   ```properties
   browser=chrome
   baseUrl=https://www.linkedin.com/in/your-profile/
   implicitWait=10
   explicitWait=15
   headless=false
   ```

   > Update `profilepage.java`'s hardcoded `baseUrl` and the skills/details page URLs to point at your own LinkedIn profile slug — they currently point at the author's profile.

## Usage

### Option A — Run via the SkillForge UI (recommended)

1. Start Chrome with remote debugging and log into LinkedIn (see above).
2. Start the local runner:

   ```bash
   cd ui
   npm start
   ```

3. Open **http://localhost:5050** in your browser.
4. Add skills, attach sources from the dropdown, then click **Run automation** and watch the live terminal until you see `BUILD SUCCESS`.

Under the hood, clicking **Run automation** does a `POST /run` to the Node server, which overwrites `src/test/java/testdata/skills.JSON` with your list and spawns `mvn test`, streaming stdout/stderr back into the page in real time.

### Option B — Run directly with Maven

1. Start Chrome with remote debugging and log into LinkedIn (see above).
2. Edit `src/test/java/testdata/skills.JSON` directly if you want a custom skill list.
3. Run the suite:

   ```bash
   mvn clean test
   ```

   (`pom.xml`'s Surefire config already points at `testng.xml`, which registers the single `AddSkillsTest` class — no extra flags needed.)

### Example: the actual test

```java
@Test(dataProvider = "skillsProvider")
public void addSkillTest(String skillName, List<String> sources) {
    profilepage.openSkillsSection();
    profilepage.addSkill(skillName, sources);

    Assert.assertTrue(
            profilepage.isSkillAddedMessageDisplayed(),
            "Success message not shown after adding skill: " + skillName
    );
}
```

Each row in `skills.JSON` becomes one TestNG data-provider iteration, so a 31-skill file runs 31 iterations of the same test — one per skill.

### Example: `skills.JSON` format

```json
{
  "skills": [
    {
      "name": "Software Testing",
      "sources": ["diploma", "istqb", "meti"]
    },
    {
      "name": "Selenium WebDriver",
      "sources": ["diploma"]
    }
  ]
}
```

`sources` values map to checkbox locators in `profilepage.selectSource()` — currently recognized keys are `diploma`, `istqb`, `meti`, `ainshams`, `lowvoltage`, `designleader`, `vicecaptain`, `aerodynamics`, and `embeddedsystems`. An unrecognized key throws `IllegalArgumentException`, so keep custom sources within this set (or extend `profilepage.java` with your own).

## Project Structure

```
LinkedIn-Skills-Automation/
├── Data/                                # Presentation assets (slide decks, demo screenshots)
├── docs/
│   ├── generate_guide_docx.py           # Generates the architecture guide as a .docx
│   └── LinkedIn_Skills_Automation_Architecture_Guide.docx
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Pages/
│   │   │   │   └── profilepage.java     # LinkedIn Skills form: open, type, select sources, save
│   │   │   └── utilis/
│   │   │       ├── ConfigReader.java    # Reads config.properties
│   │   │       ├── Framework.java       # Reusable Selenium wrappers (30+ methods) + Allure screenshot attach
│   │   │       ├── HelperClass.java     # JSON file readers (ReadFromFile, ReadSkills)
│   │   │       ├── WebDriverHandle.java # Chrome (attach-mode) / Edge / Brave driver lifecycle
│   │   │       └── POJOClass/
│   │   │           └── SkillData.java   # { name, sources } data model
│   │   └── resources/
│   │       └── config.properties        # browser, baseUrl, waits, headless
│   └── test/
│       ├── java/
│       │   └── AddSkillsTest.java       # @DataProvider over skills.JSON → addSkill() per row
│       ├── testdata/
│       │   └── skills.JSON              # 31 pre-populated skills + sources
│       └── resources/
│           └── extensions/
│               └── adblocker.crx        # Bundled ad-blocker for stable UI runs
├── ui/
│   ├── skills-dashboard.html            # SkillForge dashboard (vanilla HTML/CSS/JS)
│   ├── server.js                        # Node http server: serves UI, POST /run writes JSON + streams `mvn test`
│   ├── package.json                     # "skillforge-runner" — npm start → node server.js
│   └── README.md                        # UI-specific quick-start
├── pom.xml                              # Maven build & dependency configuration
├── testng.xml                           # TestNG suite: registers AddSkillsTest
└── .gitignore
```

## Configuration / Environment Variables

Configured through `src/main/resources/config.properties`, loaded at runtime by `ConfigReader`:

| Key | Description | Example |
|---|---|---|
| `browser` | `chrome` (attach-mode, see note above), `edge`, or `brave` | `chrome` |
| `baseUrl` | Your LinkedIn profile URL | `https://www.linkedin.com/in/your-profile/` |
| `implicitWait` | Implicit wait timeout in seconds | `10` |
| `explicitWait` | Explicit/`WebDriverWait` timeout in seconds | `15` |
| `headless` | Run headless (`--headless=new`) — **not applicable to Chrome attach-mode**, since that mode connects to a real, visible browser window | `false` |

> If you set `browser=brave`, `WebDriverHandle.java` points to a hardcoded Windows install path (`C:\Program Files\BraveSoftware\Brave-Browser\Application\brave.exe`). Update this in the source if you're on macOS/Linux or have Brave installed elsewhere.

The Node runner (`ui/server.js`) has one hardcoded value worth knowing about: it listens on **port 5050** and resolves the project root as `../` relative to itself — both are set at the top of `server.js` if you need to change them.

## A Couple of Honest Notes

- **Screenshots aren't wired into the test flow yet.** `Framework.screenshot()` exists and attaches to Allure, but `AddSkillsTest` doesn't currently call it on success or failure — a natural next step would be an `@AfterMethod` hook that calls `profilepage`'s underlying `Framework.screenshot()` automatically.
- **No `@AfterMethod`/`@AfterSuite` driver teardown per test** — `WebDriverHandle` is a singleton reused across the whole data-provider run, and `AddSkillsTest` only closes things via the `@AfterSuite` navigation step, not an explicit `quitDriver()`. Fine for a single local run; worth adding if you extend this into a CI pipeline.
- **`profilepage.java`'s locators and base URL are personalized** (author's LinkedIn slug, specific certification names like "Software Testing Diploma"). Treat this as a template — swap in your own profile URL and source labels/checkboxes before running it against your account.

## Contributing

Contributions, suggestions, and bug reports are welcome:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`.
3. Commit your changes with clear messages.
4. Push the branch and open a **Pull Request** describing the change and its motivation.
5. For bugs or ideas, feel free to open an **Issue** first to discuss the approach.

If you add a new source/checkbox to LinkedIn's Skills form, extend `profilepage.selectSource()`'s switch statement and `SOURCE_LABELS` in `skills-dashboard.html` together so the UI and the automation stay in sync.

## License

This project is intended for **educational and portfolio purposes**. No formal license file is currently published in the repository — if you plan to reuse or redistribute this code, please reach out to the repository owner ([SeifZiad](https://github.com/SeifZiad)) for clarification.
