# UsefulExample
Репозиторий состоит из различных проектов, представляющих из себя полезные примеры для последующего их использования.   

## 1.ShareCompatExample
*ShareCompatExample* - проект в котором находятся примеры вызова других приложений и обмен с ними данными.

### Список функций продемонстрированных в проекте:
1. *shareContent()* - метод показывает простейший пример использования ShareCompat
2. *openMap(Uri value)* - метод показывает простейший способ позиционирования на карте
3. *openWebPage(Sring value)* - метод для простейшего открытия web-страницы(Требует **permission**)

## 2.PreferenceChangeListenerExample
*PreferenceChangeListenerExample* - проект в котором демонстрируется работа со слушателем изменения настроек SharedPreferences. **Важное примечание!!!** Добавленное в стандартную тему стиля обязательно: `<item name="preferenceTheme">@style/PreferenceThemeOverlay</item>`. Алгоритм работы(при условии что у нас есть какие-то настройки в `SharedPreferences`:
1. Реализуем слушателя в нужном нам месте
2. Регестрируем слушателя
3. По завершению работы с конкретным экземпляром слушателя, отменяем регистрацию