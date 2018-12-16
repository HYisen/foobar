(function (msg) {
    console.log(msg);
})("Hello");

let changeUid = (newUid) => {
    console.log(uid + " -> " + newUid);
    uid = newUid;
};

function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString();
}