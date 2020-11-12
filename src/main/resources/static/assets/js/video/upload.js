const videoUploadObj = {
    init: function () {
        const avatarInput = document.querySelector("#thumbnailImageFile");
        avatarInput.addEventListener("change", this.showThumbnail);
    },
    showThumbnail: function(evt) {
        const profileImage = evt.target.files[0];
        if (!videoUploadObj.validImageType(profileImage)) {
            console.warn("invalid image file type");
        }

        const thumbImage = document.querySelector(".thumb_image");
        thumbImage.src = window.URL.createObjectURL(profileImage);
    },
    validImageType: function (profileImage) {
        return (['image/png', 'image/jpg', 'image/jpeg'].indexOf(profileImage.type) > -1);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    videoUploadObj.init();
});
