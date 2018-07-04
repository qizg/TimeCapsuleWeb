package com.sinwn.capsule.web;

import com.sinwn.capsule.constant.Constant;
import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.domain.ResponseBean;
import com.sinwn.capsule.exception.UnauthorizedException;
import org.apache.shiro.ShiroException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        logger.error(e.getMessage());
        return new ResponseBean(Constant.UNAUTHORIZED, StrConstant.AUTHORIZED_ERROR);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401() {
        //"Unauthorized"
        return new ResponseBean(Constant.UNAUTHORIZED, StrConstant.AUTHORIZED_ERROR);
    }

    // 捕捉输入参数错误异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBean requestException(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();
        List<ObjectError> errorList = result.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : errorList) {
            sb.append(error.getDefaultMessage());
            sb.append(";");
        }
        return new ResponseBean(Constant.REQUEST_ERROR, sb.substring(0, sb.length() - 1));
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        logger.error(ex.getMessage());
        if (ex instanceof MyBatisSystemException || ex instanceof DataIntegrityViolationException) {
            return new ResponseBean(getStatus(request).value(), StrConstant.SYSTEM_ERROR);
        }
        return new ResponseBean(getStatus(request).value(), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
