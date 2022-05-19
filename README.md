# BACKEND
working-student-ui

## Docker build 
```
docker build -t workingstudentapp .
```

## Docker push to  hub 
```
docker tag workingstudentapp iquinto/workingstudentapp
```

```
docker push iquinto/workingstudentapp
```

## Docker build 
```
docker run -it -p 8081:81 --rm --name workingstudent workingstudentui
```


## Docker stop
```
docker stop $(docker ps -a -q)
docker rm -vf $(docker ps -a -q)
docker rmi -f $(docker images -a -q) 

```

 