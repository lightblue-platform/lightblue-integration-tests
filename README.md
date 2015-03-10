Lightblue integration tests example using Docker. For more information, see:
* [maven-docker-plugin](https://github.com/rhuss/docker-maven-plugin)
* [maven-failsafe-plugin](http://maven.apache.org/surefire/maven-failsafe-plugin/)

# How to run?
## Configure Docker
You need to have Docker installed, Docker daemon running and Docker remote api exposed. This is how on Fedora 21:
1. Install Docker
  1. `sudo yum install docker -y`
  2. `sudo systemctl enable docker`
  3. `sudo systemctl start docker`
2. Allow ordinary user to use Docker client (assuming $USER is your login)
  1. `sudo usermod -a -G docker $USER`
  2. `sudo exec su -l $USER` (to refresh group membership without re-login)
3. Enable rest endpoint
  1. `sudo vi /etc/sysconfig/docker`
  2. Modify options like this: `OPTIONS='--selinux-enabled -H tcp://localhost:4243 -H unix:///var/run/docker.sock'`
  3. `sudo systemctl restart docker`

## Run
Once Docker is installed and configured, all you need to do is:
`mvn verify`