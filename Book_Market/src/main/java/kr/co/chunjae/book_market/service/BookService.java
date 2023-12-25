package kr.co.chunjae.book_market.service;

import kr.co.chunjae.book_market.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookService {
    List<Book> getAllBookList();
    //카테고리 검색
    List<Book> getBookListByCategory(String category);
    //@RequestParam 이용해서 도서 ID와 일치하는 도서 정보 출력 요청 처리 메서드
    Book getBookById(String bookId);

    Set<Book> getBookListByFilter(Map<String, List<String>> filter);

    //신규 도서 정보 저장
    void setNewBook(Book book);
}
