const path = require("path");
const express = require("express");
const cors = require("cors");
const helmet = require("helmet");
const morgan = require("morgan");
const rateLimit = require("express-rate-limit");

const { PluginManager } = require("./kernel/pluginManager");
const { ContentService } = require("./services/contentService");
const { createContentController } = require("./controllers/contentController");
const { createContentRoutes } = require("./routes/contentRoutes");
const { notFoundHandler, errorHandler } = require("./middleware/errorHandler");

const seoPlugin = require("./plugins/seoPlugin");
const auditPlugin = require("./plugins/auditPlugin");

const app = express();

const pluginManager = new PluginManager();
pluginManager.register(seoPlugin);
pluginManager.register(auditPlugin);

pluginManager.syncStateFromDatabase().catch((error) => {
  console.error("Failed to load plugin state:", error);
});

const contentService = new ContentService(pluginManager);
const controller = createContentController(contentService, pluginManager);

const apiRateLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 300,
  standardHeaders: true,
  legacyHeaders: false,
});

app.use(helmet({
  contentSecurityPolicy: false,
}));
app.use(cors());
app.use(express.json({ limit: "1mb" }));
app.use(morgan("dev"));
app.use("/api", apiRateLimiter);

app.get("/api/health", (_req, res) => {
  res.json({
    success: true,
    service: "plugin-cms",
    timestamp: new Date().toISOString(),
  });
});

app.use("/api", createContentRoutes(controller));

app.use(express.static(path.join(__dirname, "..", "public")));

app.get("*", (req, res, next) => {
  if (req.path.startsWith("/api")) {
    return next();
  }

  return res.sendFile(path.join(__dirname, "..", "public", "index.html"));
});

app.use(notFoundHandler);
app.use(errorHandler);

module.exports = {
  app,
};
