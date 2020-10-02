const params = new URLSearchParams(window.location.search);
for (const param of params) {
  console.log(param);
  let order = param[1];
  getOrder(order);
}


function getOrder(order) {
  fetch("http://localhost:1998/taskList/read/" + order)
    .then(function (response) {
      if (response.status !== 200) {
        console.log(
          "Looks like there was a problem. Status Code: " + response.status
        );
        return;
      }

      // Examine the text in the response
      response.json().then(function (TaskListData) {
        console.log(TaskListData);

        let tables = document.querySelector("#TaskTables");
        let data = Object.keys(TaskListData[0]);

        createTables(tables,TaskListData);

        // createTableHead(table, data);
        // createTableBody(table, TaskListData);
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });

    function createTables(tables,TaskListData){
        for(element of TaskListData){
            let tableDiv = document.createElement("div");
            let table = document.createElement("table");
            let Listname =document.createElement("h2");
            console.log(element.tasks);
            Listname.textContent=element.name;
            let data = Object.keys(element.tasks[0]);
            console.log(data);
            createTableHead(table, data);
            createTableBody(table, element.tasks);
            tableDiv.appendChild(Listname);
            tableDiv.appendChild(table);
            tables.appendChild(tableDiv);
        }
    }

  function createTableHead(table, data) {
    let thead = table.createTHead();
    let row = thead.insertRow();
    for (let key of data) {
      let th = document.createElement("th");
      let text = document.createTextNode(key);
      th.appendChild(text);
      row.appendChild(th);
    }
    // let th2 = document.createElement("th");
    // let text2 = document.createTextNode("View");
    // th2.appendChild(text2);
    // row.appendChild(th2);
  }
  function createTableBody(table, TaskListData) {
    for (let element of TaskListData) {
      let row = table.insertRow();
      for (key in element) {
        let cell = row.insertCell();
        let text = document.createTextNode(element[key]);
        cell.appendChild(text);
      }
    //   let newCell = row.insertCell();
    //   let myViewButton = document.createElement("a");
    //   myViewButton.className = "btn btn-warning";
    //   myViewButton.innerHTML = "View";
    //   myViewButton.href = "record.html?id=" + element.id;
    //   newCell.appendChild(myViewButton);
    }
  }
}
