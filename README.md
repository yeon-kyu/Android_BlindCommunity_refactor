# BlindCommunity2

2020년에 만들었던 <a href="https://github.com/yeon-kyu/Android_BlindCommunity" target="_blank"> BlindCommunity </a> 를 리펙토링한 프로젝트입니다.
기존 서버 코드 유지한 채로 앱단에서 UI, 아키텍쳐, 비즈니스 로직을 수정하였습니다

### 변경 사항
- 언어 변경 Java -> Kotlin
- MVC Architecture -> MVVM Architecture
- deprecated 라이브라리 삭제(AsyncTask) -> retrofit 추가
- ListView 삭제 -> RecyclerView 추가
- fragment 사용 방식 변경 -> Navigation 라이브라리 사용
- LinearLayout, RelativeLayout 기반 XML -> ConstraintLayout 기반 XML
- 로컬 데이터 저장 방식 FileInputStream 삭제 -> SharedPreference 추가

## Tech stack & Open-source libraries
### Architecture
- `MVVM Architecture`
- `Koin` 을 이용한 `Dependency Injection`

### AAC Libraries
 - `ViewModel`
 - `DataBinding`
 - `LiveData`

### REST API 통신
 - `retrofit2`
 - `OkHttp3`

### Other Libraries
 - `SwipeRefreshLayout`
 - `RecyclerView`
 - `Navigation`
