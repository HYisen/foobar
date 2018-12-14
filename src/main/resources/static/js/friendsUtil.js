function showFriends(type = 0) {
    console.log("friends!");

    let url = "http://localhost:8080/api/" + uid + (type == 0 ? "/friend" : type == 1 ? "/stranger" : "/invite/import") + "?limit=10000";

    let ajaxGet = (url, callback) => {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                callback(xmlhttp.responseText);
            }
        };
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    };

    ajaxGet(url, (text) => {
            console.log(text);
            let json = JSON.parse(text);
            let oldContainer = document.getElementById(type == 0 ? "friendsContainer" : type == 1 ? "strangersContainer" : "inviteContainer");
            let container = document.createElement("div");
            container.id = "friendsContainer";
            let body = oldContainer.parentElement;
            body.removeChild(oldContainer);
            body.append(container);
            for (let i in json) {
                let nickname = document.createElement("div");
                nickname.className = "friends_nickname";
                nickname.innerText = json[i].nickname;
                let oneContainer = document.createElement("form");
                oneContainer.className = "friends_oneContainer"
                oneContainer.append(nickname);
                container.append(oneContainer);
            }
        }
    )
}