IMAGE_TAG=exercise_5.01
IMAGE_NAME_WITH_TAG=dummysite:$(IMAGE_TAG)

all:  image  push-image


image:
	sudo docker build . -t $(IMAGE_NAME_WITH_TAG)


push-image:
	sudo docker login
	sudo docker tag $(IMAGE_NAME_WITH_TAG) lnsth/$(IMAGE_NAME_WITH_TAG)
	sudo docker push lnsth/$(IMAGE_NAME_WITH_TAG)

run:
	npm start