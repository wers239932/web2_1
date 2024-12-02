let X = 0;
let Y = 0;
let R = 1;

let status = document.getElementById('status');

class Point {
    constructor(x,y,r) {
        this.X=x;
        this.Y=y;
        this.R=r;
    }
}

let pointArray = [];

//changeCoords(0,0,1);

function checkX(x) {
    return !isNaN(x) && x >= -5 && x <= 3;
}

function checkY(y) {
    return !isNaN(y) && y >= -5 && y <= 3;
}

function checkR(r) {
    return !isNaN(r) && r >= 1 && r <= 4;
}

function sendUpdate() {
    // noinspection JSIgnoredPromiseFromCall
    sendPoints(pointArray, true);
}

function sendGetHistory() {
    // noinspection JSIgnoredPromiseFromCall
    sendPoints(pointArray, false);
}

async function sendPoints(points, toUpdate) {
    const request = {
        "update": ''+toUpdate
    };
    request.points=points;
    let params = {
        method: 'POST',

        headers: {
            'Content-Type': 'application/json',
            'Content-Length': JSON.stringify(request).length
        },
        body: JSON.stringify(request)
    };
    let response = await fetch('http://localhost:8080/web2_1-1.0-SNAPSHOT/controller', params);
    //console.log(params);
    if (response.ok) {
        if (!toUpdate) {
            window.location.href = response.url;
        } else {
            await writePoints(response);
        }
    } else {
        await ooops(response);
    }
}

async function writePoints(response) {
    let json = await response.json();
    json.forEach(result => {
        let newRow = document.createElement("tr");
        let tdX = document.createElement("td");
        let tdY = document.createElement("td");
        let tdR = document.createElement("td");
        let tdTime = document.createElement("td");
        let tdResult = document.createElement("td");

        tdX.innerHTML = Number(result.point.x).toFixed(2);
        tdY.innerHTML = Number(result.point.y).toFixed(2);
        tdR.innerHTML = Number(result.point.r).toFixed(1);
        tdTime.innerHTML = result.time;
        tdResult.innerHTML = result.result;


        newRow.appendChild(tdTime);
        newRow.appendChild(tdX);
        newRow.appendChild(tdY);
        newRow.appendChild(tdR);
        newRow.appendChild(tdResult);

        let table = document.getElementById("history");
        table.after(newRow);
    });
}

function changeCoords(x, y, r) {
    if (checkX(x) && checkY(y) && checkR(r)) {
            pointArray.push(new Point(x, y, r));
            console.log("point added");
        changeX(x);
        changeY(y);
        changeR(r);
    } else status.textContent = "введены некорректные координаты";
    return checkX(x) && checkY(y) && checkR(r);
}

function changeX(x) {
    console.log(x + " " + X);
    if (checkX(x)) {
        X = x;
        document.getElementById("currX").textContent = (1 * X).toFixed(2);
    } else {
        status.textContent = "введена некорректная координата X";
    }
    return checkX(x);
}

function changeY(y) {
    console.log(y + " " + Y);
    if (checkY(y)) {
        Y = y;
        document.getElementById("y").value = Y;
        document.getElementById("currY").textContent = (1 * Y).toFixed(2);
    } else {
        document.getElementById("y").value = Y;
        status.textContent = "введена некорректная координата Y";
    }
    return checkY(y);
}

function changeR(r) {
    console.log(r + " " + R);
    if (checkR(r)) {
        if(R !== r)
            resize(r);
        R = r;
        document.getElementById("r").value = R;
        document.getElementById("currR").textContent = (1 * R).toFixed(2);
    } else {
        document.getElementById("r").value = R;
        status.textContent = "введена некорректная координата R";
    }
}

document.querySelectorAll('input[type="radio"]').forEach((box) => {

    box.addEventListener('change', function () {
        changeCoords(Number(this.value), Y, R);
        drawPoint(X, Y, R);
        if (this.checked) {
            document.querySelectorAll('input[type="radio"]').forEach((box) => {
                if (box !== this) {
                    box.checked = false;
                }
            });
        }
    });
});

document.querySelector('input[id="r"]').addEventListener('change', (event) => {
    changeR(Number(event.target.value));
    //drawPoint(X, Y, R);
});
document.querySelector('input[id="y"]').addEventListener('change', (event) => {
    changeCoords(X, Number(event.target.value), R);
    drawPoint(X, Y, R);
});


async function ooops(response) {
    status.textContent = "сервер не смог расшифровать входные данные: " + response.statusText;
}


function drawPoint(x, y, r) {
    let newPoint = document.querySelector(".template").cloneNode(true);
    // document.querySelectorAll("[last='true']").forEach((node) => {
    //     document.querySelector("svg").removeChild(node);
    // });
    newPoint.removeAttribute("class");
    //newPoint.setAttribute('last', 'true');
    document.querySelector("svg").appendChild(newPoint);
    newPoint.setAttribute("x", x);
    newPoint.setAttribute("y", y);
    newPoint.setAttribute("R", r);
    newPoint.setAttribute("cx", (200 + x / r * 150));
    newPoint.setAttribute("cy", (200 - y / r * 150));
    newPoint.setAttribute('visibility', 'visible');
}

function resize(r) {
    let points = document.querySelectorAll('circle');
    points.forEach(function (point) {
        let x = Number.parseFloat(point.getAttribute("x"));
        let y = Number.parseFloat(point.getAttribute("y"));
        console.log({x, y});
        point.setAttribute("R", r);
        point.setAttribute("cx", ''+(200 + x / r * 150));
        point.setAttribute("cy", ''+(200 - y / r * 150));
    })
    pointArray.forEach(point => point.R = r);
    sendUpdate();
}

function handleSvgClick(event) {
    if (isNaN(R)) {
        status.textContent = "Выберите R!";
        return;
    } else if (status.textContent !== "") {
        status.textContent = "";
    }
    document.querySelectorAll('input[type="radio"]').forEach((box) => {
        box.checked = false;
    });
    const rect = document.getElementById('svg').getBoundingClientRect();
    console.log(event.clientX, event.clientY, rect.height)
    const x = event.clientX - rect.left - rect.width / 2;
    const y = -(event.clientY - rect.top - rect.height / 2);
    if(changeCoords(x / (150) * R, y / (150) * R, R)) {
        drawPoint(X, Y, R);
    }
}

document.getElementById('svg').addEventListener('click', handleSvgClick);