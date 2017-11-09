function getPictureURL() {
    return PROXY_ROOT_URL + "/picture/" + PICTURE_WIDTH + "/" + PICTURE_HEIGHT;
}

function generatePicture() {
    ctx.clearRect(0, 0, PICTURE_WIDTH, PICTURE_HEIGHT);
    var picture = new Image();
    picture.src = getPictureURL();
    picture.setAttribute("crossOrigin", "anonymous");
    picture.onload = function () {
        ctx.drawImage(picture, 0, 0);
        getQuote(addQuote);
    };
}

function addQuote(quote) {
    ctx.font = "24pt Calibri";
    ctx.fillStyle = "white";

    ctx.shadowColor = "black";
    ctx.shadowOffsetX = 2;
    ctx.shadowOffsetY = 2;
    ctx.shadowBlur = 5;

    var text = quote.quote + "\t©\t" + quote.author;
    var lines = [];
    var prevLineEnd = -1;
    var prevSpaceIndex = -1;
    var endOfLine = false;

    while (!endOfLine) {
        var spaceIndex = text.indexOf(" ", prevSpaceIndex + 1);
        var line;
        if (spaceIndex < 0) {
            line = text.substr(prevLineEnd + 1, text.length - prevLineEnd - 1);
            endOfLine = true;
        } else {
            line = text.substr(prevLineEnd + 1, spaceIndex - prevLineEnd - 1);
        }
        if (textWidth(line) >= PICTURE_WIDTH) {
            line = text.substr(prevLineEnd + 1, prevSpaceIndex - prevLineEnd - 1);
            prevLineEnd = prevSpaceIndex;
            lines.push(line);
        }
        if (endOfLine) {
            line = text.substr(prevLineEnd + 1, text.length - prevLineEnd - 1);
            lines.push(line);
        }
        prevSpaceIndex = spaceIndex;
    }

    drawText(lines);
}

function textWidth(text) {
    return Math.ceil(ctx.measureText(text).width);
}

function drawText(lines) {
    const LINE_HEIGHT = 30;
    for (i in lines) {
        ctx.strokeText(
            lines[i],
            (PICTURE_WIDTH - textWidth(lines[i])) / 2,
            PICTURE_HEIGHT - LINE_HEIGHT * (lines.length - i));
        ctx.fillText(
            lines[i],
            (PICTURE_WIDTH - textWidth(lines[i])) / 2,
            PICTURE_HEIGHT - LINE_HEIGHT * (lines.length - i));
    }
}
