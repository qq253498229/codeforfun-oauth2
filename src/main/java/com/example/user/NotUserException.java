package com.example.user;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * 用户名不存在异常
 * Package com.example.sbtest.user
 * Module
 * Project sb-test
 * Email 253498229@qq.com
 * Created on 2018/6/4 下午9:24
 *
 * @author wangbin
 */
@ResponseStatus(NOT_FOUND)
class NotUserException extends RuntimeException {
}
