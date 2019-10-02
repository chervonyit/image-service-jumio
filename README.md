# image-service-jumio

#### Versions:
- java-1.8.0_25
- apache-maven-3.6.0
- OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
- IDE - Intellj IDEA 2019

#### [ImageService](https://github.com/chervonyit/image-service-jumio/tree/master/image-service "Heading Link")
ImageService is used to return base64 or simple image name from json files stored by ProxyService. It has two api methods: 
`/api/image`
`/api/image-base64`

#### [ProxyService](https://github.com/chervonyit/image-service-jumio/tree/master/proxy-service "Heading Link")
ProxyService is used to filter request that contains "image" and convert it to base64, then save it to json file as cache storage. 

#### [Common](https://github.com/chervonyit/image-service-jumio/tree/master/common "Heading Link")
Common module contains useful utils, models

#### [Rest-api-test](https://github.com/chervonyit/image-service-jumio/tree/master/rest-api-test "Heading Link")
Rest-api-test test could be used as separate project with rest api tests for staging environment.

#### To run tests use this cmd:
`$ mvn clean test`
