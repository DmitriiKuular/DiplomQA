### Краткое описание:
Тестированию подлежит приложение — веб-сервис, который предлагает купить тур обычной оплатой по дебетовой карте 
и в кредит по данным банковской карты.

 1. Общее количество тест-кейсов - 37 шт:

        1.1. Тест, проверяющий отображение текста в заголовке вкладки веб-страницы
        1.2. Тест на отправку формы, заполненной данными первой тестовой карты, и получение статуса из БД
        1.3. Тест на отправку формы, заполненной данными второй тестовой карты, и получение статуса из БД
        1.4. Тест на отправку пустой формы и получение соответствующих уведомлений
        1.5. Тест на валидацию поля "Номер карты". Поле заполнено номером из 15ти значений
        1.6. Тест на валидацию поля "Номер карты". Поле заполняется валидными и принимвет их, невалидными данными и не принимает их, и значениями более 16ти
        1.7. Тест на ввод данных максимального срока действия карты(+ 5 лет), по принципу граничных значений
        1.8. Тест на ввод данных истекшего срока действия карты на месяц, по принципу граничных значений
        1.9. Тест на ввод предыдущего года от текущего в поле "Год"
        1.10. Тест на ввод данных срока действия карты, превышающего максимальный (5лет), по принципу граничных значений
        1.11. Тест на ввод значения "00" в поле "Месяц"
        1.12. Тест на ввод 13 месяца в поле "Месяц"
        1.13. Тест на ввод данных в поле "Месяц" неверного формата
        1.14. Тест на ввод данных в поле "Год" неверного формата
        1.15. Тест на ввод невалидных данных в поле "Владелец" на кириллице
        1.16. Тест на ввод невалидных данных в поле "Владелец", включая спецсимволы
        1.17. Тест на ввод данных в поле "CVC/CVV" неверного формата
        1.18. Тест на заполнение невалидными данными полей 'Месяц', 'Год' и 'CVC/CVV'
        1.19. Тест на заполнение полей 'Месяц', 'Год' и 'CVC/CVV' данными, превышающими допустимое количество
        *Примечание - тесты с 1.2. по 1.19. проведены для обоих случаев(обычной оплаты и покупки в кредит)

 1. Процент успешных тест-кейсов - 60% (22 тест-кейса)
 1. Процент неуспешных тест-кейсов - 40% (15 тест-кейсов) - по данным тестам составлены баг-репорты(Issues)

### Общие рекомендации:
 1. Issues #3 и #4 носят скорее рекомендательный характер
 1. Необходимо уточнить у Заказчика некоторые условия, касаемые поля "Владелец":

        2.1. Должен ли пользователь вводить свое имя и фамилию в поле "Владелец" именно CAPSом (буквами в верхнем регистре)
        2.2. Какие именно спец-символы разрешается вводить в поле, с обязательным запретом всех остальных спец-символов
        2.3. Какое максимальное количество символов возможно ввести в поле "Владелец"
        2.4. Какое минимальное количество символов возможно ввести в поле "Владелец"
        2.5. Обязательно ли поле "Владелец" для заполнения, ведь в последнее время некоторые банки выдают неименные карты