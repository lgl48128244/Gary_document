package org.zttc.itat.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.zttc.itat.dto.AttachDto;
import org.zttc.itat.model.Department;
import org.zttc.itat.model.Document;
import org.zttc.itat.model.User;
import org.zttc.itat.service.IDepartmentService;
import org.zttc.itat.service.IDocumentService;
import org.zttc.itat.util.ActionUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Scope("prototype")
@Controller("documentAction")
public class DocumentAction extends ActionSupport implements ModelDriven<Document> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Document doc;
	private Integer isRead;
	private Integer depId;
	private String con;
	private IDocumentService documentService;
	private IDepartmentService departmentService;
	private File[] atts;
	private String[] attsFileName;
	private String[] attsContentType;
	private Integer[] ds;

	public Integer[] getDs() {
		return ds;
	}

	public void setDs(Integer[] ds) {
		this.ds = ds;
	}

	public File[] getAtts() {
		return atts;
	}

	public void setAtts(File[] atts) {
		this.atts = atts;
	}

	public String[] getAttsFileName() {
		return attsFileName;
	}

	public void setAttsFileName(String[] attsFileName) {
		this.attsFileName = attsFileName;
	}

	public String[] getAttsContentType() {
		return attsContentType;
	}

	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public IDocumentService getDocumentService() {
		return documentService;
	}

	@Resource
	public void setDocumentService(IDocumentService documentService) {
		this.documentService = documentService;
	}

	@Override
	public Document getModel() {
		if (doc == null)
			doc = new Document();
		return doc;
	}

	public String listReceive() {
		ActionContext.getContext().put("deps", departmentService.listAllDep());
		if (isRead == null || isRead == 0) {
			ActionContext.getContext().put("pages", documentService.findNotReadDocument(con, depId));
		} else {
			ActionContext.getContext().put("pages", documentService.findReadDocument(con, depId));
		}
		return SUCCESS;
	}

	public String listSend() {
		ActionContext.getContext().put("deps", departmentService.listAllDep());
		User u = (User) ActionContext.getContext().getSession().get("loginUser");
		ActionContext.getContext().put("pages", documentService.findSendDocument(u.getId()));
		return SUCCESS;
	}

	public String show() throws IllegalAccessException, InvocationTargetException {
		Document td = documentService.load(doc.getId());
		BeanUtils.copyProperties(doc, td);
		ActionContext.getContext().put("atts", documentService.listAttachmentByDocument(doc.getId()));
		ActionContext.getContext().put("deps", documentService.listDocSendDep(doc.getId()));
		return SUCCESS;
	}

	public String updateRead() throws IllegalAccessException, InvocationTargetException {
		Document td = documentService.updateRead(doc.getId(), isRead);
		BeanUtils.copyProperties(doc, td);
		ActionContext.getContext().put("atts", documentService.listAttachmentByDocument(doc.getId()));
		ActionContext.getContext().put("deps", documentService.listDocSendDep(doc.getId()));
		return SUCCESS;
	}

	public String delete() {
		documentService.delete(doc.getId());
		ActionUtil.setUrl("document_listSend.action");
		return ActionUtil.REDIRECT;
	}

	public String addInput() {
		User u = (User) ActionContext.getContext().getSession().get("loginUser");
		List<Department> deps = departmentService.listUserDep(u.getId());
		ActionContext.getContext().put("deps", deps);
		return SUCCESS;
	}

	public void validateAdd() {
		if (ds == null || ds.length <= 0) {
			this.addFieldError("ds", "必须选择要发送的部门");
		}
		if (doc.getTitle() == null || "".equals(doc.getTitle())) {
			this.addFieldError("title", "部门公文的标题不能为空");
		}
		if (this.hasFieldErrors()) {
			addInput();
		}
	}

	public String add() throws IOException {
		if (atts == null || atts.length <= 0) {
			documentService.add(doc, ds, new AttachDto(false));
		} else {
			//String uploadPath = ServletActionContext.getServletContext().getRealPath("upload");
			String uploadPath = "D:\\teach_source\\class2\\j2EE\\document_project\\document01\\WebContent\\upload";
			documentService.add(doc, ds, new AttachDto(atts, attsContentType, attsFileName, uploadPath));
		}

		ActionUtil.setUrl("document_listSend.action");
		return ActionUtil.REDIRECT;
	}
}
