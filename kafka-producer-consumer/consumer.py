import sys
from kafka import KafkaConsumer


try:
    KAFKA_HOST = sys.argv[1]
except:
    KAFKA_HOST = "localhost:9092"

consumer = KafkaConsumer("visits", bootstrap_servers=KAFKA_HOST)


def main():
    for message in consumer:
        message_value = str(message.value)
        print(f"New connection from: {message_value}")

    consumer.close()


if __name__ == "__main__":
    main()
