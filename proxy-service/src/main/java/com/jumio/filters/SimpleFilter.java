package com.jumio.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.util.StreamUtils;
import utils.JsonHelper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;

public class SimpleFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        return request.getRequestURL().toString().contains("/image");
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        try {
            InputStream in = ctx.getRequest().getInputStream();
            String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            Map<String, Object> stringObjectMap = new GsonJsonParser().parseMap(body);

            Image image = new Image();
            image.setImage(stringObjectMap.get("image").toString());

            byte[] encodedBytes = Base64.getEncoder().encode(stringObjectMap.get("image").toString().getBytes());
            stringObjectMap.replace("image", new String(encodedBytes));
            String s = new Gson().toJson(stringObjectMap);
            log.info(s);

            Image imageBase64 = new Image();
            imageBase64.setImage(stringObjectMap.get("image").toString());
            JsonHelper.writeToJson(image, "c:\\temp\\image.json");
            JsonHelper.writeToJson(imageBase64, "c:\\temp\\base64Image.json");

            final byte[] bodyContent = s.getBytes("UTF-8");
            ctx.setRequest(new HttpServletRequestWrapper(ctx.getRequest()) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(bodyContent);
                }

                @Override
                public int getContentLength() {
                    return bodyContent.length;
                }
            });

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }
}
