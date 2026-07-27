const http = require("http");
const fs = require("fs");
const path = require("path");
const { spawn } = require("child_process");

const PORT = 5050;
const PROJECT_ROOT = path.resolve(__dirname, "..");
const SKILLS_JSON = path.join(
  PROJECT_ROOT,
  "src",
  "test",
  "java",
  "testdata",
  "skills.JSON"
);
const PUBLIC_DIR = __dirname;

let running = false;

function sendJson(res, status, body) {
  const payload = JSON.stringify(body);
  res.writeHead(status, {
    "Content-Type": "application/json; charset=utf-8",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET, POST, OPTIONS",
    "Access-Control-Allow-Headers": "Content-Type",
  });
  res.end(payload);
}

function contentType(filePath) {
  const ext = path.extname(filePath).toLowerCase();
  if (ext === ".html") return "text/html; charset=utf-8";
  if (ext === ".js") return "text/javascript; charset=utf-8";
  if (ext === ".css") return "text/css; charset=utf-8";
  if (ext === ".json") return "application/json; charset=utf-8";
  if (ext === ".svg") return "image/svg+xml";
  return "application/octet-stream";
}

function serveStatic(req, res) {
  let urlPath = req.url === "/" ? "/skills-dashboard.html" : req.url.split("?")[0];
  urlPath = decodeURIComponent(urlPath);
  const filePath = path.normalize(path.join(PUBLIC_DIR, urlPath));

  if (!filePath.startsWith(PUBLIC_DIR)) {
    res.writeHead(403);
    res.end("Forbidden");
    return;
  }

  fs.readFile(filePath, (err, data) => {
    if (err) {
      res.writeHead(404, { "Content-Type": "text/plain; charset=utf-8" });
      res.end("Not found");
      return;
    }
    res.writeHead(200, { "Content-Type": contentType(filePath) });
    res.end(data);
  });
}

function readBody(req) {
  return new Promise((resolve, reject) => {
    const chunks = [];
    req.on("data", (chunk) => chunks.push(chunk));
    req.on("end", () => {
      try {
        const raw = Buffer.concat(chunks).toString("utf8");
        resolve(raw ? JSON.parse(raw) : {});
      } catch (e) {
        reject(e);
      }
    });
    req.on("error", reject);
  });
}

function writeSkillsFile(skills) {
  const payload = { skills };
  fs.mkdirSync(path.dirname(SKILLS_JSON), { recursive: true });
  fs.writeFileSync(SKILLS_JSON, JSON.stringify(payload, null, 2), "utf8");
}

function runMaven(res) {
  const isWin = process.platform === "win32";
  const mvnCmd = isWin ? "mvn.cmd" : "mvn";

  const child = spawn(mvnCmd, ["test"], {
    cwd: PROJECT_ROOT,
    shell: true,
    env: process.env,
  });

  const writeLine = (text) => {
    if (!res.writableEnded) {
      res.write(text);
    }
  };

  child.stdout.on("data", (data) => writeLine(data.toString()));
  child.stderr.on("data", (data) => writeLine(data.toString()));

  child.on("error", (err) => {
    writeLine(`\n[runner] Failed to start Maven: ${err.message}\n`);
    if (!res.writableEnded) {
      res.end();
    }
    running = false;
  });

  child.on("close", (code) => {
    writeLine(
      code === 0
        ? "\n[runner] BUILD SUCCESS\n"
        : `\n[runner] BUILD FAILURE (exit code ${code})\n`
    );
    if (!res.writableEnded) {
      res.end();
    }
    running = false;
  });
}

const server = http.createServer(async (req, res) => {
  if (req.method === "OPTIONS") {
    res.writeHead(204, {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, POST, OPTIONS",
      "Access-Control-Allow-Headers": "Content-Type",
    });
    res.end();
    return;
  }

  if (req.method === "GET" && req.url === "/health") {
    sendJson(res, 200, { ok: true, running, projectRoot: PROJECT_ROOT });
    return;
  }

  if (req.method === "POST" && req.url === "/run") {
    if (running) {
      sendJson(res, 409, { error: "A run is already in progress" });
      return;
    }

    let body;
    try {
      body = await readBody(req);
    } catch {
      sendJson(res, 400, { error: "Invalid JSON body" });
      return;
    }

    if (!Array.isArray(body.skills) || body.skills.length === 0) {
      sendJson(res, 400, { error: "skills must be a non-empty array" });
      return;
    }

    const skills = body.skills.map((s) => ({
      name: String(s.name || "").trim(),
      sources: Array.isArray(s.sources) ? s.sources.map(String) : [],
    }));

    if (skills.some((s) => !s.name)) {
      sendJson(res, 400, { error: "Each skill needs a name" });
      return;
    }

    try {
      writeSkillsFile(skills);
    } catch (err) {
      sendJson(res, 500, { error: `Failed to write skills.JSON: ${err.message}` });
      return;
    }

    running = true;
    res.writeHead(200, {
      "Content-Type": "text/plain; charset=utf-8",
      "Access-Control-Allow-Origin": "*",
      "Cache-Control": "no-cache",
      "X-Accel-Buffering": "no",
    });

    res.write(`[runner] Wrote ${skills.length} skill(s) to:\n${SKILLS_JSON}\n`);
    res.write("[runner] Starting: mvn test\n");
    res.write("[runner] Make sure Chrome is open with --remote-debugging-port=9222\n\n");

    runMaven(res);
    return;
  }

  if (req.method === "GET") {
    serveStatic(req, res);
    return;
  }

  sendJson(res, 405, { error: "Method not allowed" });
});

server.listen(PORT, () => {
  console.log(`SkillForge runner listening on http://localhost:${PORT}`);
  console.log(`Open UI: http://localhost:${PORT}/`);
  console.log(`Project root: ${PROJECT_ROOT}`);
  console.log(`skills.JSON: ${SKILLS_JSON}`);
});
