package org.zttc.itat.dao;


import org.springframework.stereotype.Repository;
import org.zttc.itat.model.Document;

@Repository("documentDao")
public class DocumentDao extends BaseDao<Document> implements IDocumentDao {


}
