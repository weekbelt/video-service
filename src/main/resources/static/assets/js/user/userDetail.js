const userDetailObj = {
    init: function () {
        this.showProfileImage();
    },
    showProfileImage: function () {
        const imageTag = document.querySelector(".avatar");
        if (imageTag.tagName === 'IMG') {
            const name = document.querySelector(".user-profile").dataset.name;
            imageTag.src = "/api/members/" + name + "/profileImage";
        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    userDetailObj.init();
    commentLoadObj.init(0, commentObj.pagePerElementNum);
});
