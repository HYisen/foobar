function loginLoad() {
    let str=location.href;

    if (str.indexOf("error") >= 0)
    {
        document.getElementById("demo").innerHTML="error";
    };

}