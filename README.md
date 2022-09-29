# Job4j_pooh
[![Java CI with Maven](https://github.com/svoh86/job4j_threads/actions/workflows/maven.yml/badge.svg)](https://github.com/svoh86/job4j_threads/actions/workflows/maven.yml)

+ [О проекте](#О-проекте)
+ [Сборка и запуск](#Сборка-и-запуск)
+ [Использование](#Использование)
+ [Контакты](#Контакты)

## О проекте

В данном проекте написан аналог асинхронной очереди. Приложение запускает Socket и ждет клиентов. 
В качестве клиента использован cURL. В качестве протокола - HTTP. 
Сервер представляет собой систему обмена сообщениями 
на базе потокобезопасных классов из пакета java.util.concurrent.

## Сборка и запуск

### Запуск через терминал

1.Собрать jar через Maven

`mvn install -Dmaven.test-skip=true`

2.Запустить jar файл

`java -jar target/pooh.jar`

### Запуск через IDE

Перейти к папке `src/main/java` и файлу `ru.job4j.pooh.PoohServer`

## Использование

У приложения есть два режима: *Queue* и *Topic*.

- Queue

Отправитель посылает запрос на добавление данных с указанием очереди (weather) и значением параметра (temperature=18). 
Сообщение помещается в конец очереди. Если очереди нет в сервисе, то нужно создать новую и поместить в нее сообщение.
Получатель посылает запрос на получение данных с указанием очереди. Сообщение забирается из начала очереди и удаляется.
Если в очередь приходят несколько получателей, то они поочередно получают сообщения из очереди.
Каждое сообщение в очереди может быть получено только одним получателем.

***Пример запросов.***
POST запрос добавляет элементы в очередь weather.
`curl -X POST -d "temperature=18" http://localhost:9000/queue/weather`
`queue` указывает на режим очередь.
`weather` указывает на имя очереди.

GET запрос должен получить элементы из очереди weather.
`curl -X GET http://localhost:9000/queue/weather`
Ответ: temperature=18

---

- Topic

Отправитель посылает запрос на добавление данных с указанием топика (weather) и значением параметра (temperature=18). 
Сообщение помещается в конец каждой индивидуальной очереди получателей. 
Если топика нет в сервисе, то данные игнорируются.
Получатель посылает запрос на получение данных с указанием топика. 
Если топик отсутствует, то создается новый. А если топик присутствует, 
то сообщение забирается из начала индивидуальной очереди получателя и удаляется.
Когда получатель впервые получает данные из топика – для него создается индивидуальная пустая очередь. 
Все последующие сообщения от отправителей с данными для этого топика помещаются в эту очередь тоже.
Таким образом в режиме "topic" для каждого потребителя своя будет уникальная очередь с данными, 
в отличие от режима "queue", где для все потребители получают данные из одной и той же очереди.

***Пример запросов.***
`curl -X POST -d "temperature=18" http://localhost:9000/topic/weather`
`topic` указывает на режим topic.
`weather` указывает на имя topic.

`curl -X GET http://localhost:9000/topic/weather/1`
`topic` указывает на режим topic.
`weather` указывает на имя topic.
`1` - ID клиента.
Ответ temperature=18

---

## Контакты

Свистунов Михаил Сергеевич

[![Telegram](https://img.shields.io/badge/Telegram-blue?logo=telegram)](https://t.me/svoh86)

Email: sms-86@mail.ru