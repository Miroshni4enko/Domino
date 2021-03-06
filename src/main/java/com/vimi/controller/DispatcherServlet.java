package com.vimi.controller;

import com.vimi.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by vymi on 17.10.2017.
 */
public class DispatcherServlet extends HttpServlet {
    private Map<String, Object> map;
    
    private static final Logger LOG = LoggerFactory.getLogger(Commands.class);

    @Override
    public void init() throws ServletException {
        map = Commands.getInstance().getCommandsMap();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = Commands.ACTION_WELCOME;
        }

        GeneralProcess process;
        process = (GeneralProcess) map.get(action);

        if (process == null) {
            process = (GeneralProcess) map.get(Commands.ACTION_WELCOME);
        }
        if (process != null) {
            try {
                process.process(request, response);
            } catch (DataBaseException | SQLException e) {
                LOG.error("DispatcherServlet error  {}", e.toString());
            }
        }
    }
}
