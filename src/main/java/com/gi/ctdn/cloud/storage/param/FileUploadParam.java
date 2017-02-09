package com.gi.ctdn.cloud.storage.param;

import lombok.Data;

import java.io.Serializable;


@Data
public class FileUploadParam implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4535684679188717622L;
	
	private String name; //文件大小
	private boolean is_watermark; //是否水印
	private boolean keep;//是否保持长宽比
}
