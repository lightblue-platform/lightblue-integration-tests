Lightblue integration tests example using Docker. For more information, see:
* [maven-docker-plugin](https://github.com/rhuss/docker-maven-plugin)
* [maven-failsafe-plugin](http://maven.apache.org/surefire/maven-failsafe-plugin/)

# How to run?
## Configure Docker
You need to have Docker installed, Docker daemon running and Docker remote api exposed. This is how on Fedora 21:
### Install Docker
```
sudo yum install docker -y
sudo systemctl enable docker
sudo systemctl start docker
```
Allow ordinary user to use Docker client (assuming $USER is your login)
```
sudo usermod -a -G docker $USER
sudo exec su -l $USER` # (to refresh group membership without re-login)
```
### Enable rest endpoint
`sudo vi /etc/sysconfig/docker`
Modify options like this: `OPTIONS='--selinux-enabled -H tcp://localhost:4243 -H unix:///var/run/docker.sock'`
`sudo systemctl restart docker`

## Run
Once Docker is installed and configured, all you need to do is:
`mvn verify`