# QuotesSearch

How to start the QuotesSearch application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/quotes-1.0-SNAPSHOT.jar server config.yml` or run mvn exec:exec
1. To check that your application is running enter url `http://localhost:8080/swagger`
1. The search url is available at http://localhost:8080/quotes/search - you have to pass in the search text as a post parameter.
1. It takes the config.yml as the configuration . The input file names are also configured in there.

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Also you can see the metrics at http://localhost:8081/metrics

and look for QuoteSearch, under timers. This can be easily configured to be exported t graphite / other matrics visualization tool.