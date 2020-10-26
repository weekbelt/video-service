const commentObj = {
    isValid: false,
    currentMemberName: document.querySelector("#currentMemberName").value,
    init: function () {
        // 댓글 생성 및 수정시 글자 수 유효한지 확인하는 이벤트 등록
        const commentText = document.querySelector("#newCommentText");
        commentText.addEventListener("keyup", this.isValidText);
        const modifyCommentText = document.querySelector("#modifyCommentText");
        modifyCommentText.addEventListener("keyup", this.isValidText);

        // 댓글 작성 요청 이벤트 등록
        const commentCreateButton = document.querySelector("#commentAddBtn");
        commentCreateButton.addEventListener("click", this.createCommentRequest);

        // 댓글 수정 요청 이벤트 등록
        const commentModifyButton = document.querySelector("#commentModifyButton");
        commentModifyButton.addEventListener("click", this.modifyCommentRequest);

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
            commentObj.addCommentTemplate(response);

            // 성공적인 등록후 댓글 입력창 초기화 처리
            commentObj.isValid = false;
            commentText.value = "";
            if (commentText.classList.contains("is-valid")) {
                commentText.classList.remove("is-valid");
            }

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
        const textArea = event.target;
        let text = textArea.value;
        commentObj.isValid = /^.{3,200}$/.test(text);
        if (commentObj.isValid) {
            if (textArea.classList.contains("is-invalid")) {
                textArea.classList.remove("is-invalid");
            }
            textArea.classList.add("is-valid");
        } else {
            if (textArea.classList.contains("is-valid")) {
                textArea.classList.remove("is-valid");
            }
            textArea.classList.add("is-invalid");
        }
    }
};

document.addEventListener("DOMContentLoaded", () => {
    commentObj.init();
});