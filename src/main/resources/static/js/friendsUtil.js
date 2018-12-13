function showFriends() {
    console.log("friends!");

    let ajaxGet = (url, callback) => {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                callback(xmlhttp.responseText);
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    };

    ajaxGet("http://localhost:8080/api/" + uid + "/friend?limit=10000", (text) => {
            console.log(text);
            let json = JSON.parse(text);
            let oldContainer = document.getElementById("friendsContainer");
            let container = document.createElement("div");
            container.id = "friendsContainer";
            let body = oldContainer.parentElement;
            body.removeChild(oldContainer);
            body.append(container);
            for (let i in json) {
                let nickname = document.createElement("div");
                nickname.className = "nickname";
                nickname.innerText = json[i].nickname;
                let oneContainer = document.createElement("form");
                oneContainer.className = "oneContainer"
                oneContainer.append(nickname);
                container.append(oneContainer);
            }
        }
    )
}