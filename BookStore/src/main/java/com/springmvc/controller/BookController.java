package com.springmvc.controller;

import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;
import com.springmvc.exception.CategoryException;
import com.springmvc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String requestBookList(Model model){
        List<Book> list = bookService.getAllBookList(); // 웹 요청을 처리할 메서드
        model.addAttribute("bookList", list); // 모델 속성 bookList에 저장된 도서 목록을 저장
        return "books"; // book을 반환하므로 book.jsp가 있어야함.

    }

    /*@RequestMapping(value="/all")
    public String requestAllBookList(Model model){
        List<Book> list = bookService.getAllBookList(); // 웹 요청을 처리할 메서드
        model.addAttribute("bookList", list); // 모델 속성 bookList에 저장된 도서 목록을 저장
        return "books"; // book을 반환하므로 book.jsp가 있어야함.

    }*/

    @GetMapping("/all")
    public ModelAndView requestAllBookList( ){
        ModelAndView modelAndView = new ModelAndView();

        List<Book> list = bookService.getAllBookList(); // 웹 요청을 처리할 메서드
        modelAndView.addObject("bookList", list);
        modelAndView.setViewName("books");
        return modelAndView; // book을 반환하므로 book.jsp가 있어야함.
        // url : http://localhost:8092/BookMarket/books/all
    }

    @GetMapping("/{category}") // 경로변수 category(=@RequestMapping("/category")
    // 경로변수 category를 bookCategory로 재정의
    // 데이터를 모델에 저장해서 뿌리므로 모델이 필요함.
    public String requestBooksByCategory(@PathVariable("category") String bookCategory, Model model) {
        //bookCategory와 일치하는 도서 목록을 서비스 객체에서 가져와 booksByCategory에 저장
        List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);

        // 존재하지 않는 도서 분야(category)를 요청하는 경우에 대한 예외 처리 내용을 추가한다.
        if (booksByCategory == null || booksByCategory.isEmpty()) {
            throw new CategoryException();
        }

        // 키 이름 : 북 리스트 ,
        model.addAttribute("bookList", booksByCategory); // 값을 모델 속성 booklist에 저장 후 출력
        return "books"; // books.jsp
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model){
        Book bookById = bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "book"; // book.jsp
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBooksByFilter(@MatrixVariable(pathVar = "bookFilter") Map<String, List<String>> bookFilter, Model model) {

        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

    /* @GetMapping("/add")
     public String requestAddBookForm(Book book) {
         return "addBook";
     } 기존에서 아래로 수정*/
    @GetMapping("/add")
    public String requestAddBookForm(@ModelAttribute("NewBook") Book book) {
        return "addBook";
    }

    @PostMapping("/add")
    // 커맨드 객체에 @Valid를 선언하고 오류 처리 내용을 추가한다.
    public String submitAddNewBook(@Valid @ModelAttribute("NewBook") Book book,
                                   BindingResult result) {
        // submitAddNewBook() : 사용자의 입력 값을 커맨드 객체 NewBook으로 매핑할 때 유효성 검사가 진행된다.
        // 유효성 결과 값은 BindingResult 타입의 result 객체에 담긴다.
        if (result.hasErrors()) {
            // 유효성 검사로 발생된 오류가 result 객체에 있을 시, 뷰 이름 addBook을 반환하여 addBook.jsp에 출력한다.
            return "addBook";
        }

        // 이미지 등록을 위한 수정
        MultipartFile bookImage = book.getBookImage();

        String saveName = bookImage.getOriginalFilename();
        File saveFile = new File("C:\\upload", saveName);

        if (bookImage != null && !bookImage.isEmpty()) {
            try {
                // 파일 업로드를 진행한다.
                bookImage.transferTo(saveFile);
            } catch (Exception e) {
                throw new RuntimeException("도서 이미지 업로드가 실패하였습니다", e);
            }
        }
        bookService.setNewBook(book);
        return "redirect:/books";  // 웹 요청 URL을 강제로 /books로 이동시켜 매핑함.
    }

    @ModelAttribute // 메서드 수준의 @ModelAttribute 선언
    public void addAttribute(Model model) {
        // 모델 속성 addTitle에 신규 도서 등록을 저장함.
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("bookId","name","unitPrice","author", "description",
                "publisher","category","unitsInStock","totalPages", "releaseDate", "condition", "bookImage");
    }

    // @ExceptionHandler를 설정한 메서드 handleError()
    // @ExceptionHandler의 value 속성에 예외 클래스 BookIdException을 설정한다.
    @ExceptionHandler(value={BookIdException.class})
    public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
        // ModelAndView 클래스의 mav 인스턴스를 생성한다.
        ModelAndView mav = new ModelAndView();

        // 모델 속성 invalidBookId에 요청한 도서 아이디 값을 저장한다.
        mav.addObject("invalidBookId", exception.getBookId());

        // 모델 속성 exception에 예외 처리 클래스 BookIdException을 저장한다.
        mav.addObject("exception", exception);

        // 모델 속성 url에 요청 URL과 요청 쿼리문을 저장한다.
        mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());

        // 뷰 이름으로 errorBook을 설정하고 errorBook.jsp 파일을 출력한다.
        mav.setViewName("errorBook");

        // ModelAndView 클래스의 mav 인스턴스를 반환한다.
        return mav;
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "login";
    }

}
