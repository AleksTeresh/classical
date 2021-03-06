SHELL := /bin/bash

LOCAL_DB_NAME = classical-db

package:
	mvn package

start:
	java -jar ./target/classical-1.0-SNAPSHOT.jar server

start-local-db:
	-sudo kill `sudo lsof -t -i:5432`
	-docker run --name $(LOCAL_DB_NAME) -e POSTGRES_DB=classical -P -p 5432:5432 -d library/postgres:9.5

stop-local-db:
	-docker stop $(LOCAL_DB_NAME)
	-docker rm $(LOCAL_DB_NAME)

jooq-classes:
	mvn generate-sources -D jooq

