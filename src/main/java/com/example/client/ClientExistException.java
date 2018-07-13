package com.example.client;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * @author wangbin
 */
@ResponseStatus(CONFLICT)
class ClientExistException extends RuntimeException {
}
