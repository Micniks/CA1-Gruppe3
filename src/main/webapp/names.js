/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeText(evt) {

    evt.preventDefault();
    let url = "api/groupmember/all";
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                document.getElementById("table_id").innerHTML = "<table>" + insertIntoTableHeaders(data) + insertIntoTableFooters(data) + "</table>";
            });
}

function insertIntoTableHeaders(map){
    let headers = Object.getOwnPropertyNames(map[0]);
    let result = "<tr><th>" + headers.join("</th><th>") + "</th><tr>";
    return result;
};

function insertIntoTableFooters(cars) {
    let htmlRows = "<tr>";
        cars.forEach(e => {
                let temp = Object.values(e).map(function (a){
                       return "<td>" + a + "</td>";
                   }).join("") + "</tr>";
        htmlRows += temp;           
    });
    console.log(htmlRows);
    return htmlRows;
};

//console.log(insertIntoTableHeaders(headersMap));
//document.getElementById("table_id").innerHTML = '<table>' + insertIntoTableHeaders(headersMap) + insertIntoTableFooters(cars) + "</table>";