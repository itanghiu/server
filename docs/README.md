#README HSKING SERVER

## Jenkins login/password
i-tang/i-tang

## Launch the containerized app:

> cd /Users/i-tang/DEV/HSKING_SERVER
> docker-compose up -d
connect to the app:
  http://localhost:8080/hsking/index.html

##Stop the containers:
> docker-compose stop
> docker-compose rm


##START H2 DATABASE:

method1:
> java -cp /Users/i-tang/.m2/repository/com/h2database/h2/1.3.175/h2-1.3.175.jar org.h2.tools.Server

method2 (via intelliJ):
start the H2 database (in the execute comboBox choose H2Database)

##START THE APP :

1- start the H2 database (in the execute comboBox choose H2Database)
2- launch the app and create the tables :
  > mvn jetty:run
3- connect to the app:
  http://localhost:8080/hsking/index.html

##START JETTY AS A STANDALONE

> mvn jetty:run

##OPENING the H2 database as a separate instance :

Create an application and in "Main class: "field, enter: org.h2.tools.Console


## DROPPING ALL TABLES FROM H2 DATABASE:
from the H2 console in a browser, type in :
>drop all objects

##OPENING the H2 database as a separate instance :

Create an application and in "Main class: "field, enter: org.h2.tools.Console

