# Kafka Use Case

1. Start the Kafka and Zookeeper processes. If Kafka and Zookeeper are already running
   in your local machine, you can skip this step.

   ```bash
   $ docker compose up -d
   ```

2. Start the flask application:

   ```bash
   $ export FLASK_ENV=development
   $ export KAFKA_HOST="localhost:19092" # Change the value if necessary
   $ flask run
   ```
  
3. Open another terminal and start the consumer process:

   ```bash
   $ python consumer.py "localhost:19092" # Change the value if necessary
   ```
  
4. Now, go to the [Web Application](http://localhost:5000/) and see the results.
