package com.vimi.controller.processor;

import com.vimi.controller.Commands;
import com.vimi.controller.GeneralProcess;
import com.vimi.db.dao.HistoryObject;
import com.vimi.db.util.DataAccessService;
import com.vimi.exception.DataBaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by vimi on 17.10.2017.
 */
public class Welcome implements GeneralProcess {
    public static final String HISTORY_SETS = "history_sets";
    
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        Object holderForHistorySets = request.getSession().getAttribute(HISTORY_SETS);
        List<HistoryObject> historyObjectList;
        if (holderForHistorySets == null) {
            historyObjectList = DataAccessService.getInstance().getAllHistory();
            request.getSession().setAttribute(HISTORY_SETS, historyObjectList);
        }
        Commands.forward("/index.jsp", request, response);
    }
    
}
