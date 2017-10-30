package com.vimi.controller;


import com.vimi.controller.processor.GenerateSets;
import com.vimi.controller.processor.GetChain;
import com.vimi.controller.processor.Welcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Commands {
    public static final String ACTION_WELCOME = "welcome";
    public static final String GET_CHAIN = "getChainFromPool";
    public static final String GENERATE_SETS = "generateSets";
    private Map<String, Object> map;

    private static final Logger LOG = LoggerFactory.getLogger(Commands.class);
    
    protected static class Singleton {
        public static final Commands _INSTANCE = new Commands();

    }

    private Commands() {
        this.initMap();
    }

    public static Commands getInstance() {
        return Singleton._INSTANCE;
    }

    public Map<String, Object> getCommandsMap(){
        return map;
    }

    private void initMap() {
        map = new HashMap<>();
        map.put(ACTION_WELCOME, new Welcome());
        map.put(GET_CHAIN, new GetChain());
        map.put(GENERATE_SETS, new GenerateSets());
    }

    public static void forward(String url, HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e.toString());
        } catch (IOException e) {
            LOG.error(e.toString());
        }
    }
}
