package com.goodee.mvcboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.BoardFile;

// MyBatis의 매퍼 XML 파일에서 해당 쿼리를 실행하는 역할
@Mapper
public interface BoardMapper {
	
	// 보드삭제
	int deleteBoard(BoardFile boardFile);
	// 보드수정
	int updateBoard(Board board);
	// 보드추가
	int insertBoard(Board board);
	
	// 보드상세보기
	Board selectBoardOne(Board board);
	
	// local_name컬럼과 count() 반환
	List<Map<String,Object>> selectLocalNameList();
	
	// myBatis 메서드는 매개값을 하나만 활용한다.
	// param : Map<String, Object> map --> int beginRow, int rowPerPage
	List<Board> selectBoardListByPage(Map<String, Object> map); 
	
	// 전체 행의 수
	int selectBoardCount();
}
