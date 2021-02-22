# questionnaire-rest

## Инструкция по запуску

- Сделать clone этого репозитория

- Поправить файл src/main/resources/application.properties, чтобы он мог подключиться к вашей базе данных

- Из коммандной строки в папке с pom.xml выполнить команду: maven clean install.(Или выполнить ее средствами intelliJ
  Idea)

- Перейти в папку target

- Выполнить команду: java -jar questionnaire-rest-0.0.1-SNAPSHOT.jar

- Документация доступна по адресу http://{host}/swagger-ui/index.html

- В данном случае уровня бизнес логики нет, так что можно было не использовать уровень сервисов, сейчас он сливается с Data acces layer и делает его двухуровневым
