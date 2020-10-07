let addTL = document.querySelector("#TaskListAdd");
AddTLBtn(addTL);


function AddTLBtn(addTL){
    let addBtn = document.createElement("button");
    addBtn.className = "btn btn-warning";
    addBtn.onclick = function(){btnMethod();}
    addBtn.setAttribute("data-toggle","modal");
    addBtn.setAttribute("data-target","addModal");
    
    let addModal = document.createElement("div");
    addModal.className = "modal fade";
    addModal.setAttribute("tabindex","-1");
    addModal.setAttribute("aria-labelledby","-addModalLabel");
    addModal.setAttribute("aria-hidden","true");
    addModal.setAttribute("id","addModal");
    
    let addModalDialog = document.createElement("div");
    addModalDialog.className = "modal-dialog";

    let addModalContent = document.createElement("div");
    addModalContent.className = "modal-content";
    
    let addModalHeader = document.createElement("div");
    addModalHeader.className = "modal-header";
    
    let addModalBody = document.createElement("div");
    addModalBody.className = "modal-body";
    
    let addModalFooter = document.createElement("div");
    addModalFooter.className = "modal-footer";
    
    //addTL.appendchild(addBtn);
 
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