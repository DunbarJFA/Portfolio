const button = document.getElementById("btn");
const input = document.getElementById('input');

button.onclick = function(){
    if (input.value.trim() == ""){
        alert("Please tell us what to call you!");
    }
    else{
        alert(`Hello, ${input.value.trim()}!`);
        console.log(input.value.trim());
    }
};