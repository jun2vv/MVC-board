package com.goodee.mvcboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.BoardFile;

@Mapper
public interface BoardFileMapper {
	// 보드파일추가
	int insertBoardFile(BoardFile boardFile);
	// 보드파일삭제
	int deleteBoardFIle(BoardFile boardFile);
	
	List<BoardFile> selectSaveFilename(BoardFile boardFile);
	
}
