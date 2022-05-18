# working-student-app

# Docker
### Run project
```
docker compose up --build
```

### Stop project
```
docker-compose down
```
### Clean Docker


#### 1. Stop All Docker Containers
```
docker kill $(docker ps -q)
```

#### 2. Delete all containers using the following command:
```
docker rm -f $(docker ps -a -q)
```

#### 3. Delete all containers including its volumes use,
```
docker rm -vf $(docker ps -aq)
```

#### 4. Delete all the images,
```
docker rmi -f $(docker images -aq)
```
#### 5. Delete all volumes using the following command:
```
docker volume rm $(docker volume ls -q)
```
