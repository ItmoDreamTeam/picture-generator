function fsUploadPicture(onPictureUploaded) {
    var formData = new FormData();
    formData.append("picture", pictureAsBlob());

    $.ajax({
        url: API_ROOT + "/fs",
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
        url: API_ROOT + "/fs?limit=" + limit,
        method: "GET",
        success: function (response) {
            for (i = 0; i < response.length; i++) {
                onPictureReceived(API_ROOT + "/fs/" + response[i].id);
            }
        }
    });
}
