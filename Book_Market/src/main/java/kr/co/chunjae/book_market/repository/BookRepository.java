package kr.co.chunjae.book_market.repository;

import kr.co.chunjae.book_market.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookRepository {

    //도서 전체 리스트 출력
    List<Book> getAllBookList();

    //도서 검색 , category 정보를 줘서 검색기능만들기
    List<Book> getBookListByCategory(String category);

    //@RequestParam 이용해서 도서 ID와 일치하는 도서 정보 출력 요청 처리 메서드
    Book getBookByID(String bookId);

    //
    Set<Book> getBookListByFilter(Map<String, List<String>> filter);

    //@ModelAttribute 등록 페이지에서 입력된 파라미터 값을 커맨드 객체로 바인딩-저장소 객체에 신규 도서 정보 저장
    void setNewBook(Book book);

}
