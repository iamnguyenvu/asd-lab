require("dotenv").config();

const { app } = require("./app");
const { initializeDatabase } = require("./config/database");

const PORT = process.env.PORT || 4000;

async function bootstrap() {
  await initializeDatabase();

  app.listen(PORT, () => {
    console.log(`CMS server is running on http://localhost:${PORT}`);
  });
}

bootstrap().catch((error) => {
  console.error("Failed to start server:", error);
  process.exit(1);
});
