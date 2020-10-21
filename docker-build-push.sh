IMAGE_REPOSITORY=pbltest
IMAGE_NAME=spring-server
GCP_PROJECT=pbl-apol
TAG=latest


# Build Spring Boot app
./mvnw clean install

# Build Docker file
docker build -f Dockerfile --no-cache -t ${IMAGE_REPOSITORY}/${IMAGE_NAME}:${TAG} .

# Push image to Docker Hub
docker push ${IMAGE_REPOSITORY}/${IMAGE_NAME}:${TAG}

# Push image to GCP Container Registry (GCR)
docker tag ${IMAGE_REPOSITORY}/${IMAGE_NAME}:${TAG} gcr.io/${GCP_PROJECT}/${IMAGE_NAME}:${TAG}
docker push gcr.io/${GCP_PROJECT}/${IMAGE_NAME}:${TAG}

# Re-deploy Workload (containerized app) to GKE
kubectl replace --force -f gke/${IMAGE_NAME}.yaml

read