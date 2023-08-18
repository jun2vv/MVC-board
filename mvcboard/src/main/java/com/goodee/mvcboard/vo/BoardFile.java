package com.goodee.mvcboard.vo;

import lombok.Data;

@Data
public class BoardFile {
	private int boardFileNo;
	private int boardNo;
	private String originFilename;
	private String saveFilename;
	private String filetype;
	private long filesize;
}
