package org.zttc.itat.dao;


import org.springframework.stereotype.Repository;
import org.zttc.itat.model.Attachment;

@Repository("attachmentDao")
public class AttachmentDao extends BaseDao<Attachment> implements
		IAttachmentDao {


}
