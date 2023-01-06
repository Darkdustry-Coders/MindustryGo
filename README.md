<div align="center">
    <h1>Mindustry Go</h1>
    <p>(В разработке).</p>
    <p>Игровой режим основанный на игре CS:GO.</p>
</div>

<br>

## Contributing

Все необходимые правила и советы расписаны
в [CONTRIBUTING](https://github.com/Darkdustry-Coders/DarkdustryPlugin/blob/master/CONTRIBUTING.md).

## Стиль кода

Вы можете писать код как вам нравится (имеется в виду стиль), но перед отправкой изменений, вам необходимо
воспользоваться prettier-java, чтобы код
стал читабельным для остальных разработчиков плагина. Для prettier используется [конфигурация](.prettierrc.json) в
файлах проекта.

```shell
prettier --write "**/*.java"
```

## Компиляция

Gradle может потребоваться до нескольких минут для загрузки файлов. <br>
После сборки выходной jar-файл должен находиться в каталоге `/build/libs/DarkdustryPlugin.jar`.

Сначала убедитесь, что у вас установлен JDK 18. Откройте терминал в каталоге проекта и выполните следующие команды:

### Windows

_Компиляция:_ `gradlew jar`

### Linux/Mac OS

_Компиляция:_ `./gradlew jar`

### Устранение неполадок

#### Permission Denied

Если терминал выдает `Permission denied` или `Command not found` на Mac/Linux, выполните `chmod +x ./gradlew` перед
запуском `./gradlew`. *Это одноразовая процедура.*
