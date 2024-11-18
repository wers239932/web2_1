<%@ page import="beans.History" %>
<%@ page import="java.lang.Math" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="history" class="beans.History" scope="session"/>

<!DOCTYPE html>
<html>
<head>
    <title>lab 2</title>
    <link rel="stylesheet" href="styles/index.css">
</head>
<body>
<header>
    <div class="header-content">
        <h1>Лабораторная работа по вебу №1</h1>
        <h2>Багманов Владимир Алексеевич</h2>
        <h2>Группа P3230, Вариант №408190</h2>
    </div>
</header>
<div id="mainTable">
    <div>
        <div id="quest">
            <div class="longRow">Ваша точка:</div>
            <div>Введите первую координату</div>
            <div>
                <div>
                    <label>
                        <input type="checkbox" name="x" id="x-5" value="-5">
                        -5
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x-4" value="-4">
                        -4
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x-3" value="-3">
                        -3
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x-2" value="-2">
                        -2
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x-1" value="-1">
                        -1
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x0" value="0">
                        0
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x1" value="1">
                        1
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x2" value="2">
                        2
                    </label>
                    <label>
                        <input type="checkbox" name="x" id="x3" value="3">
                        3
                    </label>
                </div>
            </div>

            <div>Введите вторую координату</div>
            <div><input class="illuminated animated" type="text" placeholder="(от -5 до 3)" id="y"></div>


            <div>Выберите масштаб карты</div>
            <div><input class="illuminated animated" type="text" placeholder="(от 1 до 4)" id="r"></div>


            <div class="longRow">выбранные координаты: (<span id="currX">1</span>, <span id="currY">2</span>, <span
                    id="currR">3</span>)
            </div>

            <div class="longRow">
                <button onclick="send()">Проверить</button>
            </div>
            <span class="longRow" id="status">
      </span>
        </div>
        <svg xmlns="http://www.w3.org/2000/svg" width="400" height="400" id="svg">
            <line x1="0" y1="200" x2="400" y2="200" stroke="#000720"></line>
            <line x1="200" y1="0" x2="200" y2="400" stroke="#000720"></line>
            <line x1="350" y1="198" x2="350" y2="202" stroke="#000720"></line>
            <text x="355" y="195">R</text>
            <line x1="275" y1="198" x2="275" y2="202" stroke="#000720"></line>
            <text x="280" y="195">R/2</text>
            <line x1="125" y1="198" x2="125" y2="202" stroke="#000720"></line>
            <text x="123" y="195">-R/2</text>
            <line x1="50" y1="198" x2="50" y2="202" stroke="#000720"></line>
            <text x="55" y="195">-R</text>
            <line x1="198" y1="50" x2="202" y2="50" stroke="#000720"></line>
            <text x="204" y="55">R</text>
            <line x1="198" y1="125" x2="202" y2="125" stroke="#000720"></line>
            <text x="204" y="130">R/2</text>
            <line x1="198" y1="275" x2="202" y2="275" stroke="#000720"></line>
            <text x="204" y="280">-R/2</text>
            <line x1="198" y1="350" x2="202" y2="350" stroke="#000720"></line>
            <text x="204" y="355">-R</text>

            <polygon points="400,200 395,205 395, 195" fill="#000720" stroke="#101F27"></polygon>
            <polygon points="200,0 195,5 205,5" fill="#000720" stroke="#101F27"></polygon>

            <polygon points="200,200 200,125 275,200" fill-opacity="0.6" stroke="black" fill="blue"></polygon>

            <rect x="50" y="125" width="150" height="75" fill-opacity="0.6" stroke="black" fill="blue"></rect>


            <path d="M 200 200 L 200 350 A 150 150 0 0 1 50 200 Z" fill-opacity="0.6" stroke="black"
                  fill="blue"></path>

            <c:forEach var="res" items="${history.history.reversed()}">
                <circle r="8" R="8" X="${res.point().x}" Y="${res.point().y}"
                        cx="${200+res.point().x/res.point().r*150}" cy="${200-res.point().y/res.point().r*150}"
                        fill-opacity="0.8" fill="#3549fc" stroke="#4d00b8"
                        visibility="visible"></circle>
            </c:forEach>

            <circle class="template" r="8" R="8" X="0" Y="0" cx="200" cy="200" fill-opacity="0.8" fill="#ff49fc"
                    stroke="#4d00b8"
                    visibility="hidden"></circle>
        </svg>
    </div>

    <h4>
        <table class="history" id="history">
            <tr class="thead">
                <th>Время</th>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Результат</th>
            </tr>
            <tbody>
            <c:forEach var="res" items="${history.history.reversed()}">
                <tr>
                    <td>${res.getTime()}</td>
                    <td>${Math.round(res.point().x*100)/100}</td>
                    <td>${Math.round(res.point().y*100)/100}</td>
                    <td>${Math.round(res.point().r*100)/100}</td>
                    <td>${res.result()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </h4>

</div>

<script type="application/javascript" src="script.js"></script>
</body>
</html>