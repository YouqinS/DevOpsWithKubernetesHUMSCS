IMAGE_TAG=exercise_2.02
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
