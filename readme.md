# Epic Technical Exercise

Technical exercise for testing by using Docker-compose and Cucumber


## Set up

All the testing is done inside docker-compose environment and the report is availalbe on standard output.


#### Prerequisites

1. Linux, preferably debian based.
2. Docker
3. Docker Compose


#### Install dependencies

```bash
docker-compose up -d
```

#### Test Execution

Run tests by either all at once, or by suite name.

Results will be on standard output and in database. You can access the database (here](http://172.21.0.6:8080/?server=mysql&username=epic&db=test&select=users) with password `secret`.

For Web testing you can use a VNC client application to connect to the running desktop `vnc://172.21.0.11:5900` with password `secret`.

The test cases are written in Gherkin and are located in `/tests-java/cucumber/src/test/resources/cucumber/`

User this docker command to run the tests. Make sure to use the same user id and group id as of the system user. Use the command `id` to get the IDs.

```bash
docker run -it --rm -v $(pwd)/tests-java/cucumber:/srv/cucumber -w /srv/cucumber -u 1000:1000 --network epic-testing-exercise_qa_net maven:3.8.4-openjdk-8 bash
```

Replace last command (bash) with the following to run:

1. all test suites
	```bash
	mvn test
	```
2. only API tests
	```bash
	mvn test -Dcucumber.filter.tags="@api-todo"
	```
3. only Epic Web Portal tests
	```bash
	mvn test -Dcucumber.filter.tags="@epic-portal"
	```
4. only Database tests
	```bash
	mvn test -Dcucumber.filter.tags="@users"
	```

----

This environment is very basic and can be built upon. For example extended reporting with online avaiability, mobile testing, more applications, and so on.


