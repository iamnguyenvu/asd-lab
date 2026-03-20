const express = require("express");
const mongoose = require("mongoose");
const app = express();

mongoose.connect(process.env.MONGO_URI).then(() => console.log("Connected Mongo"));

app.get("/", (_, res) => res.json({ message: "Node + Mongo OK" }));
app.listen(3000, () => console.log("Node app on 3000"));
