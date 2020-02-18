/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function clicker(evt){
    evt.preventDefault();
    changeText();
}

function changeText() {
    let url = "api/groupmember/all";
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                document.getElementById("table_id").innerHTML = "<table>" + insertIntoTableHeaders(data) + insertIntoTableFooters(data) + "</table>";
            });
}

function insertIntoTableHeaders(map) {
    let headers = Object.getOwnPropertyNames(map[0]);
    let result = "<tr><th>" + headers.join("</th><th>") + "</th><tr>";
    return result;
}
;

function insertIntoTableFooters(names) {
    let htmlRows = "<tr>";
    names.forEach(e => {
        let temp = Object.values(e).map(function (a) {
            if(a ==="Green") {
                return "<td style='background-color:Lime;'>" + a + "</td>";
            } else if(a==="Yellow") {
                return "<td style='background-color:Yellow;'>" + a + "</td>";
            } else if(a==="Red"){
                return "<td style='background-color:Red;'>" + a + "</td>";
            } else {
                return "<td>" + a + "</td>";
            }
            
        }).join("") + "</tr>";
        htmlRows += temp;
    });
    console.log(htmlRows);
    return htmlRows;
}
;

changeText();
document.getElementById("rldBtn").addEventListener("click", clicker);
//console.log(insertIntoTableHeaders(headersMap));
//document.getElementById("table_id").innerHTML = '<table>' + insertIntoTableHeaders(headersMap) + insertIntoTableFooters(cars) + "</table>";