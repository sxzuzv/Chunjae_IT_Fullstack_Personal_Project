package kr.co.chunjae.controller;

import kr.co.chunjae.dto.BoardDTO;
import kr.co.chunjae.dto.CommentDTO;
import kr.co.chunjae.dto.PageDTO;
import kr.co.chunjae.service.BoardService;
import kr.co.chunjae.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board") // '/board'에 대한 요청명을 일괄적으로 처리할 수 있도록 한다.
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    // '/write' 요청명에 대해 처리하는 메서드 signForm()
    @GetMapping("/write")
    public String signForm() {
        // write.jsp(view)를 출력한다.
        return "write";
    }

    // 내용 작성 후, '작성' 버튼을 눌렀을 시 해당 요청(POST 방식)을 처리하는 메서드 sign()
    @PostMapping("/write")
    public String sign(@ModelAttribute BoardDTO boardDTO) {
        int saveResult = boardService.write(boardDTO);

//        if (saveResult > 0) { // 작성 내용이 있는 경우
//            // /board/ 경로로 redirect한다. (글 작성 이후, 페이지 목록으로 이동하도록 한다.)
//            return "redirect:/board/paging";
//        } else { // 작성 내용이 없는 경우
//            // write.jsp(view)를 출력한다. (기존 화면으로 돌아간다.)
//            return "write";
//        }

        return "redirect:/board/paging";
    }

    // '작성' 버튼 클릭 후 목록을 확인한다.
    // 'redirect:/board/'를 처리하는 메서드 findAll() => 글 목록을 출력한다.
    @GetMapping("/")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();

        // 모델에 가져온 목록 데이터를 넣는다.
        model.addAttribute("boardList", boardDTOList);

        // list.jsp(view)를 출력한다.
        return "list";
    }

    // 특정 번호를 클릭할 시, 해당 번호에 맞는 상세 내용을 출력한다.
    @GetMapping
    // @RequestParam을 이용하여 작성 내용의 번호를 받는다.
    public String findById(@RequestParam("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        // @RequestParam을 사용할 시, parameter가 들어오지 않으면 에러가 발생한다.
        // 혹시나 페이지를 parameter로 지정하지 못했을 시 에러가 발생하는 것을 방지하고자 required 속성을 false로 설정한다. (필수 값이 아니도록 조정)
        // 이때, defalutValue를 1로 설정하여 1 페이지로 기본 이동하도록 설정한다.

        // 조회수(hits) 기능을 추가한다.
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);

        // 모델에 가져온 상세 보기 데이터를 넣는다.
        model.addAttribute("board", boardDTO);
        // 상세 보기 시, 이전 페이지 번호를 가지고 와야 '목록' 클릭 시 돌아갈 페이지 번호를 활용할 수 있다.
        // 모델에 가져온 현재 페이지 번호를 넣는다.
        model.addAttribute("page", page);

        // 특정 번호에 해당하는 댓글 리스트를 불러와 commentDTOList에 저장한다.
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        // 모델에 불러온 댓글 리스트를 저장한다.
        model.addAttribute("commentList", commentDTOList);

        // detail.jsp(view)를 출력한다.
        return "detail";
    }

    // 특정 번호에 해당하는 게시글을 삭제한다.
    @GetMapping("/delete")
    // @RequestParam을 이용하여 삭제하고자 하는 게시글의 번호를 받는다.
    public String deleteById(@RequestParam("id") Long id) {
        boardService.deleteById(id);

        // 삭제 이후 목록 화면을 출력한다.
        return "redirect:/board/";
    }

    // 특정 번호에 해당하는 게시글의 내용을 수정한다.
    // 수정된 내용을 보여주는 @GetMapping
    @GetMapping("/update")
    public String updateForm(@RequestParam("id") Long id, Model model) {
        // 수정 반영된 내용(id parameter 이용)을 가져와 boardDTO에 저장한다.
        BoardDTO boardDTO = boardService.findById(id);

        // 모델에 boardDTO를 넣는다.
        model.addAttribute("board", boardDTO);

        // update.jsp(view)를 출력한다.
        return "update";
    }

    // '/update' 요청에 의해 내용을 수정하는 @PostMapping
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        // 사용자가 수정한 내용(boardDTO로 받아온다.)을 반영한다.
        boardService.update(boardDTO);

        // 수정 반영된 내용을 id parameter를 통해 가져와 dto에 저장한다.
        BoardDTO dto = boardService.findById(boardDTO.getId());

        // model에 속성명 'board'로 dto를 넣는다.
        model.addAttribute("board", dto);

        return "detail";
        // 수정 과정에서 발생하는 조회수 증가
//        return "redirect:/board?id+"+boardDTO.getId();
    }

    // 페이징
    // 초기 페이지 요청은 1 페이지를 보여준다.
    // '/board/paging?page=페이지 번호'에 대한 요청을 처리하는 메서드 paging()
    @GetMapping("/paging")
    public String paging(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        // @RequestParam을 사용할 시, parameter가 들어오지 않으면 에러가 발생한다.
        // 혹시나 페이지를 parameter로 지정하지 못했을 시 에러가 발생하는 것을 방지하고자 required 속성을 false로 설정한다. (필수 값은 아니게 조정)
        // 이때, defalutValue를 1로 설정하여 1 페이지로 기본 이동하도록 설정한다.
        System.out.println("page = " + page);

        // 지정된 페이지에서 보여줄 글 목록 데이터를 pagingList에 저장한다.
        List<BoardDTO> pagingList = boardService.pageList(page);

        // 지정한 페이지에서 보여줄 글 목록 데이터를 정상적으로 불러왔는지 확인한다.
        System.out.println("paginglist = " + pagingList);

        // 목록 하단에 페이징 숫자를 표기한다.
        // 실제 목록은 지정 페이지에 대한 내용만 나타나고, 다른 페이지 숫자는 출력만 하는 것이다.
        PageDTO pageDTO = boardService.pagingParam(page);

        // 선택한 페이지에 출력할 게시글 목록을 모델에 넣는다.
        model.addAttribute("boardList", pagingList);
        // pageDTO 값들을 모델에 넣는다. => 현재 페이지, 전체 페이지, 시작 페이지, 끝 페이지
        model.addAttribute("paging", pageDTO);

        return "paging";
    }

}
