from flask import Flask
app = Flask(__name__)

@app.get("/")
def home():
    return {"message": "Hello from bai09 Flask"}

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
