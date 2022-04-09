# Дипломный проект. API тестирование для <a target="_blank" href="https://reqres.in/">reqres.in</a>

## :pushpin: Содержание:

- [Технологии и инструменты](#rocket-технологии-и-инструменты)
- [Тест кейсы](#scroll-тест-кейсы)
- [Сборка в Jenkins](#jenkins-job)
- [Запуск из терминала](#computer-пример-запуска-из-терминала)
- [Allure отчет](#отчет-в-allure-report)
- [Интеграция с Allure TestOps](#интеграция-с-allure-testops)
- [Отчет в Telegram](#уведомление-в-telegram-при-помощи-бота)

## :rocket: Технологии и инструменты

<p align="center">
<a href="https://www.java.com/"><img src="images/logos/Java.svg" width="50" height="50"  alt="Java"/></a>
<a href="https://www.jetbrains.com/idea/"><img src="images/logos/Intelij_IDEA.svg" width="50" height="50"  alt="IDEA"/></a>
<a href="https://github.com/"><img src="images/logos/Github.svg" width="50" height="50"  alt="Github"/></a>
<a href="https://junit.org/junit5/"><img src="images/logos/JUnit5.svg" width="50" height="50"  alt="JUnit 5"/></a>
<a href="https://gradle.org/"><img src="images/logos/Gradle.svg" width="50" height="50"  alt="Gradle"/></a>
<a href="https://rest-assured.io/"><img src="images/logos/Rest-Assured.svg" width="50" height="50"  alt="Rest-assured"/></a>
<a href="https://github.com/allure-framework/allure2"><img src="images/logos/Allure_Report.svg" width="50" height="50"  alt="Allure"/></a>
<a href="https://www.jenkins.io/"><img src="images/logos/Jenkins.svg" width="50" height="50"  alt="Jenkins"/></a>
<a href="https://www.jenkins.io/"><img src="images/logos/Allure_EE.svg" width="50" height="50"  alt="Jenkins"/></a>
</p>

## :scroll: Тест кейсы

- ✓ Проверка списка пользователей.
- ✓ Проверка отдельного пользователя.
- ✓ Проверка ответа "Пользователь не найден".
- ✓ Проверка списка ресурсов.
- ✓ Проверка отдельного ресурса.
- ✓ Проверка ответа "Ресурс не найден".
- ✓ Проверка создания нового ресурса.
- ✓ Проверка создания нового пользователя.
- ✓ Проверка обновления данных пользователя PUT запрос.
- ✓ Проверка обновления данных пользователя PATCH запрос.
- ✓ Проверка удаления пользователя.
- ✓ Проверка успешной регистрации пользователя.
- ✓ Проверка ошибки регистрации пользователя.
- ✓ Проверка успешного входа пользователя.
- ✓ Проверка ошибки входа пользователя.

## <img src="images/logos/Jenkins.svg" width="25" height="25"  alt="Jenkins"/></a>Jenkins job
### <a target="_blank" href="https://jenkins.autotests.cloud/job/berezkindv_diploma_rest_api_tests_project/">Сборка в Jenkins</a>

Инструкция по запуску сборки в Jenkins:
<p align="center">
<a href="https://jenkins.autotests.cloud/job/berezkindv_diploma_rest_api_tests_project/"><img src="images/screenshots/jenkins_parameters.png" alt="Jenkins"/></a>
</p>

По завершении сборки можно посмотреть Allure отчет или перейти к Allure TestOps:
<p align="center">
<a href="https://jenkins.autotests.cloud/job/berezkindv_diploma_rest_api_tests_project/"><img src="images/screenshots/jenkins_reports.png" alt="Jenkins"/></a>
</p>



### Параметры сборки в Jenkins:

- BRANCH - выбор ветки репозитория GitHub (по умолчанию main)
- TASK - выбор задачи (настройка build.gradle, по умолчанию api_tests) 
- ALLURE_NOTIFICATIONS_VERSION - выбор версии библиотеки <a target="_blank" href="https://github.com/qa-guru/allure-notifications">Allure notifications</a> (по умолчанию 2.2.3)

### :computer: Пример запуска из терминала

Локальный запуск:
```bash
gradle clean test
```

## <img src="images/logos/Allure_Report.svg" width="25" height="25"  alt="Allure"/></a>Отчет в <a target="_blank" href="https://jenkins.autotests.cloud/job/berezkindv_diploma_ui_tests_project/10/allure/">Allure report</a>

### Основное окно

<p align="center">
<img title="Allure Overview Dashboard" src="images/screenshots/allure_report_dashboard.png">
</p>

### Вкладка тесты

<p align="center">
<img title="Allure Tests" src="images/screenshots/allure_report_test_cases.png">
</p>

### Вкладка графики

<p align="center">
<img title="Allure Graphics" src="images/screenshots/allure_report_graphs.png">
</p>

### В проекте используется кастомное оформление отчетов запросов и ответов

<p align="center">
<img title="Allure Graphics" src="images/screenshots/allure_report_custom_response.png">
</p>

## <img src="images/logos/Allure_EE.svg" width="25" height="25"  alt="Allure"/></a>Интеграция с <a target="_blank" href="https://allure.autotests.cloud/launch/12075">Allure TestOps</a>

### Дашборд

<p align="center">
<img title="Allure TestOps Dashboard" src="images/screenshots/teastops_dashboard.png">
</p>

### Тест-кейсы

<p align="center">
<img title="Allure TestOps Tests" src="images/screenshots/teastops_test_cases.png">
</p>

### История запусков сборки

<p align="center">
<img title="Allure TestOps Tests" src="images/screenshots/teastops_test_cases.png">
</p>

## <img src="images/logos/Telegram.svg" width="25" height="25"  alt="Allure"/></a>Уведомление в Telegram при помощи бота
По завершении всех операций телеграм бот отправит отчет со статистикой:
<p align="center">
<img title="Allure Overview Dashboard" src="images/screenshots/telegram_bot.png">
</p>