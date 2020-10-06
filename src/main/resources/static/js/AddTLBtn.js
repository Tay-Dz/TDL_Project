let addTL = document.querySelector("#TaskListAdd");
AddTLBtn(addTL);


function AddTLBtn(addTL){
    let addBtn = document.createElement("button");
    addBtn.className = "btn btn-warning";
    addBtn.onclick = function(){btnMethod();}

    addTL.appendchild(addBtn);
 
}
function btnMethod(){


}


function addTaskList(name,priority){
    fetch("http://localhost:1998/taskList/create", {
        method: 'post',
        headers: {
          "Content-type": "application/json"
        },
        body: json = JSON.stringify({
            "name": name,
            "priority": priority
        })
      })
      .then(json)
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
  }