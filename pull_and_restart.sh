#!/bin/bash
# List of images to pull
images=(
    bernardaryee/auth:latest
    bernardaryee/notification-service:latest
    bernardaryee/inventory-service:latest
    bernardaryee/order-service:latest
    bernardaryee/ussd:latest
    bernardaryee/payment-service:latest
    bernardaryee/product-service:latest
    bernardaryee/api-gateway:latest
    bernardaryee/discovery-server:latest
    # Add more images as needed
)

# Loop through the images and pull them
for image in "${images[@]}"; do
    echo "Pulling $image..."
    docker pull "$image"
done

echo "Restarting containers..."
docker ps -q | xargs docker restart

