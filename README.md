# 동영상 서비스

## 소개
동영상 감상 및 업로드를 제공하는 서비스 입니다. 

<hr>

## 주요 기술
- HTML, CSS, JavaScript
- Java
- SpringBoot
- Spring Data JPA
- Spring Security
- QueryDsl

<hr>

## 배포 환경
- Travis CI를 Github에 연동하여 master브랜치로 push할 경우 자동으로 빌드하도록 설정
- 빌드 후 생성된 .jar 파일을 AWS S3에 저장
- S3에 있는 .jar 파일을 CodeDeploy에 배포 요청
- EC2에 배포


## 주요 기능

### 01. 메인 페이지
<img width="1664" alt="스크린샷 2020-11-15 오후 7 16 37" src="https://user-images.githubusercontent.com/26062056/99182347-dbf83380-2777-11eb-8b4a-e3ed90134fc3.png">

동영상을 12개씩 호출하는 API를 구현하였습니다. 업로드한 시간 역순으로 12개까지만 보여주고 그 이상 동영상이 존재할 경우 더보기 버튼을 클릭하여 AJAX요청으로 12개를 불러올 수 있게 하였습니다.

<br>

### 02. 동영상 업로드
<img width="1660" alt="스크린샷 2020-11-15 오후 7 26 49" src="https://user-images.githubusercontent.com/26062056/99182503-a6a01580-2778-11eb-88d1-d230782d4c59.png">
MIME 타입이 video/webm, video/mp4인 동영상을 업로드 할 수 있고 노트북의 카메라로 직접 동영상 녹화를 하여 업로드 할 수 있게 하였습니다.

<br>

### 03. 동영상 시청
<img width="972" alt="스크린샷 2020-11-15 오후 7 25 53" src="https://user-images.githubusercontent.com/26062056/99182528-c3d4e400-2778-11eb-9e24-46dc45a94576.png">
동영상 시청시 재생버튼, 사운드바, 전체 화면을 Javascript로 구현하여 제어할 수 있게 했습니다.

<br>

### 04. 로그인시 Profile
<img width="1671" alt="스크린샷 2020-11-15 오후 7 27 22" src="https://user-images.githubusercontent.com/26062056/99182533-c9322e80-2778-11eb-9fb0-1cc0fac133c5.png">
로그인한 유저의 상세정보창으로 로그인한 유저가 업로드한 동영상 목록과 직접 작성한 댓글 목록을 왼쪽 탭을 누를 때마다 볼 수 있도록 하였습니다.

<br>

### 05. 통합 테스트
핸들러 별로 MockMvc를 사용하여 통합테스트를 구현하였습니다. 

```java
    // 테스트 코드 일부
    @ParameterizedTest(name = "{index} {displayName} name={0}")
    @WithMember("joohyuk")
    @DisplayName("회원 이름 변경 - 실패")
    @ValueSource(strings = {"joohyuk", "@#$%@#$", "", "asdfasdfasdfhawkerjfghaskdfh"})
    void changeNameView_fail(String name) throws Exception {
        // given
        String requestUrl = "/members/change-name";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("name", name)
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("users/changeName"));
    }

```
