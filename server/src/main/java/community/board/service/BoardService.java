package community.board.service;

import community.board.entity.Board;
import community.member.entity.Member;
import community.member.repository.MemberRepository;
import community.member.service.LevelService;
import community.member.service.MemberService;
import community.type.SearchType;
import community.board.repository.BoardRepository;
import community.exception.BusinessLogicException;
import community.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final LevelService levelService;
    private final MemberRepository memberRepository;

    /*게시글 등록*/
    public Board createBoard(Board board, Board.KindOfBoard kindOfBoard) {
        Board createBoard = boardRepository.save(board); // 게시판 저장
        createBoard.setKindOfBoard(kindOfBoard); // 게시판 분류하기
        
        Member member = memberService.findMember(createBoard.getMember().getMemberId()); //생성된 게시글을 작성한 회원을 찾는다
        member.setBoardCount(member.getBoardCount() + 1); //해당 회원에 대한 게시글 작성 카운트 1 증가

        createBoard.setBoardCreator(member.getName()); //작성자 이름
        createBoard.setCreatorLevel(member.getLevel().getLevel()); // 작성자 레벨

        levelService.memberlevel(member); // 커뮤니티 활동을 하면 레벨관련정보를 갱신
        
        return createBoard;
    }

    /*게시글 수정*/
    public Board updateBoard(Board board) {
        Board findBoard = findBoardById(board.getBoardId());

        Optional.ofNullable(board.getTitle()).ifPresent(findBoard::setTitle);
        Optional.ofNullable(board.getContents()).ifPresent(findBoard::setContents);

        return findBoard;
    }
    public Board findBoard(long boardId) {
        return findBoardById(boardId);
    }


    /*게시글 검색 및 조회*/
    @Transactional(readOnly = true) // 변경하지 않기때문에 readonly
    public Page<Board> findBoards(Board.KindOfBoard kindOfBoard, SearchType searchType, String search_keyword, Pageable pageable) {
        // 검색어 없이 검색하면 게시글 페이지를 반환.
        if (search_keyword == null || search_keyword.isBlank()) {
            return boardRepository.findAllByKindOfBoard(kindOfBoard, pageable);
        }
        // 항목에 따른 검색 - 조회
        switch (searchType) {
            case TITLE:
                return boardRepository.findByKindOfBoardAndTitleContaining(kindOfBoard, search_keyword, pageable);
            case CONTENTS:
                return boardRepository.findByKindOfBoardAndContentsContaining(kindOfBoard, search_keyword, pageable);
        }return null;
    }

    /*회원이 작성한 게시글 목록*/
    @Transactional(readOnly = true)
    public Page<Board> findMemberBoards(long memberId, Pageable pageable){
        Member member = memberService.findMember(memberId);
        return boardRepository.findAllByMember(member, pageable);
    }


    /* 추천 게시글 페이지 */
    @Transactional
    public Page<Board> rankBoards(Board.KindOfBoard kindOfBoard , Pageable pageable){
        Page<Board> boardPage = boardRepository.findAllByKindOfBoard(kindOfBoard, pageable);

        return boardPage;
    }
    
    /*게시글 작성자들의 이름, level정보 업데이트*/
    public void userAndLevelUpdate(){
        List<Board> boards = boardRepository.findAll(); //전체조회
        for(Board board : boards){  //항상 수정되게 하는 이 코드를 어떻게하면 효율적으로 바꿀 수 있을까?
            board.setBoardCreator(board.getMember().getName());
            board.setCreatorLevel(board.getMember().getLevel().getLevel());
        }
    }

    /*게시글 삭제*/
    public void deleteBoard(Long boardId) {
//        Board board = findBoard(boardId);

        boardRepository.deleteById(boardId);

        //레벨이 줄어들지 않게 해달라는 요청을 받음
//        Member member = memberService.findMember(board.getMember().getMemberId()); //생성된 게시글을 작성한 회원을 찾는다
//        member.setBoardCount(member.getBoardCount() - 1); //해당 회원에 대한 게시글 작성 카운트 1 감소
    }

    /*게시글 ID 찾기*/
    public Board findBoardById(long boardId) {
        Optional<Board> optionalQuestion = boardRepository.findById(boardId);
        return optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }
    /*조회수 관련*/
    @Transactional
    public int updateViewCount(Long id) {
        return boardRepository.updateViewCount(id);
    }

}