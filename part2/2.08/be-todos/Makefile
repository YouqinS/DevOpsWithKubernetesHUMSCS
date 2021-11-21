IMAGE_TAG=exercise_2.08
IMAGE_NAME_WITH_TAG=todo-app-be:$(IMAGE_TAG)

all: package  image  push-image

package:
	./mvnw clean package

image:
	sudo docker build . -t $(IMAGE_NAME_WITH_TAG)


push-image:
	sudo docker login
	sudo docker tag $(IMAGE_NAME_WITH_TAG) lnsth/$(IMAGE_NAME_WITH_TAG)
	sudo docker push lnsth/$(IMAGE_NAME_WITH_TAG)

create-todo:
	curl -v --header "Content-Type: application/json" \
            --request POST \
            --data '{"content":"John","status":"active"}' \
            http://localhost:8081/todo
run:
	./mvnw clean org.springframework.boot:spring-boot-maven-plugin:run

start-postgres:

manifests:
	sudo kubectl apply -f /home/zbook/devopsKube/exercises/DevOpsWithKubernetesHUMSCS/part2/2.08/manifests/ingress.yaml
	sudo kubectl apply -f /home/zbook/devopsKube/exercises/DevOpsWithKubernetesHUMSCS/part2/2.08/manifests/postgres-service.yaml
	sudo kubectl apply -f /home/zbook/devopsKube/exercises/DevOpsWithKubernetesHUMSCS/part2/2.08/manifests/postgres-secret.yaml
	sudo kubectl apply -f /home/zbook/devopsKube/exercises/DevOpsWithKubernetesHUMSCS/part2/2.08/manifests/postgres-deployment.yaml
	sudo kubectl apply -f /home/zbook/devopsKube/exercises/DevOpsWithKubernetesHUMSCS/part2/2.08/manifests/service.yaml
	sudo kubectl apply -f /home/zbook/devopsKube/exercises/DevOpsWithKubernetesHUMSCS/part2/2.08/manifests/deployment.yaml

