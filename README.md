## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Список выполненных задач:

1. Изучена структура проекта
2. Удалены классы VkOAuth2UserDataHandler и YandexOAuth2UserDataHandler, удалены поля в файле login.html, register.html,
   удалены конфигурации vk и yandex из yaml файла. в файл changelog.sql добавлен sql-код для удаления vk данных
3. Чувствительная информация вынесена в файл application.properties. При наличии переменных окружения они имеют высший
   приоритет, чем значения в файле properties
4. Тесты переведены в testcontainers на базе postgresql
5. Написаны тесты для всех публичных методов контроллера ProfileRestController.
6. Добавлены методы в DashBoardUIController, TaskService и TaskRepository (без фронта)
7. Добавлена возможность подписываться на задачи, которые не назначены на текущего пользователя (отсутствует проверка по
   БД на уже существующую запись, отсутствует проверка на роль юзера, отсутствует фронт)
11. Выполнена интернационализация приложения (теперь поддерживаются русский и английский язык)