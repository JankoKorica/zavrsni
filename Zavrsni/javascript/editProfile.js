const userImage = document.getElementById("user-img");
const profilePicIn = document.getElementById("profile-pic-in");
const usernameIn = document.getElementById("username-in");
const aboutIn = document.getElementById("about-in");
const emailIn = document.getElementById("email-in");
const dateIn = document.getElementById("date-in");
const editBtn = document.querySelector("form button");

fetch("http://localhost:8080/user/getImage/" + loggedIn.imageName)
    .then(res => userImage.style.backgroundImage = `url('${res.url}')`)

profilePicIn.addEventListener("change", (e)=>{
    userImage.style.backgroundImage = `url('${URL.createObjectURL(profilePicIn.files[0])}')`;
})

editBtn.addEventListener("click", (e) =>{
    let formData = new FormData();
    formData.append("image", profilePicIn.files[0]);
    formData.append("editedUser", new Blob([JSON.stringify({
        username: usernameIn.value,
        email: emailIn.value,
        dob: dateIn.value,
        about: aboutIn.value
    })],{type: "application/json"}));
    fetch("http://localhost:8080/user/update/" + loggedIn.id,{
        method: "PUT",
        body: formData
    }).then(res => res.json())
        .then(data => {
            sessionStorage.setItem("loggedin", JSON.stringify(data));
            location.href = "myProfile.html";
        })
})


