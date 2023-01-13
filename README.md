# Gif Service Application
## Описание
Сервис, который позволяет редактировать параметры персонажей (игроков) и назначать баны. Информация о игроках
хранится в базе данных.

***
## Системные требования
Установленные:
- Java 11
- Gradle 7.4.1
***
## Установка
- Клонируйте репозиторий
  ```
  git clone https://github.com/alela-mgn/gif-service.git
  ```
- выполните команду
  ```
  gradle build
  ```

***
## Запуск программы
-  Выполнить команду
  ```
  java -jar build/libs/gif-service-0.0.1-SNAPSHOT.jar
  ```

***
Gif Get Endpoint
---
Endpoint :
```
GET api/v1/currency/compare/{currency}
```
Получить гифку можно по следующей ссылке:
http://localhost:8080/api/v1/currency/compare/КОД_ВАЛЮТЫ (RUB или любой другой код)
***