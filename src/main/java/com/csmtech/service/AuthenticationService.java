package com.csmtech.service;

import com.csmtech.entity.UserMaster;

public interface AuthenticationService {

	UserMaster findByEmail(String email);
}
