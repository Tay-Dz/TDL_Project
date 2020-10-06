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
  if(key==="id"){continue;}
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
    let cellName = row.insertCell();
    let textName = document.createTextNode(element.name);
    cellName.appendChild(textName);
    let cellPriority = row.insertCell();
    let textPriority = document.createTextNode(priority(element.priority));
    cellPriority.appendChild(textPriority);
  
  let newCell = row.insertCell();
  let myEditButton = document.createElement("a");
  myEditButton.className = "btn btn-outline-primary";
 
  let myDeleteButton = document.createElement("a");
  myDeleteButton.className = "btn btn-outline-primary";
  myDeleteButton.onclick = function(){deleteTask(element.id);};
  
  newCell.appendChild(myEditButton);
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

function deleteTask(id){
  fetch("http://localhost:1998/task/delete/"+id, {
      method: 'delete',
      headers: {
        "Content-type": "application/json"
      }
    })
    // .then(json)
    .then(function (data) {
      console.log('Request succeeded with JSON response', data);
    })
    .catch(function (error) {
      console.log('Request failed', error);
    });

     location.reload();
}
