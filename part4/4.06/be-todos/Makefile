IMAGE_TAG=exercise_4.06
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
            --data '{"content":"todo something","status":"todo"}' \
            http://localhost:9999/todo

create-long-todo:
	curl -v --header "Content-Type: application/json" \
            --request POST \
            --data '{"content":"Set the limit of 140 characters for todos into the backend as well. Use Postman or curl to test that too long todos are blocked by the backend and you can see the non-allowed messages in your grafana","status":"active"}' \
            http://localhost:9999/todo

update-todo:
	curl -v --header "Content-Type: application/json" \
            --request PUT \
            http://localhost:9999/todo/3

run:
	./mvnw clean org.springframework.boot:spring-boot-maven-plugin:run

start-postgres:

manifests:


