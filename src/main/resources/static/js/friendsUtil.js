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
                oneContainer.className = "friends_oneContainer";
                oneContainer.append(nickname);
                switch (type) {
                    case 0:
                        let button0 = document.createElement("button");
                        button0.type = "button";
                        button0.className = "friends_button";
                        button0.innerText = "see his/her paper";
                        button0.onclick = function () {
                            showMoments(false, true, true, json[i].uid);
                        };
                        oneContainer.append(button0);

                        let button4 = document.createElement("button");
                        button4.type = "button";
                        button4.className = "friends_button";
                        button4.innerText = "break off";
                        button4.onclick = function () {
                            postFriendsRequest(4, json[i].uid);
                        };
                        oneContainer.append(button4);
                        break;
                    case 1:
                        let button1 = document.createElement("button");
                        button1.type = "button";
                        button1.className = "friends_button";
                        button1.innerText = "accept";
                        button1.onclick = function () {
                            postFriendsRequest(2, json[i].uid);
                        };
                        oneContainer.append(button1);

                        let button2 = document.createElement("button");
                        button2.type = "button";
                        button2.className = "friends_button";
                        button2.innerText = "reject";
                        button2.onclick = function () {
                            postFriendsRequest(3, json[i].uid);
                        };
                        oneContainer.append(button2);
                        break;
                    case 2:
                        let button3 = document.createElement("button");
                        button3.type = "button";
                        button3.className = "friends_button";
                        button3.innerText = "invite";
                        button3.onclick = function () {
                            postFriendsRequest(0, json[i].uid);
                        };
                        oneContainer.append(button3);
                        break;
                }
                ;

                oneContainer.tag = json[i].uid;
                container.append(oneContainer);
            }
        }
    )
}

function postFriendsRequest(type, dstUid) {         //0 for delInvite;1 for invite;2 for reject;3 for accept;4 for break off;
    console.log("invite!");

    let url;
    if (type == 2)
        utl = "http://localhost:8080/api/" + dstUid + "/invite/" + uid;
    else
        url = "http://localhost:8080/api/" + uid + (type == 3 ? "/accept/" : type == 4 ? "/friend/" : "/invite/") + dstUid;

    let ajaxGet = (url, callback) => {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                callback(xmlhttp.responseText);
            }
        };
        xmlhttp.open(type % 2 == 1 ? "POST" : "DELETE", url, true);
        xmlhttp.send();
    };

    ajaxGet(url, (text) => {
            console.log(text);
            showFriends();
            showFriends(1);
            showFriends(2);
        }
    )
}