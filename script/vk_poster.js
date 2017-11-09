function vkPost() {
    VK.Auth.login(function (r) {
        vkGetUploadUrl();
    }, 4);
}

function vkGetUploadUrl() {
    console.log("getting upload url");
    VK.Api.call("photos.getWallUploadServer", {}, function (r) {
        if (r.response) {
            vkUploadPicture(r.response.upload_url);
        } else {
            console.log("failed to get upload url");
        }
    });
}

function vkUploadPicture(uploadUrl) {
    console.log("uploading picture");

    var formData = new FormData();
    formData.append("url", uploadUrl);
    formData.append("photo", pictureAsBlob());

    $.ajax({
        url: PROXY_ROOT_URL + "/vk/upload",
        method: "POST",
        data: formData,
        processData: false,
        contentType: false,

        success: function (response) {
            var responseDeserialized = JSON.parse(response);
            vkSavePicture(responseDeserialized.photo, responseDeserialized.server, responseDeserialized.hash);
        },
        error: function () {
            console.log("failed to upload");
        }
    });
}

function vkSavePicture(photo, server, hash) {
    console.log("saving picture");
    VK.Api.call("photos.saveWallPhoto", {photo: photo, server: server, hash: hash}, function (r) {
        if (r.response) {
            vkPostPicture(r.response[0].owner_id, r.response[0].id);
        } else {
            console.log("failed to save");
        }
    });
}

function vkPostPicture(ownerId, picId) {
    console.log("posting picture");
    VK.Api.call("wall.post", {owner_id: ownerId, attachments: picId}, function (r) {
        if (r.response) {
            console.log("PICTURE POSTED")
        } else {
            console.log("failed to post");
        }
    });
}
