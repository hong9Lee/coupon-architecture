#!/bin/bash

# Wait for Schema Registry to be ready
while ! nc -z schema-registry 18081; do
  echo "Waiting for Schema Registry..."
  sleep 2
done

echo "Schema Registry is up - executing command"

# Register schema
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data @/schema/async-request-event-message.json \
    http://schema-registry:18081/subjects/coupon-download-asyncRequest/versions
