#!/usr/bin/env sh

rm -r ./db

docker rm -f antigravity-mysql antigravity-mysql-phpmyadmin antigravity-redis antigravity-redis-commander

#echo "volume remove not dangling!"
#
## shellcheck disable=SC2046
#docker volume rm $(docker volume ls  -q --filter dangling=true)

docker-compose up

