document
  .querySelector("form.TaskList")
  .addEventListener("submit", function (stop) {
    stop.preventDefault();

    let formElements = document.querySelector("form.TaskList").elements;
    console.log(formElements);

    let name = formElements["TaskListName"].value;
    let priority = priorityChange(formElements["TaskListPriority"].value);

    addTaskList(name, priority);

    
    
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

function addTaskList(name, priority) {
  fetch("http://localhost:1998/taskList/create", {
    method: "post",
    headers: {
      "Content-type": "application/json",
    },
    body: (json = JSON.stringify({
      "name": name,
      "priority": priority
    })),
  })
    .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      addBlankTask();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}

function addBlankTask(){
    fetch("http://localhost:1998/taskList/readLast")
    .then(function (response) {
      if (response.status !== 200) {
        console.log(
          "Looks like there was a problem. Status Code: " + response.status
        );
        return;
      }

      // Examine the text in the response
      response.json().then(function (ID) {
        console.log(ID);
        console.log("hello")

        addBlankTaskId(ID);
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
    
}

function addBlankTaskId(ID) {
    
  fetch("http://localhost:1998/task/create", {
    method: "post",
    headers: {
      "Content-type": "application/json",
    },
    body: (json = JSON.stringify({
      "name": "null",
      "priority": 0,
      "taskList": {
        "id":ID
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
