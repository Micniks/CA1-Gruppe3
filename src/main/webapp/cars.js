
function clickSearchID(evt) {
    evt.preventDefault();
    findCarOnID();
}
;

function clickSearchMake(evt) {
    evt.preventDefault();
    findCarOnMake();

}
;

function changeText() {
    let url = "api/car/all";
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                document.getElementById("table_id").innerHTML = "<table>" + insertIntoTableHeaders(data) + insertIntoTableFooters(data) + "</table>";
            });
}

function findCarOnID() {
    let searchId = document.getElementById("idTxt").value;
    let url = "api/car/id/" + parseInt(searchId);
    if (searchId.length > 0 && !Number.isNaN(parseInt(searchId))) {
        fetch(url)
                .then(res => res.json())
                .then(data => {
                    console.log("data", data);
                    if (data !== null) {
                        document.getElementById("table_id").innerHTML = "<table>" + insertIntoTableHeaders(data) + insertIntoTableFooters(data) + "</table>";
                        document.getElementById("result").innerHTML = "<p></p>";
                    } else {
                        console.log("error");
                        document.getElementById("result").innerHTML = "<p>No such search exist</p>";
                        changeText();
                    }

                });
    } else {
        changeText();
        document.getElementById("result").innerHTML = "<p>Please use a number</p>";
    }
}
document.getElementById("searchBtn").addEventListener("click", clickSearchID);

function findCarOnMake() {
    let searchMake = document.getElementById("idTxt").value;
    if (searchMake.length > 0) {
        let url = "api/car/make/" + searchMake;
        fetch(url)
                .then(res => res.json())
                .then(data => {
                    console.log("data", data);
                    if (Array.isArray(data) && data.length > 0) {
                        document.getElementById("table_id").innerHTML = "<table>" + insertIntoTableHeaders(data) + insertIntoTableFooters(data) + "</table>";
                        document.getElementById("result").innerHTML = "<p></p>";
                    } else {
                        changeText();
                        document.getElementById("result").innerHTML = "<p>No such search exist</p>";
                    }
                });
    } else {
        document.getElementById("result").innerHTML = "<p>Please put in a search for a maker</p>";
    }

}
document.getElementById("searchBtnMake").addEventListener("click", clickSearchMake);

function insertIntoTableHeaders(map) {
    let headers;
    if (Array.isArray(map)) {
        headers = Object.getOwnPropertyNames(map[0]);
    } else {
        headers = Object.getOwnPropertyNames(map);
    }

    let result = "<tr><th>" + headers.join("</th><th>") + "</th><tr>";
    console.log(result);
    return result;
}
;

function insertIntoTableFooters(cars) {
    let htmlRows = "<tr>";
    if (Array.isArray(cars)) {
        cars.forEach(e => {
            let temp = Object.values(e).map(function (a) {
                return "<td>" + a + "</td>";
            }).join("") + "</tr>";
            htmlRows += temp;
        });
    } else {
        let temp = Object.values(cars).map(function (a) {
            return "<td>" + a + "</td>";
        }).join("") + "</tr>";
        htmlRows += temp;
    }

    console.log(htmlRows);
    return htmlRows;
}
;

changeText();
document.getElementById("result").innerHTML = "<p></p>";