package com.springmvc.validator;

import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;
import com.springmvc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookIdValidator implements ConstraintValidator<BookId, String> {
    @Autowired
    private BookService bookService;

    // initialize() : 사용자 정의 애너테이션 @BookId의 관련 정보를 읽어 초기화 작업을 수행한다.
    public void initialize(BookId constraintAnnotation) {
    }

    // isValid() : 도메인 클래스 Book의 bookid 속성 값을 읽어서 유효성 검사를 수행한다.
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Book book;

        try {
            // bookService.getBookId() 메서드를 실행하여 입력된 도서 ID가 이미 있는 것이 파악될 경우
            book = bookService.getBookById(value);
        } catch (BookIdException e) { // BookIdException 예외 처리가 발생한다.
            return true;
        }
        if (book != null) {
            return false;
        }
        return true;
    }

}
