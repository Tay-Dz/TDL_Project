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

    
}
function createTables(tables,TaskListData){
    for(element of TaskListData){
        let tableDiv = document.createElement("div");
        let table = document.createElement("table");
        let Listname =document.createElement("h2");
        tablePriority=priority(element.priority);
        table.className = "table "+tablePriority;
        console.log(element.tasks);
        Listname.textContent=element.name;
        let data = Object.keys(element.tasks[0]);
        console.log(data);
        createTableHead(table, data);
        createTableBody(table, element.tasks);
        tableDiv.appendChild(Listname);
        tableDiv.appendChild(table);
        tableDiv.className = "col-3";
        tables.appendChild(tableDiv);
        let seperator = document.createElement("div");
        seperator.className = "col-1";
        tables.appendChild(seperator);
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
let th2 = document.createElement("th");
let text2 = document.createTextNode("Edit");
th2.appendChild(text2);
row.appendChild(th2);
let th3 = document.createElement("th");
let text3 = document.createTextNode("Delete");
th2.appendChild(text3);
row.appendChild(th3);
}
function createTableBody(table, TaskListData) {
for (let element of TaskListData) {
  let row = table.insertRow();
  for (key in element) {
    let cell = row.insertCell();
    let text = document.createTextNode(element[key]);
    cell.appendChild(text);
  }
  let newCell = row.insertCell();
  let myDeleteButton = document.createElement("a");
  myDeleteButton.className = "btn btn-outline-primary";
  
//   let myDeleteIcon = document.createElement("svg");
//   myDeleteIcon.setAttribute("height","1em");
//   myDeleteIcon.setAttribute("width","1em");
//   myDeleteIcon.setAttribute("viewBox","0 0 16 16");
//   //myDeleteIcon.setAttribute("fill","currentColor" );
//   myDeleteIcon.setAttribute("xmlns","http://www.w3.org/2000/svg");
//   myDeleteIcon.className = "bi bi-trash";

//   let iconPath1 = document.createElement("path")
//   iconPath1.setAttribute("d","M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z");
//   let iconPath2 = document.createElement("path")
//   iconPath2.setAttribute("fill-rule","evenodd");
//   iconPath2.setAttribute("d","M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z");
//   myDeleteIcon.appendChild(iconPath1);
//   myDeleteIcon.appendChild(iconPath2);
//   myDeleteButton.appendChild(myDeleteIcon);
//   myViewButton.href = "record.html?id=" + element.id;
  newCell.appendChild(myDeleteButton);
}
}

function priority(priority){
    if(priority ===1){
        return "low";
    }else if(priority ==2){
        return "medium";
    }else{
        return "high";
    }
}
