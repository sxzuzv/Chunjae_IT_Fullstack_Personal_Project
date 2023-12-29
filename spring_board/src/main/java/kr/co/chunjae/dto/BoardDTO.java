package kr.co.chunjae.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString // 다수의 데이터를 한 번에 return 하기 위해 사용한다. (메서드 작성 시, return은 한 번만 가능하므로)
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private Timestamp boardCreatedTime;
}
