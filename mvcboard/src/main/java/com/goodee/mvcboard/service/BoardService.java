package com.goodee.mvcboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.mvcboard.mapper.BoardFileMapper;
import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.BoardFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BoardService {
	// BoardMapper 인터페이스를 @Autowired 어노테이션을 통해 주입 받는다
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private BoardFileMapper boardFileMapper;
	
	// REST AIP chart 호출
	public  List<Map<String, Object>> getLocalNameList(){
		
		return boardMapper.selectLocalNameList();
	}
	
	// 보드수정
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	
	// 보드삭제
	public int removeBoard(BoardFile boardFile, String delPath) {
		// 보드파일테이블삭제 반환값
		int row = 0;
		// 보드테이블 삭제 반환값
		int resultRow = 0;
		// 보드파일타입에 리스트에다가 select문으로 가져온 savefilename을 넣는다. 
		List<BoardFile> saveFilename = boardFileMapper.selectSaveFilename(boardFile);
		log.debug(""+ saveFilename);
		
		// savefilename이 null이 아니면 실행
		if(saveFilename != null) {
			// 우선 보드파일테이블 데이터삭제
			row = boardFileMapper.deleteBoardFIle(boardFile);
			// 삭제한행의 개수만큼 upload폴더의 파일을 삭제
			if(row > 0) {
				for(BoardFile saveFile : saveFilename) {
					File f = new File(delPath+saveFile.getSaveFilename());
					f.delete();
				}
			}
			// 보드테이블 데이터삭제
			resultRow = boardMapper.deleteBoard(boardFile);
		}
		return resultRow;
	}
	
	// 보드추가및 보드파일추가
	public int addBoard(Board board, String uploadPath) {
		int row = boardMapper.insertBoard(board);
		int resultRow = 0;
		// board추가가 성공되어 row가1이고 첨부된 파일이 하나이상 있다면 실행 null이아니고 size가 1이상일때
		List<MultipartFile> fileList = board.getMultipartFile(); 
		System.out.println(fileList.size());
		
		if(row == 1 && fileList != null && fileList.size() > 0) {
			// board추가후 추가된 boardNo키값 변수에 저장
			int boardNo = board.getBoardNo();
			
			for(MultipartFile mf : fileList) { // 첨부된 파일의 개수만큼 반복해서 BoardFile객체에 저장
				if(mf.getSize() > 0) {
					BoardFile bf = new BoardFile();
					bf.setBoardNo(boardNo); // 부모테이블 키값
					bf.setOriginFilename(mf.getOriginalFilename()); // 파일원본이름 추가
					bf.setFilesize(mf.getSize());
					bf.setFiletype(mf.getContentType());;
					
					// 저장할 파일 이름 (. 이후로 추출하기에 확장자만 추출한다)
					String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
					// 새로운 이름 + 확장자
					String saveFileName = UUID.randomUUID().toString().replace("-", "") + ext;
					bf.setSaveFilename(saveFileName);
					log.debug(saveFileName +"<--- saveFileName");
					
					// 보드파일 테이블에 저장
					resultRow = boardFileMapper.insertBoardFile(bf);
					// 파일 저장(저장위치필요 -> path변수) X
					File f = new File(uploadPath+bf.getSaveFilename()); // path위치(upload폴더)에 저장파일이름으로 파일 저장
					// 빈파일에 첨부된 파일의 스트림을 주입한다.
					try {
						// 실제 파일이 저장되는 메서드
						mf.transferTo(f);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
						// 트랜잭션 처리를 위해 예외(try catch를 강요하지 않는 예외 RuntimeException()) 를 발생시켜야한다.
						throw new RuntimeException();
					}
				}
			}
		}
		return resultRow;
	}
	
	
	// 보드 상세보기
	public Board selectBoardOne(Board board) {
		return boardMapper.selectBoardOne(board);
	}
	
	// 보드 리스트
	public Map<String, Object> getBoardList(int currentPage, int rowPerPage, String localName) {
		
		// service레이어의 역할1 -> controller가 넘겨준 매개값을 dao에 매개값을 넘겨주기 위해 가공
		int beginRow = (currentPage -1) * rowPerPage;
		// 반환값1
		List<Map<String,Object>> localNameLIst = boardMapper.selectLocalNameList();
		
		// Mapper에 추상메서드의 매개값이 map이기때문에 메서드 매개변수에 사용할 map생성
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("beginRow", beginRow);
		paramMap.put("rowPerPage", rowPerPage);
		paramMap.put("localName", localName);
		
		// 반환값2
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
		
		// service레이어의 역할2 -> dao에서 반환받은 값을 컨트롤러에 반환값에 맞게 가공
		int boardCount = boardMapper.selectBoardCount();
		int lastPage = boardCount / rowPerPage;
		if(boardCount % rowPerPage != 0) {
			lastPage += 1;
		}
		
		// 결과값
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("localNameList", localNameLIst);
		resultMap.put("boardList", boardList);
		resultMap.put("lastPage", lastPage);
		
		// 반환값으로 Mapper인터페이스의 추상메서드를 반환
		return resultMap;
	}
}
