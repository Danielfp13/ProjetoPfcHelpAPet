package com.projeto.helpapet.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private MultipartFile file;
	private Integer id;
	
	public FileDTO() {
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}