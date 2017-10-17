package com.vimi.controler.processor;

import com.vimi.controler.Commands;
import com.vimi.controler.GeneralProcess;
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
