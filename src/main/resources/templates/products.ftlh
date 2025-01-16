<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<h1>My_first_shop</h1>
<hr>
Товары со всей России
<form action="/" method="get">
    Поиск по названию обьявления<input type="text" name="title"><br>
    <input type="submit" value="Поиск">
</form>
<#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} | <a href="/product/${product.id}">Подробнее</a></p>
    </div>
</#list>
<hr>
<h3>Создать новый товар</h3>
<form action="/product/create" method="post" enctype="multipart/form-data">
    Название обьявления: <input type="text" name="title"/><br>
    Описание обьявления: <input type="text" name="description"/><br>
    Цена: <input type="number" name="price"/><br>
    Город: <input type="text" name="city"/><br>
    Автор: <input type="text" name="author"/><br>
    Первая фотография: <input type="file" name="file1"/><br>
    Вторая фотография: <input type="file" name="file2"/><br>
    Третья фотография: <input type="file" name="file3"/><br>
    <input type="submit" value="Добавить товар"/>
</form>
</body>
</html>
