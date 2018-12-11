function showMoments() {
    pages --;
    console.log("BANG!");

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

    ajaxGet("http://localhost:8080/10002/paper?limit=" + pages * itemInPages, (text) => {
            console.log(text);
            let json = JSON.parse(text);
            let oldContainer = document.getElementById("momentContainer");
            let container = document.createElement("p");
            container.id = "momentContainer";
            let body = oldContainer.parentElement;
            body.removeChild(oldContainer)
            body.append(container);
            for (let i in json) {
                let oneContainer = document.createElement("form");
                let titleTR = document.createElement("p");
                let temp = document.createElement("p");
                temp.innerText = json[i].timestamp;
                titleTR.append(temp);
                temp = document.createElement("p");
                temp.innerText = json[i].person.nickname;
                titleTR.append(temp);
                temp = document.createElement("p");
                temp.innerText = json[i].paper.title;
                titleTR.append(temp);
                let contantTR = document.createElement("p");
                temp = document.createElement("p");
                temp.innerText = json[i].paper.content;
                contantTR.append(temp);
                oneContainer.append(titleTR);
                oneContainer.append(contantTR);
                container.append(oneContainer);
            }
            end = false;
            loadMoreMoments()
        }
    )
}

function loadMoreMoments() {
    console.log("BANG!");

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

    ajaxGet("http://localhost:8080/10002/paper?skip=" + pages * itemInPages + "&limit=" + itemInPages, (text) => {
            console.log(text);
            let json = JSON.parse(text);
            let container = document.getElementById("momentContainer");
            if (json.length == 0) {
                let endContainer = document.createElement("p");
                endContainer.innerText = "没有更早的信息了~";
                end = true;
                container.appendChild(endContainer);
            }
            for (let i in json) {
                let oneContainer = document.createElement("form");
                let titleTR = document.createElement("p");
                let temp = document.createElement("p");
                temp.innerText = json[i].timestamp;
                titleTR.append(temp);
                temp = document.createElement("p");
                temp.innerText = json[i].person.nickname;
                titleTR.append(temp);
                temp = document.createElement("p");
                temp.innerText = json[i].paper.title;
                titleTR.append(temp);
                let contantTR = document.createElement("p");
                temp = document.createElement("p");
                temp.innerText = json[i].paper.content;
                contantTR.append(temp);
                oneContainer.append(titleTR);
                oneContainer.append(contantTR);
                container.append(oneContainer);
            }
            pages++;
            synch = true;
            if (!end && getDocumentTop() + getWindowHeight() == getScrollTop()) {
                synch = false;
                loadMoreMoments();
            }
        }
    )
}