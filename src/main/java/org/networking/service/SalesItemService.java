package org.networking.service;

import java.util.List;

import org.networking.entity.SalesItem;

public interface SalesItemService extends BaseService<SalesItem> {
	List<SalesItem> findAll();
}
