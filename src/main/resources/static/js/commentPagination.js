const commentLoadObj = {
    init: async function (pageNum, size) {
        const responseComments = await this.getResponseData(pageNum, size);
        // 댓글 리스트 호출
        this.showCommentList(responseComments);
        // 댓글 개수 호출
        this.showCommentNum(responseComments.totalElements);
        // 페이지 리스트 호출
        commentPageLoadObj.showPagination(responseComments);
        // 페이지 버튼 클릭 이벤트 등록
        const pageButton = document.querySelector(".pagination");
        pageButton.addEventListener("click", commentPageLoadObj.pageButtonClickEvent)
    },
    getResponseData: async function (pageNum, size) {
        const videoId = document.querySelector(".videoId").id;
        const requestUri = "/api/videos/" + videoId + "/comments?page=" + pageNum + "&size=" + size;
        return await ajax("GET", requestUri);
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
        // 제일 마지막 페이지 구하기
        let endPage = (Math.ceil(commentResponse.number / pageable.pageSize) * pageable.pageSize);
        let tempEndPage = commentResponse.totalPages;
        if (endPage === 0 || endPage > tempEndPage) {
            endPage = tempEndPage;
        }

        // 첫 페이지 구하기
        let startPage = (endPage - pageable.pageSize) + 1;
        if (startPage < 0) {
            startPage = 1;
        }
        let prev = startPage !== 1;
        let next = endPage * commentResponse.size < commentResponse.totalElements;

        let str = "";
        if (prev) {
            str +=
                "<li class='page-item'>\n" +
                "    <a class='page-link' aria-label='Previous' href='" + (startPage - 1) + "'> << </a>\n" +
                "</li>"
        }

        for (let i = startPage, len = endPage; i <= len; i++) {
            const isActive = commentResponse.number === (i - 1) ? 'active' : '';
            str +=
                "<li class='page-item " + isActive + "'>\n" +
                "   <a class='page-link' href='" + (i - 1) + "'>" + i + "</a>\n" +
                "</li>"
        }

        if (next) {
            str +=
                "<li class='page-item'>\n" +
                "   <a class='page-link' aria-label='Next' href='" + (endPage + 1) + "'> >> </a>\n" +
                "</li>"
        }

        let paginationContainer = document.querySelector(".pagination");
        paginationContainer.innerHTML = str;
    },
    pageButtonClickEvent: function (event) {
        event.preventDefault();
        const requestPageNum = event.target.text;
        commentLoadObj.init(requestPageNum, commentObj.pagePerElementNum);
    }
};
//
// document.addEventListener("DOMContentLoaded", () => {
//     commentLoadObj.init(10, commentObj.pagePerElementNum);
// })