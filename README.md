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
docker run -it -v mongodb:/data/db -p 27017:27017 mongo/datastore`<br>
sudo docker exec -it [container id] bash`<br>
```
```
mongo -host localhost -port 27017
show databases
use food
db.fruits.find().pretty()
```
Ref: https://www.bmc.com/blogs/mongodb-docker-container/