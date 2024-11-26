let X = 0;
let Y = 0;
let R = 1;

let status = document.getElementById('status');

class Point {
    constructor(x,y,r) {
        this.x=x;
        this.y=y;
        this.r=r;
    }
}

changeX(0);
changeY(0);
changeR(1);

function checkX(x) {
    return !isNaN(x) && x >= -5 && x <= 3;
}

function checkY(y) {
    return !isNaN(y) && y >= -5 && y <= 3;
}

function checkR(r) {
    return !isNaN(r) && r >= 1 && r <= 4;
}

async function sendPoints(points, toUpdate) {
    const request = {
        "update": ''+toUpdate
    };
    request.array=points;
    params = {
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
        if (response.url) {
            window.location.href = response.url;
        } else {
            
        }
    } else {
        await ooops(response);
    }
}

function changeCoords(x, y, r) {
    if (checkX(x) && checkY(y) && checkR(r)) {
        changeX(x);
        changeY(y);
        changeR(r);
    } else status.textContent = "введены некорректные координаты";
}

function changeX(x) {
    console.log(x + " " + X);
    if (checkX(x)) {
        X = x;
        document.getElementById("currX").textContent = (1 * X).toFixed(2);
    } else {
        status.textContent = "введена некорректная координата X";
    }
}

function changeY(y) {
    console.log(y + " " + Y);
    if (checkY(y)) {
        Y = y;
        document.getElementById("y").value = Y;
        document.getElementById("currY").textContent = (1 * Y).toFixed(2);
    } else {
        document.getElementById("r").value = Y;
        status.textContent = "введена некорректная координата Y";
    }
}

function changeR(r) {
    console.log(r + " " + R);
    if (checkR(r)) {
        redraw(r);
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
        changeX(this.value);
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
    changeR(event.target.value);
    drawPoint(X, Y, R);
});
document.querySelector('input[id="y"]').addEventListener('change', (event) => {
    changeY(event.target.value);
    drawPoint(X, Y, R);
});


async function send() {
    const formData = {
        X: "" + X,
        Y: "" + Y,
        R: "" + R
    };
    params = {
        method: 'POST',

        headers: {
            'Content-Type': 'application/json',
            'Content-Length': JSON.stringify(formData).length
        },
        body: JSON.stringify(formData)
    };

    Date.now();
    let response = await fetch('http://localhost:8080/web2_1-1.0-SNAPSHOT/controller', params);
    //console.log(params);
    if (response.ok) {
        if (response.url) {
            window.location.href = response.url;
        }
    } else {
        await ooops(response);
    }
}

async function ooops(response) {
    status.textContent = "сервер не смог расшифровать входные данные: " + response.statusText;
}


function drawPoint(x, y, r) {
    let newPoint = document.querySelector(".template").cloneNode(true);
    document.querySelectorAll("[last='true']").forEach((node) => {
        document.querySelector("svg").removeChild(node);
    });
    newPoint.setAttribute('last', 'true');
    document.querySelector("svg").appendChild(newPoint);
    newPoint.setAttribute("X", x);
    newPoint.setAttribute("Y", y);
    newPoint.setAttribute("R", r);
    newPoint.setAttribute("cx", (200 + x / r * 150));
    newPoint.setAttribute("cy", (200 - y / r * 150));
    newPoint.setAttribute('visibility', 'visible');
}

function redraw(r) {
    let points = document.querySelectorAll("circle");
    points.forEach(function (point) {
        let x = Number.parseFloat(point.getAttribute("x"));
        let y = Number.parseFloat(point.getAttribute("y"));
        point.setAttribute("R", r);
        point.setAttribute("cx", ''+(200 + x / r * 150));
        point.setAttribute("cy", ''+(200 - y / r * 150));
    })
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
    changeCoords(x / (150) * R, y / (150) * R, R);
    drawPoint(X, Y, R);
}

document.getElementById('svg').addEventListener('click', handleSvgClick);