const express = require("express");
const app = express();
app.get("/", (_, res) => res.send("Hello from bai04"));
app.listen(3000, () => console.log("bai04 app on 3000"));
