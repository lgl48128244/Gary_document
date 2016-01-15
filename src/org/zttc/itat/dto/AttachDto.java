package org.zttc.itat.dto;

import java.io.File;

public class AttachDto {
	private File[] atts;
	private String[] attsContentType;
	private String[] attsFilename;
	private boolean hasAttach;
	private String uploadPath;
	
	
	
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public boolean isHasAttach() {
		return hasAttach;
	}
	public void setHasAttach(boolean hasAttach) {
		this.hasAttach = hasAttach;
	}
	public File[] getAtts() {
		return atts;
	}
	public void setAtts(File[] atts) {
		this.atts = atts;
	}
	public String[] getAttsContentType() {
		return attsContentType;
	}
	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}
	public String[] getAttsFilename() {
		return attsFilename;
	}
	public void setAttsFilename(String[] attsFilename) {
		this.attsFilename = attsFilename;
	}
	public AttachDto(File[] atts, String[] attsContentType,
			String[] attsFilename,String uploadPath) {
		super();
		this.atts = atts;
		this.attsContentType = attsContentType;
		this.attsFilename = attsFilename;
		this.uploadPath = uploadPath;
		this.hasAttach = true;
	}
	
	public AttachDto(boolean hasAttach) {
		this.hasAttach = hasAttach;
	}
	public AttachDto() {
	}
}
