function postComments(commentBtn){

    fetch("http://localhost:8080/comment/new",{
        method: "POST",
        headers:{
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            post: {id: commentBtn.parentElement.parentElement.parentElement.parentElement.id},
            user: loggedIn,
            content: commentBtn.parentElement.previousElementSibling.value
        })
    }).then(res => {
        showComments(commentBtn.parentElement.parentElement.parentElement.previousElementSibling.firstElementChild.firstElementChild.nextElementSibling, true);
    })
}

function showComments(container, afterPost){
    let commentContainer = container.parentElement.parentElement.nextElementSibling.firstElementChild.nextElementSibling;
    if (!afterPost)
        container.parentElement.parentElement.nextElementSibling.classList.toggle("comment-container-visible");

    if (commentContainer.innerHTML !== "" && !afterPost)
        return;

    commentContainer.innerHTML = "";

    fetch("http://localhost:8080/comment/getAllByPost/" + container.parentElement.parentElement.parentElement.id)
        .then(res => res.json())
        .then(comments => {
            if (comments.length === 0)
                commentContainer.innerHTML = "No comments";
            for (let comment of comments){
                fetch("http://localhost:8080/user/getImage/" + comment.user.imageName)
                    .then(res =>{
                        commentContainer.innerHTML += renderCommentTemplate(comment, res.url);
                    })
            }
        })

}

function addLike(postId){

    fetch("http://localhost:8080/like/new", {
        method : "POST",
        headers :{
            "Content-Type" : "application/json"
        },
        body : JSON.stringify({
            post : {id : postId},
            user : loggedIn
        })
    })

}

function removeLike(postId){
    fetch(`http://localhost:8080/like/delete?postid=${postId}&userid=${loggedIn.id}`,{method : "DELETE"})
}

function like(likeBtn){
    let postId = likeBtn.parentElement.parentElement.parentElement.id;

    switch (likeBtn.className) {
        case "is-not-liked":
            addLike(postId);
            likeBtn.parentElement.nextElementSibling.firstElementChild.innerHTML = parseInt(likeBtn.parentElement.nextElementSibling.firstElementChild.innerHTML) + 1;
            likeBtn.className = "is-liked";
            likeBtn.innerHTML = "Liked";
            break;
        case "is-liked":
            removeLike(postId);
            likeBtn.parentElement.nextElementSibling.firstElementChild.innerHTML -= 1;
            likeBtn.className = "is-not-liked";
            likeBtn.innerHTML = "Like&nbsp";
            break;
        default:
            console.error("Error when adding or removing a like");
    }
}

function isLiked(likes){
    for (let like of likes){
        if (like.user.id === loggedIn.id)
            return true;
    }
    return false;
}

function renderPostTemplate(post, userImg, imagePath){
    return `
            <article class="post" id="${post.id}">
                    <div class="post-poster-data">
                        <div class="post-poster-img" style="${userImg === null ? `` : `background-image: url('${userImg}')`}"></div>
                        <h1 class="post-poster-username" style="cursor: pointer" onclick="setVisited(${post.user.id})">${post.user.username}</h1>
                    </div>
                    <div class="post-content">${post.content}</div>
                    ${imagePath === null ? `` : `<img class="post-image" src="${imagePath}">`}
                    <div class="post-options">
                        <div class="post-options-container">
                            <div class="${isLiked(post.likes) ? `is-liked` : `is-not-liked`}" onclick="like(this)">${isLiked(post.likes) ? `Liked` : `Like&nbsp`}</div>
                            <div class="add-comment-btn" onclick="showComments(this)">Comment</div>
                        </div>
                        <div>Liked:<span>${post.likes.length}</span></div>
                    </div>
                    <div class="comment-container">
                        <form class="add-comment-form">
                            <textarea class="comment-textarea" rows="3" placeholder="Add comment"></textarea>
                            <div class="post-comment-btn-container">
                                <button class="post-comment-btn" type="button" onclick="postComments(this, false)">Post</button>
                            </div>
                        </form>
                        <div class="comments"></div>
                    </div>
                </article>`
}

function renderCommentTemplate(comment, imgSrc){
    return `<div class="comment">
                <div class="comment-poster-data">
                    <div class="comment-poster-image" style="background-image: url(${imgSrc})"></div>
                    <h4>${comment.user.username}</h4>
                </div>
                <div class="comment-content">${comment.content}</div>
            </div>
            <div class="line"></div>`
}

function checkPostUser(post, postImage){
    fetch("http://localhost:8080/user/getImage/" + post.user.imageName)
        .then(res => postContainer.innerHTML += renderPostTemplate(post, res.url ,postImage));
}

function renderPosts(what, loggedinId, offset) {
    fetch(`http://localhost:8080/post/getby/${what}?id=${loggedinId}&offset=${offset}`)
        .then(res => res.json())
        .then(posts => {
            for (let post of posts){
                fetch("http://localhost:8080/post/getImage/" + post.imageName)
                    .then(res => {
                        if (res.status === 206){
                            checkPostUser(post, null);
                            return;
                        }
                        checkPostUser(post, res.url);
                    })
            }
            postsLoading = false;
            postOffset += 10;
        })
}
