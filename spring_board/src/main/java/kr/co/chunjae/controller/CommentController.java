package kr.co.chunjae.controller;

import kr.co.chunjae.dto.CommentDTO;
import kr.co.chunjae.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment") // '/comment'에 대한 요청명을 일괄적으로 처리할 수 있도록 한다.
public class CommentController {
    private final CommentService commentService;

    // 기존에는 @GetMapping을 통해 Form을 열고, @PostMapping을 통해 데이터를 보내게 된다.
    // 댓글의 경우 @PostMapping만 필요하다.
    // 요청명 '/comment/save'를 처리하는 메서드 save()
    @PostMapping("/save")
    public @ResponseBody List<CommentDTO> write(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO = " + commentDTO);

        // 댓글 작성 내용을 Database에 반영한다.
        commentService.write(commentDTO);

        // 특정 게시글 번호에 해당하는 댓글 리스트를 Database에서 불러와 commentDTOList에 저장한다.
        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());

        // 저장한 댓글 리스트를 반환한다.
        return commentDTOList;
    }


}
