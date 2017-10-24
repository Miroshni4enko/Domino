package com.vimi.controler;


import com.vimi.controler.processor.GenerateSets;
import com.vimi.controler.processor.GetChain;
import com.vimi.controler.processor.Welcome;
import org.apache.log4j.Logger;

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

    private static final Logger LOG = Logger.getLogger(Commands.class);

    private Map<String, Object> map;
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
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }
}
