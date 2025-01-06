1. Требуется запустить docker-compose
2. Запускаем eureka-server
3. Запускаем микросервисы main-service, library-service
4. Запускаем api-gateway
5. Переходим по url http://localhost:8081/main-service/swagger-ui/index.html (Если ошибка, то нужно перезапустить api-gateway)
6. Регистрируемся и получаем токен
7. В правом верхнем углу Swagger есть кнопка Authorize куда и вставляем токен
8. После этого все запросы будут работать корректно

...в запросе о получении списка свободных книг в parameters ничего вставлять не требуется
