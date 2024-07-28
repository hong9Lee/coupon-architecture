#!/bin/bash

# Wait for Kafkay to be ready
while ! nc -z kafka 29092; do
  echo 'Waiting for Kafka to be ready...'
  sleep 2
done

echo "Kakfa is up - executing command"

# Register topic
kafka-topics --create --if-not-exists --topic coupon-download-asyncRequest --bootstrap-server kafka:29092 --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka:29092 --list
echo 'Kafka topic created.'
