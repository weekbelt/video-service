const commentObj = {
    pagePerElementNum: 10,
    isValid: false,
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
        $('#modifyCommentModal').on('show.bs.modal', function (e) {
            const commentId = $(e.relatedTarget).data('id');
            const commentModifyButton = document.querySelector("#commentModifyButton");
            commentModifyButton.addEventListener("click", () => {
                commentObj.modifyCommentRequest(commentId);
            })
        })

        // // 댓글 삭제 요청 이벤트 등록
        $('#deleteCommentModal').on('show.bs.modal', function (e) {
            const commentId = $(e.relatedTarget).data('id');
            const commentDeleteButton = document.querySelector("#commentDeleteButton");
            commentDeleteButton.addEventListener("click", () => {
                commentObj.deleteCommentRequest(commentId);
            })
        })
    },
    createCommentRequest: async function () {
        // json 요청 데이터
        const commentText = document.querySelector("#newCommentText");
        const createCommentForm = {
            text: commentText.value,
        }

        const currentMember = document.querySelector("#currentMemberName")
        if (currentMember) {
            if (!commentObj.isValid) {
                alert("글자수를 3자 이상 200자 이하로 입력해주세요.");
            } else {
                // ajax 요청 uri 구하기
                const videoId = document.querySelector(".videoId").id;
                const requestUri = "/api/videos/" + videoId + "/comments";
                const response = await fetchRequest("POST", requestUri, createCommentForm);

                alert("등록 되었습니다.");

                // comments AJAX 재요청으로 앞 10개 댓글만 보이도록
                await commentLoadObj.init(0, commentObj.pagePerElementNum);

                // 성공적인 등록후 댓글 입력창 초기화 처리
                commentObj.isValid = false;
                commentText.value = "";
                if (commentText.classList.contains("is-valid")) {
                    commentText.classList.remove("is-valid");
                }

            }
        } else {
            alert("댓글을 입력할 권한이 없습니다. 로그인 페이지로 이동합니다.");
            window.location.href = "/login";
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
            const videoId = document.querySelector(".videoId").id;
            const modifyRequestUri = "/api/videos/" + videoId + "/comments/" + commentId;
            const response = await fetchRequest("PUT", modifyRequestUri, modifyCommentForm);

            alert("수정 되었습니다.");

            // 모달 댓글 입력창 초기화
            commentObj.isValid = false;
            modifyCommentText.value = "";
            if (modifyCommentText.classList.contains("is-valid")) {
                modifyCommentText.classList.remove("is-valid");
            }

            // 모달 끄기
            $('#modifyCommentModal').modal("hide");

            // 수정 후 comment 리스트 AJAX 요청 초기화
            commentLoadObj.init(0, commentObj.pagePerElementNum);
        }
    },
    deleteCommentRequest: async function (commentId) {
        const videoId = document.querySelector(".videoId").id;
        const deleteRequestUri = "/api/videos/" + videoId + "/comments/" + commentId;
        const response = await fetchRequest("DELETE", deleteRequestUri);

        alert("성공적으로 삭제하였습니다.")

        $('#deleteCommentModal').modal('hide');

        // 삭제 후 comment 리스트 AJAX 요청 초기화
        commentLoadObj.init(0, commentObj.pagePerElementNum);
    },
    isValidText: function (event) {
        const textArea = event.target;
        let text = textArea.value;
        let resultText = text.replace(/(?:\r\n|\r|\n)/g, '<br>');
        commentObj.isValid = /^.{3,200}?$/.test(resultText);
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

// document.addEventListener("DOMContentLoaded", () => {
//     commentObj.init();
//     commentLoadObj.init(0, commentObj.pagePerElementNum);
// });