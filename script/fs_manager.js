function fsUploadPicture(onPictureUploaded) {
    var formData = new FormData();
    formData.append("picture", pictureAsBlob());

    $.ajax({
        url: PROXY_ROOT_URL + "/fs",
        method: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function () {
            onPictureUploaded();
        }
    });
}

function fsGetPicturesMeta(limit, onPictureReceived) {
    $.ajax({
        url: PROXY_ROOT_URL + "/fs?limit=" + limit,
        method: "GET",
        success: function (response) {
            for (i = 0; i < response.length; i++) {
                onPictureReceived(PROXY_ROOT_URL + "/fs/" + response[i].id);
            }
        }
    });
}
