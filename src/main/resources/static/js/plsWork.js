document
  .querySelector("form.EditTaskList")
  .addEventListener("submitForm", function (stop) {
     stop.preventDefault();

    let formElements = document.querySelector("form.EditTaskList").elements;
    console.log(formElements);

    let EDITname = formElements["EditTaskListName"].value;
    let EDITpriority = priorityChange(formElements["EditTaskListPriority"].value);

    let TLID = parseInt(TLid);
    console.log(TLID);
    addTaskList(EDITname, EDITpriority, TLID);
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

function addTaskList(name, priority, TLID) {
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
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}