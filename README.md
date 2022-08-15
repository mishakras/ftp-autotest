Тестовое задание по направлению Java-автотестирование для компании ИнфоТеКС.
В папке ftpclient - первая часть задания: разработанный клиент для работы с ftp сервером с использованием библиотеки https://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClient.html, связь идет по порту 20
В папке ftptest - вторая часть задания: разработанные тесты с помощью фреймворка TestNG сформированные в suite.

Инструкция по установке и запуску приложения.
  1. Установить apache maven; 
  2. Запустить командную строку в папке ftpclient;
  3. Запустить команду maven: mvn clean compile install;
  4. По завершению сборки в тойже папке запустить команду java: java -jar target/ftpclient-1.0-jar-with-dependencies.jar ip-адрес ftp сервера логин пользователя пароль.

Инструкция работе с приложением.
  1. Выбрать пассивный(1, рекомендуется) или активный(2) режимы работы с ftp сервером, введя соответствующую цифру в командную строку.
  2. Выбирать нужную команду из предоставленного списка, введя соответствующую цифру в командную строку. Полный список команд представлен ниже:
      1 - Вывести информацию о всех студентах;
      2 - Вывести информацию о студенте с введенным id;
      3 - Добавить студента с введенным именем, информация обновится на ftp сервере;
      4 - Удалить студента с введенным id, информация обновится на ftp сервере;
      5 - Вывести инструкцию;
      6 - Выйти из приложения.
      
Инструкция по запуску тестов.
  1. Установить apache maven; 
  2. Запустить командную строку в папке ftptest;
  3. Запустить команду maven: mvn clean test -DsuiteXmlFile=testng.xml.

Описание и обоснование тестов.
Первые семь тестов проверяют работу класса Jsonworker, в котором хранится информация о студентах скачанная с ftp сервера.
  1 - Тестирование получения списка студентов. Проверяется, что Jsonworker возвращает всех студентов в правильном порядке;
  2 - Тестирование добавления студента. Проверка успешного добавления студента с уникальным именем;
  3 - Тестирование добавления студента. Проверка, что при добавлении студента с существующим именем функция вернет ошибку;
  4 - Тестирование выдачи студента по id. Проверка успешного нахождения студента по id;
  5 - Тестирование выдачи студента по id. Проверка, что при отсутствии id студента функция вернет ошибку;
  6 - Тестирование удаления студента по id. Проверка успешного удаления студента по id;
  7 - Тестирование удаления студента по id. Проверка, что при отсутствии id студента функция вернет ошибку;
Оставшиеся 3 теста проверяют связь приложения с ftp серером и его обновление. Используется библиотека https://mockftpserver.org/
  8 - Тестирование подключения к ftp серверу. Проверка работы команд connect и login
  9 - Тестирование получения данных с ftp сервера. Проверка корректности получаемых с сервера данных.
  10 - Тестирование отправки данных на ftp сервер. Проверка работы команды sendfile.
 
Примечание 1 
  Последний тест по записи файла на fakeftpserver не проходит и я не понимаю почему.

Примечание 2
  Нет возможности разработать java проект вне фреймворка maven.
