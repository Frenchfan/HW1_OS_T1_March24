Данное дополнение содержит интеграционные тесты контроллеров

Для запуска можно использовать maven - 
test или package (если собираетесь деплоить
на сервер)
Поскольку результаты тестов не идемпотентны,
повторный запуск тестов без удаления бд, 
может привести к ошибкам. 

Однако первоначальный запуск проходит без
проблем:

![img.png](img.png)

![img_1.png](img_1.png)

![img_2.png](img_2.png)