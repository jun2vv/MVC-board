package com.goodee.mvcboard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.BoardService;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.BoardFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	// BoardService를 @Autowired 어노테이션을 통해 주입 받는다
	@Autowired
	private BoardService boardService;
	
	// 보드수정폼 매핑
	@GetMapping("board/modifyBoard")
	public String modifyBoard(Model model, Board board) {
		model.addAttribute("boardNo", board.getBoardNo());
		model.addAttribute("memberId", board.getMemberId());
		return "/board/modifyBoard";
	}
	@PostMapping("board/modifyBoard")
	public String modifyBoard(Board board) {
		int row = boardService.modifyBoard(board);
		if(row > 0) {
			log.debug("row : "+row+"수정성공"); 
		} else {
			log.debug("row : "+row+"수정실패"); 
		}
		return "redirect:/board/boardList";
	}
	
	// 보드삭제폼 매핑
	@GetMapping("/board/removeBoard")
	public String removeBoard(HttpServletRequest request, BoardFile boardFile) {
		String delPath = request.getServletContext().getRealPath("/upload/");
		int row = boardService.removeBoard(boardFile, delPath);
		if(row > 0) {
			log.debug("row : "+row+"삭제성공"); 
		} else {
			log.debug("row : "+row+"삭제실패"); 
		}
		return "redirect:/board/boardList";
	}
	
	// 보드추가폼 맵핑
	@GetMapping("/board/addBoard")
	public String addBoard() {
		return "/board/addBoard";
	}
	@PostMapping("/board/addBoard")
							// @RequestParam로 값을 받지않고 보드클래스로 값을 받는다 -> 커맨드객체
	public String addBoard(HttpServletRequest request, Board board) { // 매개값 request객체를 받는다
		String uploadPath = request.getServletContext().getRealPath("/upload/");
		int row = boardService.addBoard(board, uploadPath);
		if(row > 0) {
			log.debug("row : "+row+"입력성공"); 
		} else {
			log.debug("row : "+row+"입력실패"); 
		}
		return "redirect:/board/boardList";
	}
	
	// 보드상세보기
	@GetMapping("board/boardOne")
	public String selectBoardOne(Model model, Board board) {
		// 디비값을 저장할 객체 (반환타입이 클래스라 클래스로 받음)
		Board boardOne = boardService.selectBoardOne(board);
		// 모델로 view에 값 보내주기
		model.addAttribute("boardOne", boardOne);
		return "/board/boardOne";
	}
	
	
	// board리스트 맵핑
	@GetMapping("/board/boardList")
							// Model은 맵
	public String boardList(Model model, 
							@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
							@RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage,
							@RequestParam(name = "localName", required = false) String localName) {
		System.out.println(localName + ": localName");
		
		// 출력할 db를 서비스에서 불러온다
		Map<String, Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName);
		
		// request.setAttribute()와 비슷한 역할 model에 키값으로 저장하여 view로 분리해서 전달
		model.addAttribute("localNameList", resultMap.get("localNameList"));
		model.addAttribute("boardList", resultMap.get("boardList"));
		
		// 페이징값 넘기기
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		// 지역별 페이징
		model.addAttribute("localName", localName);
		
		
		return "/board/boardList";
	}
}
