function showusername()
{
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

    ajaxGet("http://localhost:8080/10000/paper",(text)=>{
        console.log(text);
        let json = JSON.parse(text);
        for (let i in json) {
            document.getElementById("time_"+i).innerText = json[i].timestamp;
            document.getElementById("nickname_"+i).innerText = json[i].person.nickname;
            document.getElementById("title_"+i).innerText = json[i].paper.title;
            document.getElementById("content_"+i).innerHTML = json[i].paper.content;
        }
    })
}

