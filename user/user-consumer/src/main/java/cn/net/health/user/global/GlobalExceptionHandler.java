package cn.net.health.user.global;


import cn.net.health.user.dto.ResultInfo;
import cn.net.health.user.ex.BusinessException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理
 *
 * @author jun hu
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
    public ResultInfo<Boolean> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("参数解析失败！", e);
		return new ResultInfo<>("400", "参数解析失败！");
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
    public ResultInfo<Boolean> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法！", e);
		return new ResultInfo<>("405", "不支持当前请求方法！");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseBody
	public ResultInfo<Boolean> handleHttpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException e) {
		logger.error("不支持当前媒体类型！", e);
		return new ResultInfo<>("415", "不支持当前媒体类型！");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
	@ResponseBody
	public ResultInfo<Boolean> handleException(Exception e) {
        if(e instanceof BusinessException){
			BusinessException ex = (BusinessException) e;
			return new ResultInfo<>(ex.getCode(), ex.getMessage());
        }
		logger.error("服务运行异常！", e);
        e.printStackTrace();
		return new ResultInfo<>("500", "服务运行异常！");
    }

    /**
     * 403 - 没有权限
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResultInfo<Boolean> handleUnauthorizedException(UnauthorizedException e) {
        logger.error("没有权限！", e);
        return new ResultInfo<>("403","没有权限！");
    }

}
