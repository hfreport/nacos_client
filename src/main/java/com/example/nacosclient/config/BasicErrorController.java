package com.example.nacosclient.config;

import com.example.nacosclient.base.ResponseCodeEnum;
import com.example.nacosclient.base.Result;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author hf
 * date   2022/1/5 17:00
 * description controller advice 无法处理404情况。spring boot提供errorController处理这种情况。
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController extends AbstractErrorController {

    public BasicErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public Object error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                false);
        HttpStatus status = getStatus(request);
        Result result = Result.buildFail(ResponseCodeEnum.URL_NOT_FOUND);
        result.setData(new ResponseEntity<>(body, status));
        return result;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
