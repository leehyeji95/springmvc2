package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms; // Spring Boot 가 자동으로 등록해줌

    @Test
    void helloMessage() {
        System.out.println("System Locale="+ Locale.getDefault());
        // default 로 설정됨
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");

    }

    @Test
    void notFoundMessageCode() {
        // 메시지 코드를 찾을 수 없는 경우 예외 발생하기
        // properties 에 no_code 없음 (hello 만 있음)
//        ms.getMessage("no_code", null, null);
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        // 메시지 없는 경우 기본 메시지 설정 출력
        String result = ms.getMessage("no_code", null, "기본메시지", null);
        assertThat(result).isEqualTo("기본메시지");
    }

    @Test
    void argumentMessage() {
    // 매개변수 사용법 -> Object 배열 넘기기
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        // 기본 언어
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
