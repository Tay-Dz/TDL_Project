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

        let tables = document.querySelector("#TaskTables");
        createTables(tables, TaskListData);
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}
function createTables(tables, TaskListData) {
  for (element of TaskListData) {
    let tableDiv = document.createElement("div");
    let table = document.createElement("table");
    let Listname = document.createElement("h2");
    tablePriority = priority(element.priority);
    table.className = "table " + tablePriority;
    Listname.textContent = element.name;
    let data = Object.keys(element.tasks[0]);
    createTableHead(table, data);
    createTableBody(table, element.tasks);
    tableDiv.appendChild(Listname);
    tableDiv.appendChild(table);

    //Need to add footer for deleta and edit task list
    let ID =element.id;
    let Name = element.name;

    let tableFooter = document.createElement("footer");

    let myAddTaskButton = document.createElement("button");
    myAddTaskButton.className = "btn btn-outline-primary";
    myAddTaskButton.innerHTML = "Add Task";
    myAddTaskButton.setAttribute("data-toggle", "modal");
    myAddTaskButton.setAttribute("data-target", "#AddTaskModal");
    myAddTaskButton.onclick = function () {
      changeAddTaskModal(ID, Name);
    };

    let myEditButtonTL = document.createElement("button");
    myEditButtonTL.className = "btn btn-outline-primary";
    myEditButtonTL.innerHTML = "Edit";
    myEditButtonTL.setAttribute("data-toggle", "modal");
    myEditButtonTL.setAttribute("data-target", "#EditTLModal");
    myEditButtonTL.onclick = function () {
      changeEditTLModal(ID, Name);
    };

    let myDeleteButtonTL = document.createElement("button");
    myDeleteButtonTL.className = "btn btn-outline-primary";
    myDeleteButtonTL.innerHTML = "Delete";
    myDeleteButtonTL.onclick = function () {
      deleteTaskList(ID);
    };

    tableFooter.appendChild(myAddTaskButton);
    tableFooter.appendChild(myEditButtonTL);
    tableFooter.appendChild(myDeleteButtonTL);
    tableDiv.appendChild(tableFooter);

    tableDiv.className = "col-md";
    tables.appendChild(tableDiv);
    let seperator = document.createElement("div");
    seperator.className = "col-sm";
    tables.appendChild(seperator);
  }
}

