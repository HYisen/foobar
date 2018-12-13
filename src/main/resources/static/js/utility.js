(function (msg) {
    console.log(msg);
})("Hello");

let changeUid = (newUid) => {
    console.log(uid + " -> " + newUid);
    uid = newUid;
};