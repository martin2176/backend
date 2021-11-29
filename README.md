# demobackend

## Steps to build project (includes creation of Jar file): 

```console
mvn clean install
```

or without tests

```console
mvn clean install -DskipTests
```

## Command to run jar file:
##### Requires 4 environmental variables
- APIKEY
- TABLENAME
- USE_HTTPS
- ZIPNAME

```console
java -DAPIKEY=[REPLACE WITH API KEY] -DTABLENAME=[REPLACE WITH TABLE NAME] -DUSE_HTTPS=[REPLACE WITH true or false] -DZIPNAME=[REPLACE WITH Column Name] -jar backend-0.0.1-SNAPSHOT.jar com.martin.backend.BackendApplication
```

Example:

```console
java -DAPIKEY=55a5555555a5a555a5555555a5555555 -DTABLENAME=dummytable -DUSE_HTTPS=false -DZIPNAME=zipcolumn -jar backend-0.0.1-SNAPSHOT.jar com.martin.backend.BackendApplication
```


## Requirements
Java Version: 11
Maven Version: ~3.6.3 (or greater)

