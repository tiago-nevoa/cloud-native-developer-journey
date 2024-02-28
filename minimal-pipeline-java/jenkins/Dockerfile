FROM jenkins/jenkins:2.418-jdk17

# disable wizard
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# install plugins
COPY --chown=jenkins:jenkins ./plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

# switch to root user
USER root

# install tools
RUN apt-get update && apt-get upgrade -y && apt-get install -y maven docker.io

# install kubectl
RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
RUN chmod +x ./kubectl
RUN mv ./kubectl /usr/local/bin/kubectl

# install cf-cli
RUN curl --location https://cli.run.pivotal.io/stable?release=linux64-binary | tar xz cf
RUN mv ./cf /usr/local/bin/cf

# switch back to jenkins user
USER jenkins

# expose port
EXPOSE 8080
