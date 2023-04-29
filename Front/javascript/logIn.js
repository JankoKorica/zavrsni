const usernameIn = document.getElementById("username-in");
const passwordIn = document.getElementById("password-in");
const submitBtn = document.querySelector("button");
const alertTxt = document.getElementById("alert-txt");
alertTxt.style.color = "red";

submitBtn.addEventListener("click", (e) =>{
   fetch("http://localhost:8080/user/logIn",{
       method: "post",
       headers: {
           "Content-Type": "application/json"
       },
       body: JSON.stringify({
           username: usernameIn.value,
           password: passwordIn.value
       })
   }).then(res =>{
       if (!res.ok){
           alertTxt.innerText = "Username or password inncorect.";
       }
       return res.json();
   }).then(data => {
       sessionStorage.setItem("loggedin", JSON.stringify(data));
       location.href = "index.html";
   })
});
