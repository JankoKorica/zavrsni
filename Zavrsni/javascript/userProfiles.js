const usernameHeader = document.querySelector(".profile-data-edit h1");
const about = document.getElementById("about");
const profilePic = document.getElementById("profile-pic");
const followers = document.getElementById("follower-count");
const followees = document.getElementById("followee-count");
const visited = JSON.parse(sessionStorage.getItem("visited"));
const follow = document.querySelector(".profile-data-edit button");
const postContainer = document.getElementById("user-posts");
let postOffset = 0;
let postsLoading = false;

if (visited === loggedIn.id)
    window.location.href = "myProfile.html";

fetch("http://localhost:8080/user/get/" + visited)
    .then(res => res.json())
    .then(data =>{
        usernameHeader.innerText = data.username;
        about.innerText = data.about;
        if (data.imageName !== null){
            fetch("http://localhost:8080/user/getImage/" + data.imageName)
                .then(res => profilePic.style.backgroundImage = "url('" + res.url + "')");
        }
    })

fetch(`http://localhost:8080/follow/isfollowing?followerid=${loggedIn.id}&followeeid=${visited}`)
    .then(res => res.json())
    .then(data => {
        if (!data)
            return;
        follow.className = "unfollow-btn";
        follow.innerHTML = "Unfollow";
    })

follow.addEventListener("click", (e) =>{
    if (follow.className === "unfollow-btn"){
        fetch(`http://localhost:8080/follow/delete?followerid=${loggedIn.id}&followeeid=${visited}`, {method: "DELETE"});
        follow.innerHTML = "Follow";
        follow.className = "follow-btn";
    }
    else {
        fetch("http://localhost:8080/follow/new", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                follower: loggedIn,
                followee: {id: visited}
            })
        })
        follow.innerHTML = "Unfollow";
        follow.className = "unfollow-btn";
    }
})

fetch("http://localhost:8080/follow/get/" + visited)
    .then(res => res.json())
    .then(data =>{
        followers.innerText = data.followers.length;
        followees.innerText = data.followees.length;
    })

renderPosts("user", visited, postOffset);

window.addEventListener("scroll", (e) => {

    if (postsLoading) return;

    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight){
        postsLoading = true;
        renderPosts("user", visited, postOffset);
    }
})
