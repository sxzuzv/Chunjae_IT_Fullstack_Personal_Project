package com.springmvc.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

// 사용자 정의 애너테이션 @BookId
// 도메인 클래스에 @BookId가 부여된 멤버 변수는 BookIdValidator 클래스로 유효성 검사를 수행한다.
@Constraint(validatedBy=BookIdValidator.class)
// Method, Field, Annotation_type을 정의하고, 이는 런타임 할 때 적용된다.
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BookId { // 사용자 정의 애너테이션 @BookId
    // 필수 속성 message, groups, payload를 포함한다.
    // @BookId에 대한 유효성 검사 시, 오류가 발생하면
    // 메시지 리소스 파일(messages.properties)에 설정된 BookId.NewBook.bookId의 메시지를 출력한다.
    String message() default "";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
