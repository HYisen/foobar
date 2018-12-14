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
                let time = document.createElement("div");
                time.className = "time";
                time.innerText = getLocalTime(json[i].timestamp / 1000);

                let nickname = document.createElement("div");
                nickname.className = "nickname";
                nickname.innerText = json[i].person.nickname;

                let title = document.createElement("div");
                title.className = "title";
                title.innerText = json[i].paper.title;

                let content = document.createElement("div");
                content.className = "content";
                content.innerHTML = json[i].paper.content;

                let oneContainer = document.createElement("form");
                oneContainer.className = 'oneContainer';

                oneContainer.append(time);
                oneContainer.append(nickname);
                oneContainer.append(title);
                oneContainer.append(content);

                container.append(oneContainer);
            }
            synch = true;
            if (isUpdate) {
                pages++;
            }
            if (!end && getDocumentTop() + getWindowHeight() == getScrollTop()) {
                synch = false;
                showMoments(true, isFocus);
            }
        }
    )
}