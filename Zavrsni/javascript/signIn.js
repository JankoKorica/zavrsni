const usernameIn = document.getElementById("username-in");
const passwordIn = document.getElementById("password-in");
const passwordIn2 = document.getElementById("password2-in");
const emailIn = document.getElementById("email-in");
const dobIn = document.getElementById("date-in");
const aboutIn = document.getElementById("about-in");
const imageIn = document.getElementById("profile-pic-in");
const submitBtn = document.getElementById("create");
const alertTxt = document.querySelector("p");
const userImg = document.getElementById("user-img");

imageIn.addEventListener("change", (e)=>{
    userImg.style.backgroundImage = `url('${URL.createObjectURL(imageIn.files[0])}')`;
})

submitBtn.addEventListener("click",(e) =>{
    if(usernameIn.value === "" || emailIn.value === "" || dobIn.value === "" || passwordIn.value === "" || passwordIn2 === ""){
        alertTxt.innerText = "Please fill in all required text fields!";
        return;
    }
    if (passwordIn.value !== passwordIn2.value){
        alertTxt.innerText = "Passwords not maching.";
        console.log("Isti nisu")
        return;
    }
    let formData = new FormData();
    formData.append("image", imageIn.files[0]);
    formData.append("user", new Blob([JSON.stringify({
        username: usernameIn.value,
        password: passwordIn.value,
        email: emailIn.value,
        dob: dobIn.value,
        about: aboutIn.value
        })],{type: "application/json"}));
    fetch("http://localhost:8080/user/new",{
        method: "post",
        body: formData
    }).then(res => {
        if (!res.ok){
            alertTxt.innerText = "Username is taken";
        }
        else{
            fetch("http://localhost:8080/user/logIn",{
                method: "post",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: usernameIn.value,
                    password: passwordIn.value
                })
            }).then(res => res.json())
                .then(data => {
                sessionStorage.setItem("loggedin", JSON.stringify(data));
                location.href = "index.html";
            })
        }
    })
});

