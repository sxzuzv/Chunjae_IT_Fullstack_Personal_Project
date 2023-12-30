package com.springmvc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

// JSR-380 애너테이션(@Pattern, @Size, @Min, @Digits, @NotNull) 사용을 위해 패키지 import
import javax.validation.constraints.*;

import com.springmvc.validator.BookId;

@Getter
@Setter
//@AllArgsConstructor // 파라미터가 들어가는 생성자
@NoArgsConstructor  // 일반 생성자
public class Book {
    // Book 클래스의 멤버 변수에 대해 JSR-380을 선언한다.
    // 도서 ID : 첫 문자가 ISBN으로 시작하여 1부터 9까지 연속된 숫자가 오는 정규 표현식 패턴을 갖는다.
    // 유효성 검사 시, bookId 값이 정규 표현식 패턴과 일치하지 않을 경우
    // messages.properties 파일에 선언된 Pattern.NewBook.bookId 메시지를 출력한다.
    @Pattern(regexp = "ISBN[1-9]+")

    // bookId 속성에 대해 사용자 정의 제약 사항의 애너테이션을 선언한다.
    // 존재하는 도서 ID 유효성 검사 : 저장소 객체의 존재 여부에 대한 유효성 검사를 위해 사용자 정의 애너테이션 @BookId를 선언한다.
    @BookId
    private String bookId;            // 도서Id

    // 도서명 : 최소 4자 이상, 최대 50자 이하의 문자열 크기를 가진다.
    // 유효성 검사 시, name 값이 기준 문자열 크기에 해당되지 않을 경우
    // messages.properties 파일에 선언된 Size.NewBook.name의 메시지를 출력한다.
    @Size(min = 4, max = 50)
    private String name;              // 도서명

    // 멤버 변수 unitPrice의 제약 사항 (세 가지 JSR-380을 갖는다.)
    // @Min : unitPrice의 최솟값을 0으로 설정한다.
    // 유효성 검사에서 최솟값이 0 미만일 시, Min.NewBook.unitPrice의 메시지를 출력한다.
    @Min(value = 0)

    // @Digits : unitPrice의 자릿수를 설정(정수 8자리와 소수점 2자리)한다.
    // 유효성 검사 시, 자릿수와 일치하지 않으면 Digits.NewBook.unitPrice의 메시지를 출력한다.
    @Digits(integer = 8, fraction = 2)

    // @NotNull : unitPrice는 Null이 아닌 값을 갖는다.
    // 유효성 검사 시, Null 값일 경우 NotNull.NewBook.unitPrice의 메시지를 출력한다.
    @NotNull
    private int unitPrice;            // 도서 가격

    private String author;            // 저자
    private String description;       // 설명
    private String publisher;         // 출판사
    private String category;          //분류
    private long unitsInStock;        // 재고
    private String releaseDate;       // 출판일(월/년)
    private String condition;         // 신규 도서 또는 중고 도서 또는 전자책
    private MultipartFile bookImage;  // 도서 이미지


    public Book(String bookId, String name, int unitPrice) {
        super();
        this.bookId = bookId;
        this.name = name;
        this.unitPrice = unitPrice;
    }

}
