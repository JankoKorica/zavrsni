const logoutMenuBtn = document.getElementById("user-img-logout");
const logoutMenu = document.getElementById("logout-options");
const searchBar = document.getElementById("search");
const searchResults = document.getElementById("search-result-container");
const searchBtn = document.getElementById("search-btn");
const userImg = document.querySelector(".user-data .user-img");
const username = document.querySelector(".user-data h3");
const loggedIn = JSON.parse(sessionStorage.getItem("loggedin"));
const notificationAlert = document.getElementById("notification-alert");
logoutMenuBtn.addEventListener("click", (e) =>{
    logoutMenu.classList.toggle("logout-visible");
});
searchBar.addEventListener("focus", (e) =>{
    searchResults.style.display = "block";
});
document.addEventListener("click", (e) => {
    if(e.target === searchResults || e.target === searchBar || e.target === searchBtn) return;
    searchResults.style.display = "none";
});
if (searchBtn !== null){
    searchBtn.addEventListener("click", (e) =>{
        searchResults.style.display = "block";
        searchBar.focus();
    });
}


if (loggedIn === null)
    location.href = "logIn.html";

if (username !== null)
    username.innerText = loggedIn.username;

if (loggedIn.imageName !== null){
    fetch("http://localhost:8080/user/getImage/" + loggedIn.imageName)
        .then(res => {
            logoutMenuBtn.style.backgroundImage = "url('" + res.url + "')";
            if (userImg !== null)
                userImg.style.backgroundImage = "url('" + res.url + "')";
        })
}

function setVisited(user){
    sessionStorage.setItem("visited" , user);
    location.href = "userProfiles.html";
}

searchBar.addEventListener("input" , (e) =>{
    searchResults.innerHTML = "";
    if (searchBar.value === "")
        return;
    fetch("http://localhost:8080/user/search/" + searchBar.value)
        .then(res => res.json())
        .then(data => {
            for (let user of data){
                fetch("http://localhost:8080/user/getImage/" + user.imageName)
                    .then(res =>{
                        searchResults.innerHTML += `
                                <div class="search-item" onclick="setVisited(${user.id})">
                                    <div class="search-item-img" style='background-image: url("${res.url}")'></div>
                                    <h3>${user.username}</h3>
                                </div>
                                <div class="line"></div>`
                    })

            }
        })
})

fetch("http://localhost:8080/notification/get/" + loggedIn.id)
    .then(res => res.json())
    .then(notifications =>{
        for (let notification of notifications) {
            if (!notification.viewed)
                notificationAlert.style.backgroundColor = "red";
        }
    })