function createTableHead(table, data) {
  let thead = table.createTHead();
  let row = thead.insertRow();
  for (let key of data) {
    if (key === "id") {
      continue;
    }
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
  th3.appendChild(text3);
  row.appendChild(th3);
}
function createTableBody(table, TaskListData) {
  let firstElement = true;
  for (let element of TaskListData) {
    if (firstElement) {
      firstElement = false;
      continue;
    }
    let row = table.insertRow();

    let cellName = row.insertCell();
    let textName = document.createTextNode(element.name);
    cellName.appendChild(textName);

    let cellPriority = row.insertCell();
    let textPriority = document.createTextNode(priority(element.priority));
    cellPriority.appendChild(textPriority);

    let newCell = row.insertCell();
    let newCell2 = row.insertCell();
    let myEditButton = document.createElement("button");
    myEditButton.className = "btn  ";
    myEditButton.setAttribute("data-toggle", "modal");
    myEditButton.setAttribute("data-target", "#EditTaskModal");

    let editIcon = document.createElement("span");
    editIcon.className = "material-icons";
    editIcon.innerHTML="create";
    myEditButton.appendChild(editIcon);


    let ID = element.id;
    let Name=element.name;
    myEditButton.onclick = function () {
      changeEditTaskModal(ID, Name);
    };

    let myDeleteButton = document.createElement("button");
    myDeleteButton.className = "btn";
    let deleteIcon = document.createElement("span");
    deleteIcon.className = "material-icons";
    deleteIcon.innerHTML="delete";
    myDeleteButton.appendChild(deleteIcon);
    myDeleteButton.onclick = function () {
      deleteTask(element.id);
    };

    newCell.appendChild(myEditButton);
    newCell2.appendChild(myDeleteButton);
  }
}

function priority(priority) {
  if (priority === 1) {
    return "Low";
  } else if (priority == 2) {
    return "Medium";
  } else {
    return "High";
  }
}

function deleteTask(id) {
  fetch("http://localhost:1998/task/delete/" + id, {
    method: "delete",
    headers: {
      "Content-type": "application/json",
    },
  })
    // .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });

  location.reload();
}

function deleteTaskList(id) {
  fetch("http://localhost:1998/taskList/delete/" + id, {
    method: "delete",
    headers: {
      "Content-type": "application/json",
    },
  })
    // .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });

  location.reload();
}

 let TLid;
 let TaskID;

function changeEditTLModal(id, name) {

  let modalPH = document.getElementById("EditTaskListName");
  modalPH.setAttribute("value", name);

  TLid = id;
  console.log(TLid);
}
function changeAddTaskModal(id, name) {

  let addTitle = document.querySelector("#addTitle");
  addTitle.innerHTML += name;

  TLid = id;
  console.log(TLid);
}
function changeEditTaskModal(id, name) {

  let modalPH = document.getElementById("EditTaskName");
  modalPH.setAttribute("value", name);

  TaskID = id;
  console.log(TLid);
}

document
  .querySelector("form.EditTaskList")
  .addEventListener("submit", function (stop) {
     stop.preventDefault();

    let formElements = document.querySelector("form.EditTaskList").elements;
    console.log(formElements);

    let EditTLname = formElements["EditTaskListName"].value;
    let EditTLpriority = priorityChange(formElements["EditTaskListPriority"].value);

    let TLID = parseInt(TLid);
    console.log(TLID);
    editTaskList(EditTLname, EditTLpriority, TLID);
  });
document
  .querySelector("form.AddTask")
  .addEventListener("submit", function (stop) {
     stop.preventDefault();

    let formElements = document.querySelector("form.AddTask").elements;
    console.log(formElements);

    let ADDname = formElements["AddTaskName"].value;
    let ADDpriority = priorityChange(formElements["AddTaskPriority"].value);

    let TLID = parseInt(TLid);
    console.log(TLID);
    addTask(ADDname, ADDpriority, TLID);
  });

  document
  .querySelector("form.EditTask")
  .addEventListener("submit", function (stop) {
     stop.preventDefault();

    let formElements = document.querySelector("form.EditTask").elements;
    console.log(formElements);

    let EDITname = formElements["EditTaskName"].value;
    let EDITpriority = priorityChange(formElements["EditTaskPriority"].value);

    let TID = parseInt(TaskID);
    console.log(TID);
    editTaskList(EDITname, EDITpriority, TID);
  });

function priorityChange(priority) {
  if (priority === "High") {
    return 3;
  } else if (priority === "Medium") {
    return 2;
  } else if (priority === "Low") {
    return 1;
  }
}

function editTaskList(name, priority, TLID) {
  fetch("http://localhost:1998/taskList/update/" + TLID, {
    method: "put",
    headers: {
      "Content-type": "application/json",
    },
    body: (json = JSON.stringify({
      "id": TLID,
      "name": name,
      "priority": priority
    })),
  })
    .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      location.reload();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}
function addTask(name, priority, TLID) {
  fetch("http://localhost:1998/task/create", {
    method: "post",
    headers: {
      "Content-type": "application/json",
    },
    body: (json = JSON.stringify({
      "name": name,
      "priority": priority,
      "taskList": {
        "id":TLID
      }

    })),
  })
    .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      location.reload();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}

function editTaskList(name, priority, TID) {
  fetch("http://localhost:1998/task/update/" + TID, {
    method: "put",
    headers: {
      "Content-type": "application/json",
    },
    body: (json = JSON.stringify({
      "id": TID,
      "name": name,
      "priority": priority
    })),
  })
    .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      location.reload();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}
