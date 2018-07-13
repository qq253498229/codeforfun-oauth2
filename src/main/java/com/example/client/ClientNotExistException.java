package com.example.client;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Package com.example.sbtest.client
 * Module
 * Project sb-test
 * Email 253498229@qq.com
 * Created on 2018/6/4 下午9:10
 *
 * @author wangbin
 */
@ResponseStatus(NOT_FOUND)
class ClientNotExistException extends RuntimeException {
}
