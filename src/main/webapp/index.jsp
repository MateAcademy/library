<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Main page</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%; /* Ширина таблиці, можна змінити відповідно до ваших потреб */
            margin: auto;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .h1 {
            text-align: center;
            font-size: 30px;
        }

        .h2 {
            text-align: center;
            font-size: 20px;
        }
    </style>
</head>
<body>

    <br>
    <hr>
    <h2 class="h2"><a href="/people/">Show all people</a></h2>
    <h2 class="h2"><a href="/people/new">Add new person</a></h2>
    <hr>
    <h2 class="h2"><a href="/book/">Show all books</a></h2>
    <h2 class="h2"><a href="/book/new">Add new book</a></h2>
    <hr>

</body>
</html>

