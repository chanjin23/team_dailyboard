package com.team5.first_project.board.service;

import com.team5.first_project.board.dto.RequestBoardDto;
import com.team5.first_project.board.dto.ResponseBoardDto;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.repository.BoardRepository;
import com.team5.first_project.exception.NotFoundByBoardIdException;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.entity.MemberRoleEnum;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시판 생성 (Create)
     */
    @Transactional
    public ResponseBoardDto saveBoard(String name, String description, String type, MultipartFile file) throws IOException {
        RequestBoardDto requestBoardDto = new RequestBoardDto(name, description, type);
        Board board = requestBoardDto.toEntity(requestBoardDto);

        //파일이 저장된 폴더 경로
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";

        if (!file.isEmpty()) {
            UUID uuid = UUID.randomUUID();  //랜덤으로 식별자를 생성
            String fileName = uuid + "_" + file.getOriginalFilename();//UUID와 파일이름을 포함된 파일 이름으로 저장
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            board.setFileName(fileName);
            board.setFilePath(fileName);    //static 아래부분의 파일 경로로만으로도 접근이 가능
        }
        Board saveBoard = boardRepository.save(board);

        return saveBoard.toResponseBoardDto();
    }

    /**
     * 모든 게시판 조회
     */
    @Transactional
    public List<Board> getAllBoards() {

        return boardRepository.findAll();
    }

    /**
     * 특정 게시판 조회
     */
    @Transactional
    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundByBoardIdException(id));
    }

    /**
     * 게시판 수정
     */
    @Transactional
    public void updateBoard(Long id, String description, String name, String type) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundByBoardIdException(id));
        board.update(description, name, type);

        boardRepository.save(board);
    }

    /**
     * 게시판 삭제
     */
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundByBoardIdException(id));

        boardRepository.deleteById(board.getId());
    }


    // 관리자인지 검증
    public boolean administratorVerification(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return false;
        }
        if (member.getRole() == MemberRoleEnum.ADMIN) {
            return true;
        } else {
            return false;
        }
    }
}
