function pictureAsBlob() {
    var dataURL = canvas.toDataURL();
    var bytes = atob(dataURL.split(',')[1]);
    var arr = new Uint8Array(bytes.length);
    for (var i = 0; i < bytes.length; i++) {
        arr[i] = bytes.charCodeAt(i);
    }
    return new Blob([arr], {type: 'image/png'});
}

VK.init({apiId: VK_APP_ID});

var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
canvas.setAttribute("width", PICTURE_WIDTH);
canvas.setAttribute("height", PICTURE_HEIGHT);

generatePicture();
