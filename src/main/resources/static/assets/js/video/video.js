const videoObj = {
    keyword: "",
    lastPageNumber: 0,
    init: async function () {
        const searchingBy = document.querySelector("#keyword");
        if (searchingBy) {
            this.keyword = searchingBy.dataset.keyword;
        }

        const requestUri = "/api/videos?page=" + this.lastPageNumber + "&keyword=" + this.keyword;
        let videoPage = await fetchRequest("GET", requestUri);

        // 화면에 비디오 리스트 호출
        this.showVideos(videoPage);

        // 더보기 이벤트 등록
        const moreVideoButton = document.querySelector("#moreVideoButton");
        if (moreVideoButton) {
            moreVideoButton.addEventListener("click", () => {
                videoObj.lastPageNumber += 1;
                videoObj.init();
            });
        }
    },
    showVideos: function (videoPage) {
        const videoContent = videoPage.content;

        // 더보기 버튼 처리
        if (!videoPage.last) {
            const moreVideoButton = document.createElement("button");
            moreVideoButton.classList.add("btn");
            moreVideoButton.classList.add("get-started-btn");
            moreVideoButton.id = "moreVideoButton";
            moreVideoButton.textContent = "더보기";

            const mainContainer = document.querySelector("#main");
            mainContainer.appendChild(moreVideoButton);
        } else {
            const moreVideoButton = document.querySelector("#moreVideoButton");
            if (moreVideoButton) {
                moreVideoButton.remove();
            }
        }

        // handlbar 데이터 바인딩
        let resultHTML = "";
        videoContent.forEach(video => {
            const data = {};
            data.videoId = video.id;
            data.videoDetailUri = "/videos/" + video.id;
            data.thumbnailUri = "/api/videos/" + video.id + "/thumbnail";
            data.title = video.title;
            data.viewCount = "조회수 " + video.views + "회";
            moment.locale('ko');
            data.createdDateTime = moment(video.createdDateTime, "yyyy-MM-DD`T`hh:mm").fromNow();

            const videoContainerTemplate = document.querySelector("#videoElementTemplate").innerHTML;
            const bindTemplate = Handlebars.compile(videoContainerTemplate);
            const videoElementHTML = bindTemplate(data);

            resultHTML += videoElementHTML;
        })


        let videoContainer = document.querySelector(".home-videos");
        videoContainer.innerHTML += resultHTML;
    }

}

document.addEventListener("DOMContentLoaded", async () => {
    videoObj.init();
})