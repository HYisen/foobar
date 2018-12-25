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

const ajax = (url, method, content) => {
    let headers = new Headers();
    if (content != null) {
        headers.append('Content-Type', 'application/json');
    }
    let init = {
        method: method,
        headers: headers,
        // mode: 'cors',
    };
    if (content != null) {
        init.body = content instanceof Object ? JSON.stringify(content) : content;
    }
    return fetch(url, init)
        .then(response => {
            if (!response.ok) {
                throw new Error(`response is not ok when ${method} ${url} with code ${response.status}`)
            }
            const contentType = response.headers.get("Content-Type");
            if (contentType == null || !contentType.includes("application/json")) {
                throw new TypeError(`response Content-Type is not JSON when ${method} ${url}`);
            }
            return response.json();
        });
};

const build = (...ids) => {
    let rtn = {};
    for (const id of ids) {
        console.log(id);
        rtn[id] = document.getElementById(id).value;
    }
    return rtn;
};

const communicate = (subPath, method, content) => {
    const uid = document.getElementById('uid').value;
    return ajax(`/api/${uid}` + subPath, method, content);
};