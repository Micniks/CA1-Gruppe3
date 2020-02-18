/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function clicker(evt){
    evt.preventDefault();
    changeText();
}

function clickRandom(evt){
    evt.preventDefault();
    randomJoke();
}

function changeText() {
    let url = "api/joke/all";
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                document.getElementById("table_id").innerHTML = "<table>" + insertIntoTableHeaders(data) + insertIntoTableFooters(data) + "</table>";
            });
};

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

function insertIntoTableHeaders(map) {
    let headers = Object.getOwnPropertyNames(map[0]);
    let result = "<tr><th>" + headers.join("</th><th>") + "</th><tr>";
    return result;
};


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