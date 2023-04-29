const usernameHeader = document.querySelector(".profile-data-edit h1");
const about = document.getElementById("about");
const profilePic = document.getElementById("profile-pic");
const followers = document.getElementById("follower-count");
const followees = document.getElementById("followee-count");
const postContainer = document.getElementById("user-posts");
let postOffset = 0;
let postsLoading = false;

usernameHeader.innerText = loggedIn.username;
about.innerText = loggedIn.about;

if (loggedIn.imageName !== null){
    fetch("http://localhost:8080/user/getImage/" + loggedIn.imageName)
        .then(res => profilePic.style.backgroundImage = `url('${res.url}')`);
}

fetch("http://localhost:8080/follow/get/" + loggedIn.id)
    .then(res => res.json())
    .then(data =>{
        followers.innerText = data.followers.length;
        followees.innerText = data.followees.length;
    })

renderPosts("user", loggedIn.id, postOffset);

window.addEventListener("scroll", (e) => {

    if (postsLoading) return;

    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight){
        postsLoading = true;
        renderPosts("user", loggedIn.id, postOffset);
    }
})


