os=$(go env GOOS)
arch=$(go env GOARCH)
echo ${os}/${arch}

#curl -L -o kubebuilder https://go.kubebuilder.io/dl/latest/$(go env GOOS)/$(go env GOARCH)
#chmod +x kubebuilder
#sudo mv kubebuilder /usr/local/bin/


## download kubebuilder and extract it to tmp
##curl -L https://go.kubebuilder.io/dl/2.3.1/${os}/${arch} | tar -xz -C /tmp/

## move to a long-term location and put it on your path
## (you'll need to set the KUBEBUILDER_ASSETS env var if you put it somewhere else)
##sudo mv /tmp/kubebuilder_2.3.1_${os}_${arch} /usr/local/kubebuilder
#export PATH=$PATH:/usr/local/kubebuilder/bin


# this is needed to when you want to use make to push docker image:
echo https://storage.googleapis.com/kubebuilder-tools/kubebuilder-tools-1.16.4-${os}-${arch}.tar.gz
curl -fsL https://storage.googleapis.com/kubebuilder-tools/kubebuilder-tools-1.16.4-${os}-${arch}.tar.gz -o kubebuilder-tools && tar -zvxf kubebuilder-tools
sudo mv kubebuilder/ /usr/local/kubebuilder