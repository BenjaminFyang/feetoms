package com.crm.oms.exception;

import com.crm.oms.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult<String> handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult<String> handle(Exception exception) {
        log.error("出现异常", exception);
        return CommonResult.failed("服务器出现异常");
    }


    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<String> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return getCommonResult(bindingResult);
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult<String> handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return getCommonResult(bindingResult);
    }


    /**
     * 异常数据封装.
     *
     * @param bindingResult 验证框架
     * @return the CommonResult
     */
    private CommonResult<String> getCommonResult(BindingResult bindingResult) {
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }
}
