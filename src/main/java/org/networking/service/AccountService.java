package org.networking.service;

import java.util.Date;

import org.networking.entity.Account;

/**
 * Created by Jona on 9/26/2015.
 */
public interface AccountService extends BaseService<Account> {
	void distributeGroupPoints(Date date);
}
