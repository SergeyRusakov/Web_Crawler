# Web_Crawler

Simple Web Crawler project.
Parsing web pages and takes the titles.
To start, you have to write URL of start page into first text field and press the start button.
In the "workers" field, you can set the nuber of workers (threads), using in parsing (1-50). 1 is default.
Also, you can set the time limt and depth limit (so that workers do not go too deep into the web).
Label "parsed pages" shows current links found (but not parsed yet). 
To save the parsed links (link and title from the page) - write the file path and press the save button.

Парсит веб-страницы и забирает с них значение тега title. 
Для начала, необходимо ввести стартовый url в соответсвующее поле и нажать кнопку start.
В поле "workers" указывается количество работников (потоков), используемых при парсинге (от 1 до 50). 1 по умолчанию
Также, можно указать лимит времени и лимит "глубины" (так, программа не будет уходить слишком глбоко в сеть).
Строка "parsed pages" указывает на количество найденных на данный моент ссылок. 
Для сохранения ссылок в формате адрес - титул, необходимо указать путь к файлу и нажать на кнопку save. 
