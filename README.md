<h1>📌 Git 협업 규칙</h1>

<h2>1. 브랜치 전략</h2>
<p>본 프로젝트는 <strong>GitHub Flow</strong> 기반으로 진행한다.</p>

<h3>브랜치 구조</h3>
<table>
  <tr>
    <th>브랜치명</th>
    <th>역할</th>
  </tr>
  <tr>
    <td><code>main</code></td>
    <td>배포/최종 안정 버전. <strong>직접 push 금지</strong>. PR 리뷰를 통해 merge.</td>
  </tr>
  <tr>
    <td><code>feature/*</code></td>
    <td>기능 개발 브랜치. main에서 분기하여 개발 후 PR로 merge.</td>
  </tr>
  <tr>
    <td><code>fix/*</code></td>
    <td>버그 수정 브랜치.</td>
  </tr>
  <tr>
    <td><code>refactor/*</code></td>
    <td>리팩토링 브랜치.</td>
  </tr>
</table>

<hr>

<h2>2. 브랜치 네이밍 규칙</h2>

<h3>기능 개발</h3>
<pre>
feature/post-create
feature/post-read
feature/post-update
feature/post-delete
feature/like-add
feature/like-remove
</pre>

<h3>버그 수정</h3>
<pre>
fix/post-edit-error
fix/like-count-bug
</pre>

<h3>리팩토링</h3>
<pre>
refactor/post-service
refactor/like-controller
</pre>

<hr>

<h2>3. 커밋 메시지 규칙</h2>

<table>
  <tr>
    <th>타입</th>
    <th>설명</th>
  </tr>
  <tr>
    <td><code>feat</code></td>
    <td>새로운 기능 추가</td>
  </tr>
  <tr>
    <td><code>fix</code></td>
    <td>버그 수정</td>
  </tr>
  <tr>
    <td><code>refactor</code></td>
    <td>코드 리팩토링</td>
  </tr>
  <tr>
    <td><code>style</code></td>
    <td>UI/코드 스타일 변경</td>
  </tr>
  <tr>
    <td><code>docs</code></td>
    <td>문서 수정</td>
  </tr>
  <tr>
    <td><code>test</code></td>
    <td>테스트 코드 추가/수정</td>
  </tr>
  <tr>
    <td><code>chore</code></td>
    <td>빌드/환경설정/기타 작업</td>
  </tr>
</table>

<hr>

<h2>5. Pull Request 규칙</h2>

<h3>PR 제목 규칙</h3>
<pre>
[feat] 게시글 작성 기능 구현
[fix] 좋아요 수 오류 해결
[refactor] PostService 구조 개선
</pre>

<hr>

<h2>6. 폴더 구조 (예시)</h2>

<pre>
src
 └── main
     ├── java
     │    └── com.project
     │          ├── controller
     │          ├── service
     │          ├── repository
     │          ├── domain
     │          └── dto
     └── resources
          ├── application-example.yml
          ├── static
          └── templates
</pre>

<hr>

<h2>7. 코드 리뷰 기준</h2>

<ul>
  <li>PR은 1명 이상의 승인 필요</li>
  <li>중복 코드, 불필요한 로직이 없는지 확인</li>
  <li>예외 처리 적절성 검토</li>
  <li>네이밍 규칙 준수 여부 확인</li>
  <li>테스트 진행됐는지 확인</li>
</ul>

<hr>

<h2>8. 협업 규칙 요약</h2>

<ul>
  <li><strong>main 브랜치 직접 push 금지</strong></li>
  <li>모든 기능은 <code>feature/*</code> 브랜치에서 개발</li>
  <li>PR + 리뷰 승인 후 merge</li>
  <li>커밋 메시지 규칙 준수</li>
  <li>application.yml 등 secret 파일 push 금지</li>
</ul>
