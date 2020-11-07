const commentLoadObj = {
    init: async function (pageNum, size) {
        const responseComments = await this.getResponseData(pageNum, size);
        // 댓글 리스트 호출
        this.showCommentList(responseComments);
        // 댓글 개수 호출
        this.showCommentNum(responseComments.totalElements);
        // 페이지 리스트 호출
        commentPageLoadObj.showPagination(responseComments);
    },
    getResponseData: async function (pageNum, size) {
        const videoId = document.querySelector(".videoId").id;
        const requestUri = "/api/videos/" + videoId + "/comments?page=" + pageNum + "&size=" + size;
        return await fetchRequest("GET", requestUri);
    },
    showCommentList: async function (responseComments) {
        const comments = responseComments.content;
        let resultCommentListHTML = "";
        comments.forEach(comment => {
            resultCommentListHTML += commentObj.makeCommentTemplate(comment);
        });

        const commentListUl = document.querySelector("#commentList");
        commentListUl.innerHTML = resultCommentListHTML;
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

const commentPageLoadObj = {
    showPagination: function (commentResponse) {
        const pageable = commentResponse.pageable;

        // 해당 페이지에 속한 첫번째 PageNumber구하기
        let startNumber = Math.floor(commentResponse.number / pageable.pageSize) * pageable.pageSize + 1;

        // 마지막 PageNumber 구하기
        let endNumber = commentResponse.totalPages > startNumber + (pageable.pageSize - 1) ? startNumber + (pageable.pageSize - 1) : commentResponse.totalPages;

        let str = "";
        // 제일 첫 페이지로 가는 버튼 구하기
        str += "<li class='page-item'>\n" +
            "<a class='page-link' aria-label='Previous' data-id='0'>&laquo;</a>\n" +
            "</li>"

        // 이전 페이지로 가는 버튼 구하기
        if (commentResponse.first) {
            str += "<li class='page-item disabled'>\n" +
                "<a class='page-link' aria-label='Previous' data-id='" + (commentResponse.number - 1) + "'>&lsaquo;</a>\n" +
                "</li>"
        } else {
            str += "<li class='page-item'>\n" +
                "<a class='page-link' aria-label='Previous' data-id='" + (commentResponse.number - 1) + "'>&lsaquo;</a>\n" +
                "</li>"
        }

        // 페이지 번호 표시
        for (let pageNumber = startNumber; pageNumber <= endNumber; pageNumber++) {
            if ((pageNumber - 1) === commentResponse.number) {
                str += "<li class='page-item active'>\n" +
                    "<a class='page-link' data-id='" + (pageNumber - 1) + "'>" + pageNumber +"</a>\n" +
                    "</li>"
            } else {
                str += "<li class='page-item'>\n" +
                    "<a class='page-link' data-id='" + (pageNumber - 1) + "'>" + pageNumber + "</a>\n" +
                    "</li>"
            }
        }


        // 다음 페이지로 가는 버튼 구하기
        if (commentResponse.last) {
            str += "<li class='page-item disabled'>\n" +
                "<a class='page-link' aria-label='Previous' data-id='" + (commentResponse.number + 1) + "'>&rsaquo;</a>\n" +
                "</li>"
        } else {
            str += "<li class='page-item'>\n" +
                "<a class='page-link' aria-label='Previous' data-id='" + (commentResponse.number + 1) + "'>&rsaquo;</a>\n" +
                "</li>"
        }

        // 제일 마지막 페이지로 가는 버튼 구하기
        str += "<li class='page-item'>\n" +
            "<a class='page-link' data-id='" + (commentResponse.totalPages - 1) + "'>&raquo;</a>\n" +
            "</li>"

        let paginationContainer = document.querySelector(".pagination");
        paginationContainer.innerHTML = str;

        // 페이지 버튼 클릭 이벤트 등록
        const pageButton = document.querySelector(".pagination");
        pageButton.addEventListener("click", commentPageLoadObj.pageButtonClickEvent)
    },
    pageButtonClickEvent: function (event) {
        event.preventDefault();
        const requestPageNum = event.target.dataset.id;
        commentLoadObj.init(requestPageNum, commentObj.pagePerElementNum);
    }
};
