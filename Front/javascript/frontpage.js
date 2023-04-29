const postContainer = document.getElementById("timeline-container");
let postsLoading = false;
let postOffset = 0;
renderPosts("timeline", loggedIn.id, postOffset);

window.addEventListener("scroll", (e) => {

    if (postsLoading) return;

    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight){
        postsLoading = true;
        renderPosts("timeline", loggedIn.id, postOffset);
    }
})