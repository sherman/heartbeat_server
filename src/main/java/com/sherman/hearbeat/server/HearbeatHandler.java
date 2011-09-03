package com.sherman.hearbeat.server;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 04.09.11
 * Time: 1:36
 * To change this template use File | Settings | File Templates.
 */
public class HearbeatHandler extends AbstractHandler {
    private static final String ANSWER = "ok";

    @Override
    public void handle(
            String target,
            Request request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException, ServletException {
        OutputStream out = httpServletResponse.getOutputStream();
        out.write(ANSWER.getBytes());
        out.close();
    }
}
