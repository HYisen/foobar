function showMoments(isUpdate = false, isFocus = false, isNew = false, focusID = uid) {
    lastIsFocus = isFocus;
    if (isNew) pages = 1;
    if (!isUpdate) {
        if (pages > 1) pages--;
        end = false;
    }
    if (!isFocus) focusID = uid;
    console.log("BANG!");
    lastFocusID = focusID;

    let url = "http://localhost:8080/api/" + focusID + (isFocus ? "/paper" : "/moment") + "?limit=" + (isUpdate ? itemInPages + "&skip=" : "") + pages * itemInPages;

    console.log(url);
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

    ajaxGet(url, (text) => {
        console.log(text);
        let json = JSON.parse(text);
        let container;
        if (!isUpdate) {
            let oldContainer = document.getElementById("momentContainer");
            container = document.createElement("div");
            container.id = "momentContainer";
            let body = oldContainer.parentElement;
            body.removeChild(oldContainer);
            body.append(container);
        } else
            container = document.getElementById("momentContainer");
        if (json.length == 0) {
            let endContainer = document.createElement("p");
            endContainer.innerText = "没有更早的信息了~";
            end = true;
            container.appendChild(endContainer);
        }
        for (let i in json) {
            let oneContainer = document.createElement("form");
            oneContainer.className = 'oneContainer';

            let time = document.createElement("div");
            time.className = "time";
            time.innerText = getLocalTime(json[i].timestamp / 1000);
            oneContainer.append(time);

            let nickname = document.createElement("div");
            nickname.className = "nickname";
            nickname.innerText = json[i].person.nickname;
            oneContainer.append(nickname);

            let title = document.createElement("div");
            title.className = "title";
            title.innerText = json[i].paper.title;
            oneContainer.append(title);

            if (uid == json[i].person.uid) {
                let delButton = document.createElement("button")
                delButton.type = "button";
                delButton.className = "delete_button";
                delButton.innerText = "delete";
                delButton.onclick = function () {
                    deletePaper(uid, json[i].paper.pid);
                };
                oneContainer.append(delButton);
            }

            oneContainer.append(document.createElement("br"));
            let content = document.createElement("div");
            content.className = "content";
            content.innerHTML = (json[i].paper.content).replace(/</g, "&lt")
                .replace(/>/g, "&gt")
                .replace(/\n/g, "<br/>")
                .replace(/&ltimg width=(\d+)% height=(\d+)% src = "(.*)"&gt/g, "<img width=$1% height=$2% src = \"$3\">");
            oneContainer.append(content);


            container.append(oneContainer);
        }
        synch = true;
        if (isUpdate) {
            pages++;
        }
        if (!end && getDocumentTop() + getWindowHeight() == getScrollTop()) {
            synch = false;
            showMoments(true, isFocus, false, focusID);
        }
    }
        )
}

function postPaper() {
    console.log("hei");
    let title_con = document.getElementById("paper_form_title");
    let content_con = document.getElementById("paper_form_content");

    let post_title = title_con.value;
    let post_content = content_con.value;

    if (post_title == "" || post_content == "") {
        alert("no title or content!");
        return;
    }
    let paper = {
        "title": post_title,
        "content": post_content
    };

    title_con.value = "";
    content_con.value = "";

    let url = "http://localhost:8080/api/" + uid + "/paper";
    console.log(url);
    let ajaxGet = (url, callback) => {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                callback(xmlhttp.responseText);
            }
        }
        xmlhttp.open("POST", url, true);
        xmlhttp.setRequestHeader("CONTENT-TYPE", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify(paper));
    };

    ajaxGet(url, (text) => {
        showMoments(false, lastIsFocus, true, lastFocusID);
    }
        )
}

function deletePaper(uid, pid) {
    console.log("delete");

    let url = "http://localhost:8080/api/" + uid + "/paper/" + pid;
    console.log(url);
    let ajaxGet = (url, callback) => {
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                callback(xmlhttp.responseText);
            }
        }
        xmlhttp.open("DELETE", url, true);
        xmlhttp.send();
    };

    ajaxGet(url, (text) => {
        showMoments(false, lastIsFocus, true, lastFocusID);
    }
        )
}

function inputPicture() {
    let pictureURLInput = document.getElementById("pictureURL");
    let post_content = document.getElementById("paper_form_content");

    let pictureURL = pictureURLInput.value;

    post_content.value = post_content.value + "<img width=20% height=20% src = \"" + pictureURL + "\">";

    pictureURLInput.value = "";

}