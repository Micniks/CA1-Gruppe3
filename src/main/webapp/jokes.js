/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function clicker(evt) {
    evt.preventDefault();
    changeText();
}

function clickRandom(evt) {
    evt.preventDefault();
    randomJoke();
}

function clickSearch(evt) {
    evt.preventDefault();
    let searchString = document.getElementById("idTxt").value;
    if (searchString.length === 0) {
        document.getElementById("result").innerHTML = "<p style='color: red'>" + "No ID, no joke, NO PROBLEM!" + "</p>";
    } else if (Number.isNaN(parseInt(searchString))) {
        document.getElementById("result").innerHTML = "<p style='color: red'>" + "an ID that is not a number... that's the real joke!" + "</p>";
    } else {
        findJoke();
    }

}
;

function changeText() {
    let url = "api/joke/all";
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                document.getElementById("table_id").innerHTML = "<table>" + insertIntoTableHeaders(data) + insertIntoTableFooters(data) + "</table>";
            });
}
;

function randomJoke() {
    let url = "api/joke/random";
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                document.getElementById("result").innerHTML = "<p>" + data["value"] + "</p>";
            });
}
document.getElementById("random").addEventListener("click", clickRandom);

function findJoke() {
    let searchId = parseInt(document.getElementById("idTxt").value);
    let url = "api/joke/id/" + searchId;
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                if (data !== null) {
                    document.getElementById("result").innerHTML = "<p> ID " + searchId + ": " + data["value"] + "</p>";
                } else {
                    document.getElementById("result").innerHTML = "<p style='color: red'>" + "No joke with that ID was found... Did you do that on purpose?" + "</p>";
                }
            });
}
document.getElementById("searchBtn").addEventListener("click", clickSearch);

function insertIntoTableHeaders(map) {
    let headers = Object.getOwnPropertyNames(map[0]);
    let result = "<tr><th>" + headers.join("</th><th>") + "</th><tr>";
    return result;
}
;


function insertIntoTableFooters(jokes) {
    let htmlRows = "<tr>";
    jokes.forEach(e => {
        let temp = Object.values(e).map(function (a) {
            return "<td>" + a + "</td>";
        }).join("") + "</tr>";
        htmlRows += temp;
    });
    console.log(htmlRows);
    return htmlRows;
}
;

changeText();