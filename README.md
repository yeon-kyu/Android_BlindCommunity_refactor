# BlindCommunity2

</br>

<p align="center">  
2020년에 만들었던 <a href="https://github.com/yeon-kyu/Android_BlindCommunity" target="_blank"> BlindCommunity </a> 를 리펙토링한 프로젝트입니다. </br>
기존 서버 코드는 유지한 채로 앱단에서 UI, 아키텍쳐, 비즈니스 로직 등을 수정하였습니다.
</p>
</br>

<p align="center">
<img src="https://github.com/yeon-kyu/Android_BlindCommunity_refactor/blob/main/previews/BC_from_login_to_post.gif" width="32%"/>
<img src="https://github.com/yeon-kyu/Android_BlindCommunity_refactor/blob/main/previews/BC_from_board_to_write_post.gif" width="32%"/>
<img src="https://github.com/yeon-kyu/Android_BlindCommunity_refactor/blob/main/previews/BC_from_board_to_write_comment.gif" width="32%"/>
</p>

</br>

 ## 구글 플레이스토어 링크
 https://play.google.com/store/apps/details?id=com.yeonkyu.blindcommunity
</br>
</br>

### 변경 사항
- 언어 변경 `Java` -> `Kotlin`
- `MVC` Architecture -> `MVVM` Architecture
- 의존성 주입(`Koin`) 추가
- deprecated 라이브라리(`AsyncTask`) 삭제 -> `coroutine` 추가
- `HttpUrlConnection` -> `OkHttp`+`Retrofit`
- `ListView` 삭제 -> `RecyclerView`+`DiffUtil` 을 이용한 `ListAdapter` 추가
- fragment 사용 방식 변경 -> `ViewPager2`, `Bottom Navigation` 라이브라리 사용
- `LinearLayout`, `RelativeLayout` 위주 XML -> `ConstraintLayout` 위주 XML
- 로그인 데이터 저장 방식 `FileInputStream` 삭제 -> `SharedPreference` 추가
- 내가 찜한 게시물 저장 기능 추가(`Room persistence` 사용)
- 기본 제공 다이얼로그 사용 방식 -> `커스텀 다이얼로그` 
- 보다 일관성있고 직관적인 변수, 함수, 클래스명
- res/value/string, res/value/color 파일 활용
- 더 직관적인 UI

</br>

## Tech stack & Open-source libraries
### Architecture
- `MVVM Architecture`
- `Koin` 을 이용한 `Dependency Injection`

### AAC Libraries
 - `ViewModel`
 - `DataBinding`
 - `LiveData`
 - `Room`

### REST API 통신
 - `retrofit2`
 - `OkHttp3`
 - `Gson`

### Other Libraries
 - `SwipeRefreshLayout`
 - `RecyclerView` + `ListAdapter`
 - `Navigation`
 - `RealTimeBlurView`
 - `Mockito`

### MAD Score Card

<p align="center">
<img height ="500" src="/previews/summary.png"/>
</p>
<p align="center">
<img height ="600" src="/previews/kotlin.png"/>
</p>
<p align="center">
<img height ="800" src="/previews/jetpack.png"/>
</p>
