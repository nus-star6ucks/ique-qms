### Use CloudSQL

1. Setting up Dependencies

   ```java
   dependencies {
       implementation platform("com.google.cloud:spring-cloud-gcp-dependencies:3.3.0")
           implementation("com.google.cloud:spring-cloud-gcp-starter-sql-mysql")
   }
   ```

2. From Google Cloud Console

    1. Create a service account
    2. Download the key file

3. Add `_key.json` file to the code repository

4. Create a new file: `application-cloud.properties` in `/src/main/resources `

   add config:

   ```properties
   spring.cloud.gcp.sql.enabled=true
   # need to change later
   spring.cloud.gcp.sql.database-name=connection_test
   spring.cloud.gcp.sql.instance-connection-name=ique-star6ucks:asia-southeast1:queue-db
   spring.datasource.username=queue-manager
   spring.datasource.password=rTJBMdkj6LrCSf0+
   spring.cloud.gcp.project-id=ique-star6ucks
   spring.cloud.gcp.credentials.location=file:_key.json
   ```

5. Add config to  `application.properties` file

   ```properties
   spring.profiles.active=cloud
   ```
   
6. Run the application by `./gradlew bootRun`

![screenshot](https://tva1.sinaimg.cn/large/008vxvgGgy1h70q9gbf1dj31ne0po101.jpg)



Resources:

- [Spring Cloud GCP 3.3.0](https://googlecloudplatform.github.io/spring-cloud-gcp/3.3.0/reference/html/index.html)
- [Connect a Spring Boot app to Cloud SQL](https://codelabs.developers.google.com/codelabs/cloud-spring-petclinic-cloudsql?hl=en&continue=https%3A%2F%2Fcodelabs.developers.google.com%2Fspring)

- [Authorize with a service account](https://cloud.google.com/sdk/docs/authorizing#authorize_with_a_service_account)