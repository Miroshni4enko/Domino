package com.vimi.controller.processor;

import com.vimi.controller.Commands;
import com.vimi.controller.GeneralProcess;
import com.vimi.db.dao.HistoryObject;
import com.vimi.db.util.DataAccessService;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Chain;
import com.vimi.model.Domino;
import com.vimi.model.MenegerDomino;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.vimi.controller.processor.Welcome.HISTORY_SETS;

/**
 * Created by vymi1016 on 10/24/2017.
 */
public class GenerateSets implements GeneralProcess {
    public final static String ALL_SETS = "all_sets";
    public final static String GET_SETS = "get_all_sets";

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        String getAllSets = request.getParameter(GET_SETS);
        Object holderForDominoList =  request.getSession().getAttribute(GetChain.CHAIN);
        List<Chain> sets = null;
        if (holderForDominoList != null) {
            List<Domino> dominoList = (List<Domino>) holderForDominoList;
            if (getAllSets == null) {
                sets = Collections.singletonList(Collections.max(MenegerDomino.generateChains(dominoList), new Comparator<Chain>() {
                    @Override
                    public int compare(Chain o1, Chain o2) {
                        return o1.size() > o2.size() ? 1 : 0;
                    }
                }));
            } else {
                sets = MenegerDomino.generateChains(dominoList);
           }
            DataAccessService dataAccessService = DataAccessService.getInstance();
            Date date = new Date();
            String chain = dominoList.toString();
            int chain_id = dataAccessService.createChain(chain, date);
            dataAccessService.createSets(chain_id, sets);

            Object holderForHistorySets = request.getSession().getAttribute(HISTORY_SETS);
            List<HistoryObject> historyObjectList;
            if (holderForHistorySets != null) {
                historyObjectList = (List<HistoryObject>) holderForHistorySets;
            } else {
                historyObjectList = new ArrayList<>();
            }
            
            for(Chain ch : sets){
                historyObjectList.add(new HistoryObject(date, chain, ch.toString()));
            }
            
            request.getSession().setAttribute(HISTORY_SETS, historyObjectList);
            request.getSession().setAttribute(ALL_SETS, sets);
        }
        
        Commands.forward("/GenerateSets.jsp", request, response);
    }
}
