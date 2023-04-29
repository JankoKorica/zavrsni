const notificationContainer = document.getElementById("notifications-container");

fetch("http://localhost:8080/notification/get/" + loggedIn.id)
.then(res => res.json())
.then(notifications =>{
    notifications.reverse();
    for (let notification of notifications){
        let time = notification.time.split("T");
        notificationContainer.innerHTML += `
        <div class="notification">
            <div class="notification-container">
                <div class="notification-tag" style="background-color: ${notification.viewed ? `#434343` : `red`}"></div>
                <div class="notification-content">${notification.content}</div>
            </div>
            <div class="notification-time">${time[0] + " " + time[1].trim(-3)}</div>
        </div>
        <div class="line"></div>`
    }

})

fetch("http://localhost:8080/notification/viewed/" + loggedIn.id,{method: "PUT"});