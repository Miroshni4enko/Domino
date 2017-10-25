package com.vimi.controller.processor;

import com.vimi.controller.Commands;
import com.vimi.controller.GeneralProcess;
import com.vimi.exception.DataBaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Слава on 17.10.2017.
 */
public class Welcome implements GeneralProcess {

    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        Commands.forward("/index.jsp", request, response);
    }
}
