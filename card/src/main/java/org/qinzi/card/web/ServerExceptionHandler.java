package org.qinzi.card.web;

import org.qinzi.card.exception.GlobalProcessException;
import org.qinzi.card.exception.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ServerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerExceptionHandler.class);

    @ExceptionHandler(GlobalProcessException.class)
    @ResponseBody
    public Result globalExceptionHandler(GlobalProcessException e){
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(TemplateException.class)
    public ModelAndView templateExceptionHandler(TemplateException e){
        return new ModelAndView("/404");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result unsuportMessageExceptionHandler(HttpRequestMethodNotSupportedException e){
        return Result.error("FF0000F1","Unsupport request");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e){
        return Result.error("FF0000F2","Some parameters are loss");
    }

    @ExceptionHandler(Exception.class)
    public Result unkownException(Exception e){
        logger.error("Unkown system error",e);
        return Result.error("FFFFFFFF","Unkown system error");
    }
}
