<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>My First Shop</title>
</head>
<body>
<h1>My First Shop</h1>
<hr>
<p>Товары со всей Беларуси</p>
<form action="/" method="get">
    Поиск по:
    <label>Название обьявления: <input type="text" name="title" value="${product.title!}" /></label><br>
    <label>Описание обьявления: <input type="text" name="description" value="${product.description!}" /></label><br>
    <label>Цена: <input type="number" name="price" min="0" step="1" value="${product.price!}" /></label><br>
    <label>Город: <input type="text" name="city" value="${product.city!}" /></label><br>
    <label>Автор: <input type="text" name="author" value="${product.author!}" /></label><br>
    <input type="submit" value="Поиск">
</form>

<#if products?has_content>
    <#list products as product>
        <div>
            <p>
                <b>${product.title}</b> ${product.price} |
                <a href="/product/${product.id}">Подробнее</a>
            </p>
        </div>
    </#list>
<#else>
    <p>Товары не найдены.</p>
</#if>

<hr>
<h3>Создать новый товар</h3>
<form action="/product/create" method="post" enctype="multipart/form-data">
    <label>Название обьявления: <input type="text" name="title" value="${product.title!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "title")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "title")[0].defaultMessage}
        </span>
    </#if>
    <br>

    <label>Описание обьявления: <input type="text" name="description" value="${product.description!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "description")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "description")[0].defaultMessage}
        </span>
    </#if>
    <br>

    <label>Цена: <input type="number" name="price" value="${product.price!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "price")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "price")[0].defaultMessage}
        </span>
    </#if>
    <br>

    <label>Город: <input type="text" name="city" value="${product.city!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "city")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "city")[0].defaultMessage}
        </span>
    </#if>
    <br>

    <label>Автор: <input type="text" name="author" value="${product.author!}" /></label>
    <#if errors?has_content && errors?filter(it -> it.field == "author")?has_content>
        <span style="color:red;">
            ${errors?filter(it -> it.field == "author")[0].defaultMessage}
        </span>
    </#if>
    <br>

    <label>Первая фотография: <input type="file" name="file1" /></label><br>
    <label>Вторая фотография: <input type="file" name="file2" /></label><br>
    <label>Третья фотография: <input type="file" name="file3" /></label><br>

    <input type="submit" value="Добавить товар" />
</form>
</body>
</html>