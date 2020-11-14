const commentLoadObj = {
    init: async function (pageNum, size) {
        let responseComments = "";
        const commentTemplate = document.querySelector("#commentTemplate").innerHTML;
        const page = document.querySelector("#main").dataset.page;

        if (page === 'videoDetail') {
            responseComments = await this.ajaxByVideoId(pageNum, size);

            // 댓글 리스트 호출
            await this.showCommentList(responseComments, commentTemplate);

            // 댓글 개수 호출
            this.showCommentNum(responseComments.totalElements);

        } else if (page === 'userDetail') {
            responseComments = await this.ajaxByName(pageNum, size);
            console.log(responseComments);

            // 댓글 리스트 호출
            await this.showCommentList(responseComments, commentTemplate);
        }

        // 페이지 리스트 호출
        commentPageLoadObj.showPagination(responseComments);
    },
    ajaxByVideoId: async function (pageNum, size) {
        const videoId = document.querySelector(".videoId").id;
        const requestUri = "/api/videos/" + videoId + "/comments?page=" + pageNum + "&size=" + size;
        return await fetchRequest("GET", requestUri);
    },
    ajaxByName: async function (pageNum, size) {
        const name = document.querySelector(".user-profile").dataset.name;
        const requestUri = "/api/members/" + name + "/comments?page=" + pageNum + "&size=" + size;
        return await fetchRequest("GET", requestUri);
    },
    showCommentList: async function (responseComments, commentTemplate) {
        const comments = responseComments.content;
        let resultCommentListHTML = "";

        comments.forEach(comment => {
            resultCommentListHTML += commentLoadObj.makeCommentTemplate(comment, commentTemplate);
        });

        const commentListUl = document.querySelector(".comments");
        commentListUl.innerHTML = resultCommentListHTML;
    },
    makeCommentTemplate: function (commentReadForm, commentTemplate) {
        // 최근 수정 시간을 ~전 으로 처리
        moment.locale('ko');
        commentReadForm.createdDateTime = moment(commentReadForm.createdDateTime, "YYYY-MM-DD`T`hh:mm").fromNow();
        commentReadForm.modifiedDateTime = moment(commentReadForm.modifiedDateTime, "YYYY-MM-DD`T`hh:mm").fromNow();

        // 내가 남긴 댓글인지 검증
        const currentMember = document.querySelector("#currentMemberName");
        if (currentMember) {
            if (commentReadForm.name === currentMember.value) {
                commentReadForm.isWriter = true;
            }
        }

        const bindTemplate = Handlebars.compile(commentTemplate);
        const commentElement = bindTemplate(commentReadForm);

        return commentElement;
    },
    showCommentNum: function (commentNum) {
        let commentNumStr = "" + commentNum;
        if (commentNum > 1) {
            commentNumStr += " comments";
        } else {
            commentNumStr += " comment";
        }
        document.querySelector("#commentNum").innerHTML = commentNumStr;
    }
};
