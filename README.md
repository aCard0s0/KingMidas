# KingMidas


```
docker build -t [nameTag] [src_path]
docker run [nameTag]
docker stop [nameTag]
docker rm [nameTag]
docker ps -a
```

###Redis
```
docker build -t redis/pubsub .
docker run -p 6379:6379 redis/pubsub
```

Redis docs: https://github.com/redis/jedis/wiki/Getting-started <br>
Good article with examples codes: https://www.vogella.com/tutorials/RxJava/article.html <br>
Lib alternatives: https://redis.io/clients#java

###MongoDB
```
docker build -t mongo/datastore .
docker run -it -v mongodb:/data/db -p 27017:27017 mongo/datastore
sudo docker exec -it [container id] bash
```
```
mongo -host localhost -port 27017
show databases
use food
db.fruits.find().pretty()
```
run mongodb as container: https://www.bmc.com/blogs/mongodb-docker-container/ <br>
mongodb quick guide: https://gist.github.com/bradtraversy/f407d642bdc3b31681bc7e56d95485b6

Observables: http://reactivex.io/tutorials.html