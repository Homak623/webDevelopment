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
<h1>Подробная информация о товаре</h1><hr>
<#list product.images as image>
    <#if image?has_content>
<img src="/images/${image.id}" height="60px"/>
    </#if>
</#list>
<hr>
<b>Название товара:</b> ${product.title}<br>
<b>Описание:</b> ${product.description}<br>
<b>Цена:</b> ${product.price}<br>
<b>Город:</b> ${product.city}<br>
<b>Автор:</b> ${product.author}<br>
<form action="/product/delete/${product.id}" method="post">
    <input type="submit" value="Удалить товар"/>
</form>
<h2>Редактировать товар</h2>
<form action="/product/update/${product.id}" method="post">
    <input type="hidden" name="_method" value="PATCH"/>

    Название:<input type="text" name="title"/><br><br>

    Описание:<input type="text" name="description"/><br><br>

    Цена:<input type="number" name="price"/><br><br>

    Город:<input type="text" name="city" /><br><br>

    Автор:<input type="text" name="author" /><br><br>

    <button type="submit">Сохранить изменения</button>
</form>

</body>
</html>