const headline = document.querySelector("main h1");
const userCategory = sessionStorage.getItem("userlist");
const userListContainer = document.getElementById("user-container");
const followUserId = sessionStorage.getItem("userType") === "loggedin" ? loggedIn.id : sessionStorage.getItem("visited");
function renderUserList(user){
    fetch("http://localhost:8080/user/getImage/" + user.imageName)
        .then(res => {
            userListContainer.innerHTML += `
                                    <div href="userProfiles.html" class="user" onclick="setVisited(${user.id})">
                                        <div class="user-profile-img" style="background-image: url('${res.url}')"></div>
                                        <div class="user-content">
                                            <h3>${user.username}</h3>
                                        </div>
                                    </div>
                                    <div class="line"></div>`;
        })
}
console.log(followUserId)
fetch("http://localhost:8080/follow/get/" + followUserId)
.then(res => res.json())
.then(data => {
    headline.innerText = userCategory;
    switch (userCategory){
        case "followers":
            for (let follower of data.followers)
                renderUserList(follower.follower);
            break;
        case "followees":
            for (let followee of data.followees)
                renderUserList(followee.followee);
            break;
    }
})