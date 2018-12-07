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
        document.getElementById("username").innerHTML = text;
    })
}

