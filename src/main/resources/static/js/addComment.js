const commentObj = {
    isValid: false,
    currentMemberName: document.querySelector("#currentMemberName").value,
    init: function () {
        // 댓글 글자 수 유효한지 확인하는 이벤트 등록
        const commentText = document.querySelector("#newCommentText");
        commentText.addEventListener("keyup", this.isValidText);
        // 댓글 작성 요청 이벤트 등록
        const commentCreateButton = document.querySelector("#commentAddBtn");
        commentCreateButton.addEventListener("click", this.createCommentRequest);
        // 댓글 수정 요청 이벤트 등록
        const commentModifyButton = document.querySelector("#commentModifyButton");
        commentModifyButton.addEventListener("click", this.modfiyCommentRequest);
        // 댓글 삭제 요청 이벤트 등록
        const commentDeleteButton = document.querySelector("#replyDeleteButton");
        commentDeleteButton.addEventListener("click", this.deleteCommentRequest);

    },
    createCommentRequest: async function () {
        const commentText = document.querySelector("#newCommentText");
        const createCommentForm = {
            text: commentText.value,
        }

        if (!commentObj.isValid) {
            alert("글자수를 3자 이상 200자 이하로 입력해주세요.");
        } else {
            const videoId = document.querySelector(".videoId").id;
            const requestUri = "/api/videos/" + videoId + "/comments";
            const response = await ajax("POST", requestUri, createCommentForm);

            alert("등록 되었습니다.");

            commentObj.isValid = false;
            commentText.value = "";
            commentObj.addCommentTemplate(response);
        }
    },
    modifyCommentRequest: function() {

    },
    addCommentTemplate: function (commentReadForm) {
        // 최근 수정 시간을 ~전 으로 처리
        moment.locale('ko');
        commentReadForm.createdDateTime = moment(commentReadForm.createdDateTime, "YYYY-MM-DD`T`hh:mm").fromNow();
        commentReadForm.modifiedDateTime = moment(commentReadForm.modifiedDateTime, "YYYY-MM-DD`T`hh:mm").fromNow();

        if (commentReadForm.name === commentObj.currentMemberName) {
            commentReadForm.isWriter = true;
        }
        const commentElementTemplate = document.querySelector("#template").innerHTML;
        const bindTemplate = Handlebars.compile(commentElementTemplate);
        const commentElement = bindTemplate(commentReadForm);

        const commentListContainer = document.querySelector("#commentList");
        commentListContainer.insertAdjacentHTML("afterbegin", commentElement);
    },
    isValidText: function (event) {
        let text = event.target.value;
        commentObj.isValid = (/^.{3,1000}$/).test(text);
        
        console.log(commentObj.isValid);
    }
};

document.addEventListener("DOMContentLoaded", () => {
    commentObj.init();
});