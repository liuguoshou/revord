package com.recycling.admin.ds;

import java.io.Serializable;

public interface AdminGenericDao<T, ID extends Serializable> {
	public Long getLastInsertId();
}
