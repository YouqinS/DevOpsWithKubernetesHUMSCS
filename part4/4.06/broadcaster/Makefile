IMAGE_TAG=exercise_4.06
IMAGE_NAME_WITH_TAG=broadcaster:$(IMAGE_TAG)

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
	./mvnw spring-boot:run

start-nat:
	docker run -d --name nats-main -p 4222:4222 -p 6222:6222 -p 8222:8222 nats

publish-msg:
	docker pull byrnedo/nats-cli
	docker run byrnedo/nats-cli pub -s nats://localhost:4222 test  hi