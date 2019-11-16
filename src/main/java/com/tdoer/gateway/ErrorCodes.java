package com.tdoer.gateway;

import com.tdoer.springboot.http.StatusCodes;

public interface ErrorCodes extends StatusCodes {
    public Integer SHOULD_SELECT_STORE = 40100;
    public Integer USER_NOT_FOUND = 50001;
}
