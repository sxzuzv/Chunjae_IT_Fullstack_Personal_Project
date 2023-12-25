package kr.co.chunjae.book_market.controller;

import kr.co.chunjae.book_market.domain.Book;
import kr.co.chunjae.book_market.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String requestBookList(Model model) {
        List<Book> list = bookService.getAllBookList();
        model.addAttribute("bookList", list);
        return "books";
    }

    @GetMapping("/all")
    public ModelAndView requestAllBookList() {
        ModelAndView mav = new ModelAndView();
        List<Book> list = bookService.getAllBookList();
        mav.addObject("bookList", list);
        mav.setViewName("books");
        return mav;
    }

    //카테고리 검색 기능
    @GetMapping("/{category}")
    public String requestBooksCategory(@PathVariable("category") String bookCategory, Model model) {
        List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
        model.addAttribute("bookList", booksByCategory);
        return "books";
    }

    //@RequestParam 이용해서 도서 ID와 일치하는 도서 정보 출력 요청 처리 메서드
    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model) {
        Book bookById = bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "book";
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBooksByFilter(@MatrixVariable(pathVar = "bookFilter") Map<String, List<String>> bookFilter, Model model) {
        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

    //신규 도서 정보 등록(도서 등록 페이지)
    @GetMapping("/add")
    public String requestAddBookForm(@ModelAttribute("NewBook") Book book) {
        return "addBook";
    }
    @PostMapping("/add")
    public String submitAddNewBook(@ModelAttribute("NewBook") Book book) {
        bookService.setNewBook(book);
        return "redirect:/books";
    }

    //도서 등록 페이지-제목 출력
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("addTitle", "신규 도서 등록");
    }
}
