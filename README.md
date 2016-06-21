# openweathermap

Please follow the below steps to run this web app:

1. Add the following jars files in your tomcat server lib folder available in openweathermap/lib_files/

- json-20090211.jar
- owm-japis-2.5.0.3.jar
- junit-3.8.1.jar

2. Deploy openweathermap/openweathermap/target/openweathermap.war on tomcat or any web container

3. Access it using the following urls

http://localhost:8080/openweathermap/
http://localhost:8080/openweathermap/OpenWeatherController?location=Hong+Kong
http://localhost:8080/openweathermap/OpenWeatherController?location=London

TO DO

- Write Junit tests
- Use Java 8 Date Time API to address Timezone issues
- Write Java Docs, code comments
- Code clean up
- Write HTML, CSS code to style and format the webpage appropriately
- At the moment, single servlet is written which is implementing the basic requirements but the solution can be refacored to have more dedicated Java class depending on the use case
- If session management is required then HTTP Session can be used
