package com.bot.exchanges.commons.utils;

import com.bot.commons.exceptions.AppException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomErrorDecoder implements ErrorDecoder {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = response.request().httpMethod().name() + " " + response.request().url() +
                ":\n Error message: " + response.status() + " - ";
        try {
            body += IOUtils.toString(response.body().asReader());
        } finally {
            return new AppException(body);
        }
    }
}