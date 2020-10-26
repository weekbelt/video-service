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
        // const commentModifyButton = document.querySelector("#commentModifyButton");
        // commentModifyButton.addEventListener("click", commentObj.modifyCommentRequest);
        $('#modifyCommentModal').on('show.bs.modal', function (e) {
            const commentId = $(e.relatedTarget).data('id');
            const commentModifyButton = document.querySelector("#commentModifyButton");
            commentModifyButton.addEventListener("click", () => {
                commentObj.modifyCommentRequest(commentId);
            })

        })

        // // 댓글 삭제 요청 이벤트 등록
        // const commentDeleteButton = document.querySelector("#replyDeleteButton");
        // commentDeleteButton.addEventListener("click", this.deleteCommentRequest);

    },
    createCommentRequest: async function () {
        // json 요청 데이터
        const commentText = document.querySelector("#newCommentText");
        const createCommentForm = {
            text: commentText.value,
        }

        if (!commentObj.isValid) {
            alert("글자수를 3자 이상 200자 이하로 입력해주세요.");
        } else {
            // ajax 요청 uri 구하기
            const videoId = document.querySelector(".videoId").id;
            const requestUri = "/api/videos/" + videoId + "/comments";
            const response = await ajax("POST", requestUri, createCommentForm);

            alert("등록 되었습니다.");

            // 등곡한 댓글 리스트 맨 위에 삽입
            const commentElement = commentObj.makeCommentTemplate(response);
            const commentListContainer = document.querySelector("#commentList");
            commentListContainer.insertAdjacentHTML("afterbegin", commentElement);

            // 성공적인 등록후 댓글 입력창 초기화 처리
            commentObj.isValid = false;
            commentText.value = "";
            if (commentText.classList.contains("is-valid")) {
                commentText.classList.remove("is-valid");
            }

        }
    },
    modifyCommentRequest: async function (commentId) {
        // json 요청 데이터
        const modifyCommentText = document.querySelector("#modifyCommentText");
        const modifyCommentForm = {
            text: modifyCommentText.value
        }

        if (!commentObj.isValid) {
            alert("글자수를 3자 이상 200자 이하로 입력해주세요.");
        } else {
            // ajax 요청 uri 구하기
            // const modifyButton = event.target;
            // const commentId = modifyButton.value;
            const videoId = document.querySelector(".videoId").id;
            const modifyRequestUri = "/api/videos/" + videoId + "/comments/" + commentId;
            const response = await ajax("PUT", modifyRequestUri, modifyCommentForm);

            alert("수정 되었습니다.");

            // 모달 댓글 입력창 초기화
            commentObj.isValid = false;
            modifyCommentText.value = "";
            if (modifyCommentText.classList.contains("is-valid")) {
                modifyCommentText.classList.remove("is-valid");
            }

            // 모달 끄기
            $('#modifyCommentModal').modal("hide");

            // 기존의 댓글 창 수정
            const modifyBeforeCommentTemplate = document.querySelector('[data-commentId="' + commentId + '"]');
            const modifiedCommentTemplate = $.parseHTML(commentObj.makeCommentTemplate(response))[1];

            const commentListContainer = document.querySelector("#commentList");
            commentListContainer.replaceChild(modifiedCommentTemplate, modifyBeforeCommentTemplate);
        }
    },
    makeCommentTemplate: function (commentReadForm) {
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

        return commentElement;
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