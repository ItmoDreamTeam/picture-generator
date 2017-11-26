const QUOTE_URL = "https://andruxnet-random-famous-quotes.p.mashape.com";
const QUOTE_API_KEY = "CdbBmWOrBfmshc7t9INffynYmXwNp1JFGbpjsny0dUKurJRhrP";

function getQuote(onQuoteReceived) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", QUOTE_URL);
    xhr.setRequestHeader("X-Mashape-Key", QUOTE_API_KEY);
    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                var quote = JSON.parse(this.responseText);
                console.log(quote);
                onQuoteReceived(quote);
            } else {
                console.log("failed to get quote, status = " + this.status);
            }
        }
    };
    xhr.send();
}
