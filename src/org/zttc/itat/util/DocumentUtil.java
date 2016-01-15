package org.zttc.itat.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.zttc.itat.dao.IAttachmentDao;
import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Attachment;
import org.zttc.itat.model.Document;
import org.zttc.itat.model.Message;

public class DocumentUtil {
	public static String[] addAttach(AttachDto ad,IAttachmentDao attachmentDao,Message msg, Document doc) throws IOException {
		// 1、添加私人信件对象
		if (ad.isHasAttach()) {
			// 需要添加附件
			File[] atts = ad.getAtts();
			String[] attsContentType = ad.getAttsContentType();
			String[] attsFilename = ad.getAttsFilename();
			String[] newNames = new String[atts.length];
			for (int i = 0; i < atts.length; i++) {
				File f = atts[i];
				String fn = attsFilename[i];
				String contentType = attsContentType[i];
				Attachment a = new Attachment();
				a.setContentType(contentType);
				a.setCreateDate(new Date());
				if(msg!=null)
					a.setMessage(msg);
				if(doc!=null) a.setDocument(doc);
				a.setOldName(fn);
				a.setSize(f.length());
				a.setType(FilenameUtils.getExtension(fn));
				String newName = getNewName(fn);
				a.setNewName(newName);
				newNames[i] = newName;
				attachmentDao.add(a);
			}
			// 上传附件
			String uploadPath = ad.getUploadPath();
			for (int i = 0; i < atts.length; i++) {
				File f = atts[i];
				String n = newNames[i];
				String path = uploadPath + "/" + n;
				FileUtils.copyFile(f, new File(path));
			}
			return newNames;
		}
		return null;
	}
	
	private static String getNewName(String name) {
		String n = new Long(new Date().getTime()).toString();
		n = n+"."+FilenameUtils.getExtension(name);
		return n;
	}
}
