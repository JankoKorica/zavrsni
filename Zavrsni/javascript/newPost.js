const postContent = document.getElementById("post-content");
const postImage = document.getElementById("image-upload");
const postBtn = document.querySelector("#new-post button");
const displayedImage = document.querySelector("#new-post img");
const alertTxt = document.querySelector("#new-post p");

postImage.addEventListener("change" , (e) =>{
    displayedImage.src = URL.createObjectURL(postImage.files[0]);
})

postBtn.addEventListener("click", (e) => {
    if(postContent.value === "" && postImage.files.length === 0){
        alertTxt.innerText = "Please add image or text!"
        return;
    }
    let formData = new FormData();
    formData.append("image", postImage.files[0]);
    formData.append("post", new Blob([JSON.stringify({
        user: loggedIn,
        content: postContent.value
    })], {type: "application/json"}));
    fetch("http://localhost:8080/post/new", {
        method: "POST",
        body: formData
    }).then(res => {
        if (res.ok){
            location.href = "myProfile.html";
        }
    });

})