package com.bora.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)    //test 진행할 때 Junit에 내장된 실행자 외에 다른 실행자를 실행시킴. 스프릥부트 테스트와 Junit 사이에 연결자 역할
@WebMvcTest(controllers = HelloController.class)    //@Service, @Component, @Repository등은 사용 X, 여기서는 컨트롤러만 사용하기 때문에 선언
public class HelloControllerTest {

    @Autowired  //스프링이 관리하는 빈을 주입받음
    private MockMvc mvc;    //웹 API 테스트할 때 사용, 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있음

    @Test
    public void returnHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void returnHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)    //param : API 테스트할 때 사용될 요청 파라미터를 설정, 값은 String만 허용, 그래서 숫자, 날짜 등의 데이터를 등록할 때도 문자열로 변경해야 함.
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    //JSON 응답값을 필드별로 검증할 수 있는 메소드, $를 기준을 필드명을 명시
                .andExpect(jsonPath("$.amount", is(amount)));

    }

}