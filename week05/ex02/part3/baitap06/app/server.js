const express = require("express");
const app = express();
app.get("/", (_, res) => res.send("CI/CD compose demo"));
app.listen(3000);
