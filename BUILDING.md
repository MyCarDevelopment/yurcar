# Сборка MyCar2

## Окружение

- JDK 17 для запуска Gradle и компиляции Java/Kotlin.
- Android SDK Platform 35.
- Gradle запускается через wrapper проекта: `gradlew.bat`.

В Android Studio выберите JDK 17 в Settings → Build, Execution, Deployment →
Build Tools → Gradle → Gradle JDK. Для командной строки задайте `JAVA_HOME`
на свой JDK 17. Абсолютный путь к JDK не следует добавлять в `gradle.properties`:
у каждого разработчика он свой.

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
```

При первом запуске нужен интернет для загрузки зависимостей.

## Библиотеки и совместимость

- `minSdk 24`: минимальная версия ОС — Android 7.0.
- `compileSdk 35` и `targetSdk 35` не повышают минимальную версию ОС.
- Версии Kotlin и плагина Compose Compiler должны совпадать.
- Версии Compose управляются одним BOM для приложения и UI-тестов.
- Для Lifecycle и Navigation используются общие переменные версий в `app/build.gradle`.
- Версии Firebase управляются Firebase BOM.
- Репозитории зависимостей задаются только в `settings.gradle`.

Текущие версии закреплены; это не означает, что они самые новые.
Перед обновлением проверяйте требования к minSdk, compileSdk, Kotlin и AGP.
Не используйте динамические версии (`+`).

Геолокация карты использует платформенный LocationManager через AndroidX и не
требует Google Play services. Доступность и точность зависят от провайдеров
устройства, настроек геолокации и выданных разрешений.
Наличие подходящей версии Android само по себе не гарантирует работу всех функций:
нужны проверки на устройствах, включая старые Android и устройства без Google.
