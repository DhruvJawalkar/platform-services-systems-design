$env:MAVEN_OPTS='-Dmaven.repo.local=C:/Users/dhruv/OneDrive/Desktop/Labs/platform-services-systems-design/.m2/repository'
Push-Location services/orders-service
mvn clean package
Pop-Location
