const picker = document.getElementById('color_picker');
const list = document.getElementById('color_list');
const body = document.getElementById('body');

function colorPick(){
    let li = document.createElement("LI");
    let color = document.createTextNode(picker.value);
    li.appendChild(color);
    let deleteButton = document.createElement("button");
    deleteButton.innerHTML = '<img src="img/delete_icon.png"/>';
    deleteButton.setAttribute("class","delete_button"); 
    li.appendChild(deleteButton);
    deleteButton.onclick = function(event){
        list.removeChild(li);
        event.stopPropagation();
    };
    li.style.background = picker.value;
    li.onclick = function() {
        document.body.style.backgroundColor = this.style.background;
    };
    list.appendChild(li);
}

picker.addEventListener("input",colorPick);
