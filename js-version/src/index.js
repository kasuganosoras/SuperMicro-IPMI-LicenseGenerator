var sha1hmac = require("./sha1hmac.js");

const input = document.getElementById("input");
const output = document.getElementById("output");
const button = document.getElementById("button");

function hex2bin(hex) {
  let bytes = [];

  for (let i = 0; i < hex.length - 1; i += 2) {
    bytes.push(parseInt(hex.substr(i, 2), 16));
  }

  return String.fromCharCode.apply(String, bytes);
}

/**
 *
 * @param {string} mac
 */
function getKey(mac) {
  return sha1hmac.Crypto.sha1_hmac(
    hex2bin(mac.replace(/:/g, "")),
    hex2bin("8544E3B47ECA58F9583043F8")
  )
    .toString()
    .substr(0, 24);
}

function handler() {
  output.value = getKey(input.value)
    .match(/..../g)
    .join("-");
}

button.addEventListener("click", handler);
