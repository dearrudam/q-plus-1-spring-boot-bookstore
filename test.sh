#!/bin/sh

function test_get(){
    local URL=$1
    curl -X 'GET' \
      "$1" \
      -H 'accept: */*' 1>>/dev/null
    if [ "$?" != "0" ]; then
      echo "$(date) $1 -> Error"
    else
      echo "$(date) $1 -> OK"
    fi
    sleep 5
}

while true; do
  for i in {0..2}; do
    test_get "http://localhost:8080/api/v1/authors?page=$i&pageSize=15"
    test_get "http://localhost:8080/api/v1/authors/3"
    sleep 5
  done
done
}