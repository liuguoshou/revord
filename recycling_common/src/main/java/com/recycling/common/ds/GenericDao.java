package com.recycling.common.ds;

import java.io.Serializable;

public interface GenericDao<T, ID extends Serializable> {
	public Long getLastInsertId();
}
