# 섹션5. 회원 관리 예제 - 웹 MVC 개발

회원 웹 기능으로 홈화면을 추가하고 회원 등록, 회원 목록 조회 컨트롤을 만들어본다. 

# 홈 화면 추가

`/`로 오는 GET 요청에 응답하기 위해 HomeController와 처리 메소드, `home.html`을 만든다. 

```java
package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
```

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<div class="container">
  <div>
    <h1>Hello Spring</h1>
    <p>회원 기능</p>
    <p>
      <a href="/members/new">회원 가입</a>
      <a href="/members">회원 목록</a>
    </p>
  </div>
</div> <!-- /container -->
</body>
</html>
```

이제부터 `/`로 오는 GET 요청은 기존의 `resources/static/index.html`이 아닌 `home.html`이 반환되기 시작한다. 그 이유는 스프링이 먼저 스프링 컨테이너에서 관련된 컨트롤러를 찾고, 없으면 static 파일을 찾기 때문이다. 

![컨트롤러-스태틱 우선순위](resources/컨트롤러-스태틱%20우선순위.png)

# 회원 등록

MemberController에 `/members/new` GET, POST 요청에 응답하는 메소드와 html 파일을 만든다. HTML 파일의 form 태그 내부 input 태그의 name property가 핵심이다. name property의 값과 폼으로 쓰는 자바 클래스의 멤버 변수와 이름이 일치해야 한다. 

```java
@Controller
public class MemberController {
...
		@GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }
```

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<div class="container">
  <form action="/members/new" method="post">
    <div class="form-group">
      <label for="name">이름</label>
      <input type="text" id="name" name="name" placeholder="이름을 입력하세요">
      <!--                         ^     ^ 서버에서 key로 쓰는 값    -->
    </div>
    <button type="submit">등록</button>
  </form>
</div> <!-- /container -->
</body>
</html>
```

```java
package hello.hellospring.controller;

public class MemberForm {
    // 단순 자바 클래스로 HTML 폼 내용을 자바로 받기 위함    
    private String name;

    public String getName() {
      //          ^ 존재해야 함
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

스프링은 HTML form input 태그의 `name="variable"`을 보고 폼 객체의 setter인 `setVariable()`을 찾아 호출하여 PostMapping된 메소드의 argument로 넘겨준다. Setter가 반드시 있어야 한다. 

# 회원 리스트 조회

회원 컨트롤러에 `/members` GET 요청을 처리하는 메소드를 정의한다. 

```java
@Controller
public class MemberController {
		...
		@GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();

        model.addAttribute("members", members);
        return "members/memberList";
    }
		...
}
```

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<div class="container">
    <div>
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>이름</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="member : ${members}">
                <td th:text="${member.id}"></td>
                <td th:text="${member.name}"></td>
            </tr>
            </tbody>
        </table>
    </div>
</div> <!-- /container -->
</body>
</html>
```

자바의 for-each 문법과 닮은 문법이 Thymeleaf에 있다. member.id는 member 객체의 getId 메소드를 호출해 값을 알아내는 방식으로 작동한다. 

이는 장고 템플릿 언어의 `{% for member in members %} {{ member.id }}`와 매우 유사하다.