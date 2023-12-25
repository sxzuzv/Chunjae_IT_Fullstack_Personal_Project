package kr.co.chunjae.book_market.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor      //생성자
public class Book {
    private String bookId;          //도서ID
    private String name;            //도서명
    private int unitPrice;          //가격
    private String author;          //저자
    private String description;     //설명
    private String publisher;       //출판사
    private String category;        //분류
    private long unitsInStock;       //재고 수
    private String releaseDate;     //출판일(월/년)
    private String condition;       //신규 도서 또는 중고 도서 또는 전자책

    public Book(String bookId, String name, int unitPrice) {
        this.bookId = bookId;
        this.name = name;
        this.unitPrice = unitPrice;
    }
}
