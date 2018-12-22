function mySubmit() {
    console.log("hei");

    let old_password = document.getElementById("old_password").value;
    let new_password = document.getElementById("new_password").value;
    let new_nickname = document.getElementById("new_nickname").value;

    if (old_password == "")
    {
        alert("no old password!");
        return;
    }
    if (new_password == "" & new_nickname == "")
    {
        alert("no new password or new nickname!");
        return;
    }

    let userInfo = {
        "oldPassword": old_password,
        "newPassword": new_password,
        "nickname": new_nickname
    };

    const url = "http://localhost:8080/api/{uid}/userinfo";
    let ajaxGet = (url, callback) => {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                callback(xmlhttp.responseText);
            }
        }
        xmlhttp.open("POST", url, true);
        xmlhttp.setRequestHeader("CONTENT-TYPE", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify(userInfo));
    };

    ajaxGet(url, (text) => {
            window.location.href = "moments";
        }
    )
}