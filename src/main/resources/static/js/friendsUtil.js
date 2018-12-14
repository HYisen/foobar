function showFriends(isFriends = true) {
    console.log("friends!");

    let url = "http://localhost:8080/api/" + uid + (isFriends ? "/friend" : "/stranger") + "?limit=10000";

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
            let oldContainer = document.getElementById(isFriends?"friendsContainer":"strangersContainer");
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