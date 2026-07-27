# SkillForge UI + Local Runner

Control panel for your LinkedIn skills automation.

## What it does

1. You add skills and (optional) sources in the browser.
2. **Run automation** sends that list to this local server.
3. The server overwrites `src/test/java/testdata/skills.JSON`.
4. It runs `mvn test` and streams the log into the UI terminal.
5. Your existing Selenium/TestNG code updates the LinkedIn profile.

## Prerequisites

- Node.js installed
- Maven on PATH
- Chrome already started with remote debugging and logged into LinkedIn:

```text
chrome.exe --remote-debugging-port=9222
```

## Start

From the `ui` folder:

```powershell
cd D:\ASU\Testing\LinkedIn_skills\ui
npm start
```

Then open:

```text
http://localhost:5050/
```

## Demo flow

1. Start Chrome (port 9222) and log in.
2. Start the runner (`npm start`).
3. Open the UI, add skills/sources, click **Run automation**.
4. Watch the live terminal until success or failure.
