package com.tokabout.ming.web.support;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 13. 8. 19.
 */
public class ErrorResponseBody {

    private String code;
    private String message;


    public ErrorResponseBody(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }


    public ErrorResponseXmlBody toXmlBody() {
        return new ErrorResponseXmlBody(this);
    }

    @JacksonXmlRootElement(localName = "response")
    public class ErrorResponseXmlBody {

        private ErrorResponseBody error;

        public ErrorResponseXmlBody(ErrorResponseBody error) {
            this.error = error;
        }

        public ErrorResponseBody getError() { return error; }

    }

}
