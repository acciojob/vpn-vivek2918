package com.driver.services;

import com.driver.model.User;

public interface ConnectionService {
    public User disconnect(int userId) throws Exception;

}