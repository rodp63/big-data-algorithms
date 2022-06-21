import os

from kafka import KafkaProducer
from flask import Flask, request


KAFKA_HOST = os.environ.get("KAFKA_HOST", "localhost:9092")

app = Flask(__name__)
producer = KafkaProducer(bootstrap_servers=KAFKA_HOST)


@app.route("/", methods=["GET"])
def main():
    producer.send("visits", str.encode(request.remote_addr))
    return "Welcome to this website", 200
