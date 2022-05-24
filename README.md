# Working Student APP (<i>Backend</i>)


## Requirements
* [JDK 11](https://www.oracle.com/es/java/technologies/javase/jdk11-archive-downloads.html)
* [Gradle version >= 5.0](https://docs.gradle.org/current/userguide/compatibility.html)
* [Intellj IDEA](https://www.jetbrains.com/idea/) or similar
* [Docker/Docker Desktop](https://www.docker.com/)
* [PostgreSQL](https://www.postgresql.org/)

## Installation

## Docker
### Docker build 
```
docker build -t workingstudentapp .
```

### Docker push to  hub 
```
docker tag workingstudentapp iquinto/workingstudentapp
```

```
docker push iquinto/workingstudentapp
```

### Docker run (for testing)
```
docker run -it -p 8081:81 --rm --name workingstudent workingstudentapp
```


### Docker stop
```
docker stop $(docker ps -a -q)
docker rm -vf $(docker ps -a -q)
docker rmi -f $(docker images -a -q) 

```

 
