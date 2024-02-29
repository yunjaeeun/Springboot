package com.ohgiraffers.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* 설명.
 *  @RestController는 모든 핸들러 메소드에 @ResponseBody를 붙여주는 어노테이션이다.
 *  이 Controller의 핸들러 메소드 반환값은 이제 viewResolver가 처리하지 않는다
 *  (JSON 문자열로 반환되기 떄문)
* */
@RestController
@RequestMapping("/response")
public class ResponseRestController {

    @GetMapping("/hello")
    public String helloworld() {
        return "hello world";
    }

    @GetMapping("/random")
    public int getRandomNumber() {
        return (int)(Math.random() * 10) + 1;
    }

    @GetMapping("/message")
    public Message getMessage() {
        return new Message(200, "메시지를 응답합니다");
    }

    /* 설명.
     *  JSON(JavaScript Object Notation): 자바 스크립트가 인지할 수 있는 객체 형태의 문자열
     *  RestController에서 반환한 것(자바의 타입들)은 모두 JSONArray형태([]), 또는 JSONObject형태({})로 바뀌어
     *  JSON 문자열로 반환된다.
     *  1. Map 또는 일반 클래스 타입 -> {} 형태
     *  2. ArrayList -> [] 형태
    * */
    @GetMapping("/test")
    public List<Map<String, Message>> getTest() {
        List<Map<String, Message>> list = new ArrayList<>();
        Map<String, Message> map = new HashMap<>();
        map.put("test1", new Message(200, "성공"));
        map.put("test2", new Message(200, "성공"));
        list.add(map);
        return list;
    }

    @GetMapping("/list")
    public List<String> getList() {
        return List.of(new String[]{"사과", "바나나", "복숭아"});
    }

    @GetMapping("/map")
    public Map<Integer, String> getMapping() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(200, "정상응답"));
        messageList.add(new Message(404, "페이지를 찾을 수 없습니다"));
        messageList.add(new Message(500, "개발자의 잘못입니다."));

        return messageList.stream()     // list에 있는 값을 하나씩 꺼내 개별적으로 동작함.(코드와 메세지를 하나씩 꺼내 Map으로 담음)
                .collect(Collectors.toMap(Message::getHttpStatusCode, Message::getMessage));
    }

    /* 설명. 이미지 응답하기 */
    /* 설명. produces는 response header의 content-type 설정이다 */
    @GetMapping(value = "/image", produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/static/12345.gif").readAllBytes();
    }

    /* 설명. ResponseEntity는 필수는 아니지만 유용하게 사용할 수 있다. */
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity() {
        return ResponseEntity.ok(new Message(200, "응답성공"));     // ok를 붙이면 실제로 200번 응답코드를 가지고 간다.
    }
}
