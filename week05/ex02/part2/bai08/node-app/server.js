const express = require("express");
const mysql = require("mysql2/promise");
const app = express();

app.get("/", async (_, res) => {
  try {
    const conn = await mysql.createConnection({
      host: process.env.DB_HOST,
      user: process.env.DB_USER,
      password: process.env.DB_PASSWORD,
      database: process.env.DB_NAME
    });
    const [rows] = await conn.query("SELECT 1 AS ok");
    await conn.end();
    res.json({ message: "Node connected MySQL", rows });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

app.listen(3000, () => console.log("bai08 on 3000"));
