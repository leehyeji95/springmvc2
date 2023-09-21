package hello.itemservice.validation;

import com.sun.jdi.Field;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.FieldError;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    // 에러코드 넣으면 여러 개 반환해주는 인터페이스
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        //item 객체 사용
        for(String messageCode: messageCodes) {
            System.out.println("messageCode = " + messageCode);
            //messageCode = required.item
            //messageCode = required
//          new ObjectError("item", new String[]{"required.item", "required"});
        }

        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        //에러코드, 객체, 필드명, 필드타입
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        // 단축키 iter + Tab
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
//            messageCode = required.item.itemName
//            messageCode = required.itemName
//            messageCode = required.java.lang.String
//            messageCode = required
        }
//        bindingResult.rejectValue("itemName", "required");    -> 이 안에서 codesResolver 사용
//        new FieldError("item", "itemName", null, false, messageCodes, null, null, "");

        assertThat(messageCodes).containsExactly("required.item.itemName", "required.itemName", "required.java.lang.String", "required");
    }
}
