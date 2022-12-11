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
 1. В IDEA открыть Terminal:
    * в первой вкладке терминала ввести команду: docker-compose up;
    * создать вторую вкладку терминала;
    * во второй вкладке терминала ввести команду: java -jar artifacts/aqa-shop.jar. 
 1. На проверочном устройстве должна быть выставлена Автоматическая установка даты и времени.

 **Можно запускать тесты.**


[![Build status](https://ci.appveyor.com/api/projects/status/o5tx26dok5vpi8dc?svg=true)](https://ci.appveyor.com/project/DmitriiKuular/diplomqa)