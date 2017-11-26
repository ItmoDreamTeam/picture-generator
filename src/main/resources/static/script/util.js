function convertImageToBlob(dataURL) {
    var bytes = atob(dataURL.split(',')[1]);
    var arr = new Uint8Array(bytes.length);
    for (var i = 0; i < bytes.length; i++) {
        arr[i] = bytes.charCodeAt(i);
    }
    return new Blob([arr], {type: 'image/png'});
}
