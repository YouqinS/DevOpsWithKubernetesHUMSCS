IMAGE_TAG=exercise_2.08
IMAGE_NAME_WITH_TAG=todo-app-fe:$(IMAGE_TAG)

all:  image  push-image


image:
	sudo docker build . -t $(IMAGE_NAME_WITH_TAG)


push-image:
	sudo docker login
	sudo docker tag $(IMAGE_NAME_WITH_TAG) lnsth/$(IMAGE_NAME_WITH_TAG)
	sudo docker push lnsth/$(IMAGE_NAME_WITH_TAG)

run:
	npm start