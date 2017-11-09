function drawRecentPictures() {
    fsGetPicturesMeta(PICTURES_LIMIT, drawPicture);
}

function drawPicture(url) {
    var openPictureLink = document.createElement("a");
    openPictureLink.setAttribute("href", url);
    openPictureLink.setAttribute("target", "_blank");

    var pic = document.createElement("img");
    pic.setAttribute("class", "pic");
    pic.setAttribute("src", url);
    openPictureLink.appendChild(pic);

    var container = document.getElementById("pics-container");
    container.appendChild(openPictureLink);
}
