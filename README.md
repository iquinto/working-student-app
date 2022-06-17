# Working Student APP (<i>Backend</i>)


## Requirements
* [JDK 11](https://www.oracle.com/es/java/technologies/javase/jdk11-archive-downloads.html)
* [Gradle version >= 5.0](https://docs.gradle.org/current/userguide/compatibility.html)
* [Intellj IDEA](https://www.jetbrains.com/idea/) or similar
* [Docker/Docker Desktop](https://www.docker.com/)
* [PostgreSQL](https://www.postgresql.org/)

## Installation
### Prepare Database (Postgres)
* Download and install <a href="https://www.postgresql.org/download/">PostgresSQL</a>
* Optionally, download  and install <a href="https://www.pgadmin.org/download/">pgAdmin</a>
* Create a database named ```workingstudent```
* Schema and tables are automatically creted <b>on app load</b> by  execution by executing <a href="https://github.com/iquinto/working-student-app/blob/master/src/main/resources/scripts/schema.sql">  this script</a>.
* Create user and role:

```
CREATE ROLE workingstudent WITH
	LOGIN
	SUPERUSER
	CREATEDB
	CREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'workingstudent';
GRANT usage on schema public to workingstudent;
GRANT create on schema public to workingstudent;
```
* To sum-up, the author avails the following data for database management:

```
database: workingstudent
username: workingstudent
password: workingstudent
host: localhost
port: 5432
```

### Prepare Springboot App
* Download or clone the  repository.
* Using IntelliJ IDE (or similar) import the working-student-app as Gradle project.
* The application has 3 profiles:<br>
<b>dev</b>. This is the profile for local execution. <br>
<b>docker</b>. This is the profile for building  docker image.<br>
<b>test</b>.  This is the profile for testing.

### Project Execution
Set active profile to <b>dev</b> in <a href="https://github.com/iquinto/working-student-app/blob/master/src/main/resources/application.properties">application.properties</a>:
```spring.profiles.active=dev```
Then run:
```
$ ./gradlew bootRun
```
or follow this <b><a href="https://www.jetbrains.com/help/idea/work-with-gradle-tasks.html"> instructions</a></b>.

### Test Execution
Run the following command
 
```
$ ./gradlew test 
```
or 
```
$ ./gradlew check 
```

or follow this <b><a href="https://www.jetbrains.com/help/idea/work-with-tests-in-gradle.html"> instructions</a></b>.


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

 ## AWS + Docker 

```
docker build -t workingstudentapp .
docker tag workingstudentapp iquinto/ws-back
docker push iquinto/ws-back
ssh -i "***file.pem" ubuntu@*** // use public ip
sudo docker-compose up
```
 
