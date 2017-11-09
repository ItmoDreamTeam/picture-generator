function fsUpload() {
    var username = "AwesomePictures";
    var password = "123456";

    fsCreateUser(username, password);

    var picName = Date.now() + ".png";

    var formData = new FormData();
    formData.append("file", pictureAsBlob(), picName);

    $.ajax({
        url: FS_ROOT_URL + "/user/" + username + "/file",
        method: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(username + ":" + password));
        },
        data: formData,
        processData: false,
        contentType: false,

        success: function () {
            alert("Successfully uploaded");
        }
    });
}

function fsCreateUser(username, password) {
    $.ajax({
        url: FS_ROOT_URL + "/signup?username=" + username + "&password=" + password,
        method: "POST",
        success: function (response) {
            console.log("User created. " + response);
        }
    });
}
