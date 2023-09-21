package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    //Item 검증하는 Validator 생성
    //Spring 인터페이스 Validator 를 사용하는 장점

    @Override
    public boolean supports(Class<?> clazz) {
        //파라미터로 넘어오는 Class 가 Item 을 지원되는지
        //isAssignableFrom() 의미는
        //item == clazz
        //item == subItem
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //검증로직
        Item item = (Item) target;
        //Errors 의 자식이 BindingResult -> 다형성으로 errors 에 bindingResult 담아서 사용가능

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())) {
            //1. 상품명이 없는 경우
            errors.rejectValue("itemName", "required");
        }

        if(item.getPrice() == null || item.getPrice() <= 1000 || item.getPrice() >= 1000000) {
            //2. 가격 제한
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if(item.getQuantity() == null || item.getQuantity() >= 9999) {
            //3. 수량 제한
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                //특정 필드가 아님
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

    }
}
