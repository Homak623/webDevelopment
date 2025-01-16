<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Подробная информация о товаре</title>
</head>
<body>
<h1>Подробная информация о товаре</h1>
<hr>
<#list product.images as image>
    <#if image?has_content>
        <img src="/images/${image.id}" height="60px" />
    </#if>
</#list>
<hr>
<b>Название товара:</b> ${product.title}<br>
<b>Описание:</b> ${product.description}<br>
<b>Цена:</b> ${product.price}<br>
<b>Город:</b> ${product.city}<br>
<b>Автор:</b> ${product.author}<br>

<form action="/product/delete/${product.id}" method="post">
    <input type="submit" value="Удалить товар" />
</form>

<h2>Редактировать товар</h2>
<form action="/product/update/${product.id}" method="post">
    <input type="hidden" name="_method" value="PATCH" />

    <label>Название: <input type="text" name="title" value="${product.title!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "title")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "title")[0].defaultMessage}
        </span>
    </#if>
    <br><br>

    <label>Описание: <input type="text" name="description" value="${product.description!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "description")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "description")[0].defaultMessage}
        </span>
    </#if>
    <br><br>

    <label>Цена: <input type="number" name="price" value="${product.price!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "price")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "price")[0].defaultMessage}
        </span>
    </#if>
    <br><br>

    <label>Город: <input type="text" name="city" value="${product.city!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "city")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "city")[0].defaultMessage}
        </span>
    </#if>
    <br><br>

    <label>Автор: <input type="text" name="author" value="${product.author!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "author")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "author")[0].defaultMessage}
        </span>
    </#if>
    <br><br>

    <button type="submit">Сохранить изменения</button>
</form>

</body>
</html>