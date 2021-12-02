IMAGE_TAG=exercise_4.01
IMAGE_NAME_WITH_TAG=pingpong:$(IMAGE_TAG)

all: package  image  push-image

package:
	./mvnw clean package

image:
	sudo docker build . -t $(IMAGE_NAME_WITH_TAG)


push-image:
	sudo docker login
	sudo docker tag $(IMAGE_NAME_WITH_TAG) lnsth/$(IMAGE_NAME_WITH_TAG)
	sudo docker push lnsth/$(IMAGE_NAME_WITH_TAG)


run:
	./mvnw org.springframework.boot:spring-boot-maven-plugin:run

start-postgres:
	sudo docker run -d -it -p 5432:5432 -e POSTGRES_DB=pingpong -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:10.1

