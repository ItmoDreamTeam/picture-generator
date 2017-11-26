function pictureAsBlob() {
    return convertImageToBlob(canvas.toDataURL());
}

function onPictureUploadedToFs() {
    var container = document.getElementById("pics-container");
    while (container.firstChild) {
        container.removeChild(container.firstChild);
    }
    drawRecentPictures();
}

VK.init({apiId: VK_APP_ID});

var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
canvas.setAttribute("width", PICTURE_WIDTH);
canvas.setAttribute("height", PICTURE_HEIGHT);

generatePicture();
drawRecentPictures();
