function myRegister() {
    console.log("hei");

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let nickname = document.getElementById("nickname").value;

    if (username == "")
    {
        alert("no username!");
        return;
    }
    if (password == "")
    {
        alert("no password!");
        return;
    }
    if (nickname == "")
    {
        alert("no nickname!");
        return;
    }

    let info = {
        "username": username,
        "password": password,
        "nickname": nickname
    };

    let url = "http://localhost:8080/api/register";
    let ajaxGet = (url, callback) => {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                callback(xmlhttp.responseText);
            }
        }
        xmlhttp.open("POST", url, true);
        xmlhttp.setRequestHeader("CONTENT-TYPE", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify(info));
    };

    ajaxGet(url, (text) => {
        window.location.href = "heaven";
    }
        )
}