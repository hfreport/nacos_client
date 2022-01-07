package com.example.nacosclient.base;

import java.io.Serializable;

/**
 * @author hf
 * date   2022/1/5 15:34
 * description
 */
public class BaseReq implements Serializable {
    private static final long serialVersionUID = -1181837134397794131L;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
