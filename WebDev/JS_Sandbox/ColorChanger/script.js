let picker = document.getElementById('color_picker');
let list = document.getElementById('color_list');
let body = document.getElementById('body');

picker.oninput = function(){
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
