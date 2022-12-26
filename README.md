 **Для запуска автотестов необходимо выполнить следующие действия:**
 1. Установить ПО среды разработки IntelliJ IDEA;
 1. Установить десктопную версию Docker desktop [Руководство по утановке](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md);
 1. с помощью команды docker pull в терминале загрузите с DockerHub следующие образы:
     * mysql:8.0;
     * postgres:12-alpine;
     * node:latest.
 1. Установить плагин Docker в IDEA:
     * откройте IntelliJ IDEA, в настройках перейдите в раздел плагинов: File -> Settings -> Plugins;
     * в разделе Plugins в поиске введите docker;
     * нажмите на кнопку Install плпгина Docker, после установки перезапустите IDEA.
 1. Открыть проект в IntelliJ IDEA:
     * в корне папки проекта открыть файл build.gradle;
     * загрузить все плагины и зависимости нажав на кнопку ![img.png](img.png).
 1. Установить плагин Lombok в IDEA:
    * откройте IntelliJ IDEA, в настройках перейдите в раздел плагинов: File -> Settings -> Plugins;
    * в разделе Plugins в поиске введите lombok;
    * нажмите на кнопку Install плпгина Lombok, после установки перезапустите IDEA;
    * после перезагрузки IDEA, в настройках перейдите в раздел Annotation Processors: File -> Settings -> поиск annotation processors;
    * в открывшемся разделе Annotation Processors поставьте галочку в чекбоксе "Enable annotation processing";
    * нажмите на кнопки Apply и Ok.
 1. На проверочном устройстве должна быть выставлена Автоматическая установка даты и времени.
 1. Запустить Docker desktop (если Вы закрыли данное приложение).
 1. В IDEA открыть Terminal:
    * в первой вкладке терминала, для запуска необходимых контейнеров, ввести команду: docker-compose up;
    * дождаться сообщения о готовности плагина к подключению;
    * создать вторую вкладку терминала:
       * во второй вкладке терминала, для запуска приложения совместно БД MySQl, ввести команду: java -jar artifacts/aqa-shop.jar; 
       * во второй вкладке терминала, для запуска приложения совместно БД PostgreSQL, ввести команду: java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/kuularbase -Dspring.datasource.username=kuular -Dspring.datasource.password=12345v -jar artifacts/aqa-shop.jar.
       * если тестируем на OC Windows, и приложение совместно БД PostgreSQL не запустилось, ввести команду: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/kuularbase" "-Dspring.datasource.username=kuular" "-Dspring.datasource.password=12345v" -jar artifacts/aqa-shop.jar
 1. Запустить тесты в новой вкладке терминала командой: 
    * если тестируем совместно с БД MySQl: ./gradlew clean test;
    * если тестируем совместно с БД PostgreSQL: ./gradlew test -Durl=jdbc:postgresql://localhost:5432/kuularbase -DuserName=kuular -Dpassword=12345v.



[![Build status](https://ci.appveyor.com/api/projects/status/o5tx26dok5vpi8dc?svg=true)](https://ci.appveyor.com/project/DmitriiKuular/diplomqa)