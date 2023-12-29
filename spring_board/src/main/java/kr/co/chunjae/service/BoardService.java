package kr.co.chunjae.service;

import kr.co.chunjae.dto.BoardDTO;
import kr.co.chunjae.dto.PageDTO;
import kr.co.chunjae.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // INSERT : 글 작성
    public int write(BoardDTO boardDTO) {
        return boardRepository.write(boardDTO);
    }

    // READ : 전체 목록 보기
    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    // READ : 특정 번호 게시글 상세 보기
    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    // Hits : 조회수 반영
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    // DELETE : 특정 번호 게시글 삭제
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    // UPDATE : 특정 번호 게시글 수정
    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    // PAGING : 페이징 처리 (특정 페이지에 해당하는 게시글 목록만을 출력)
    int pageLimit = 3; // 한 페이지당 보여주는 글 갯수
    int blockLimit = 3; // 페이지 하단에 보여주는 페이지 번호의 갯수

    public List<BoardDTO> pageList(int page) {
        /*
        한 페이지당 보여지는 글 갯수 : 3개
            1 page => 0
            2 page => 3
            3 page => 6
         */
        // 0부터 시작하므로 -1 계산이 필요하다.
        int pagingStart = (page - 1) * pageLimit;

        Map<String, Integer> pagingParams = new HashMap<>();

        // 내림차순하여 해당 페이지에서 출력할 게시글 범위를 지정한다.
        // start가 0이고 limit이 3이라면 게시글 번호 12~9가 출력된다. (내림차순)
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);

        List<BoardDTO> pagingList = boardRepository.pagingList(pagingParams);

        return pagingList;
    }

    public PageDTO pagingParam(int page) {
        // 전체 게시글 개수를 boardCount에 저장한다.
        int boardCount = boardRepository.boardCount();

        // 게시글 개수에 따른 전체 페이지 개수를 계산한다.
        // '전체 게시글 개수 / 한 페이지당 출력할 게시글 게수(3개)'를 계산하여 무조건 반올림(ceil)한다.
        // ex) 게시글 개수가 13개일 시, 총 페이지는 13/3을 반올림한 5개가 된다.
        int maxPage = (int) Math.ceil((double) boardCount / pageLimit);

        // 현재 페이지 기준 시작 페이지 값을 계산한다. (1, 4, 7, 10 ...)
        // blockLimit : 페이지 하단에 보여줄 페이지 번호의 개수를 의미하며, 한 페이지당 3개의 페이지 번호를 보여준다.
        // 매개변수로 받아오는 page는 선택된 page를 의미한다.
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;

        // 현재 페이지 기준 끝 페이지 값을 계산한다. (3, 6, 9, 12 ...)
        int endPage = startPage + blockLimit - 1;

        if (endPage > maxPage) { // 마지막 페이지 번호가 전체 페이지 개수보다 클 경우
            endPage = maxPage; // 마지막 페이지 번호를 전체 페이지 개수로 지정한다.
        }

        // PageDTO에 계산한 값들을 저장한다.
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);

        return pageDTO;
    }
}
