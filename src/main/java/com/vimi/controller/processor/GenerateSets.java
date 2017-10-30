package com.vimi.controller.processor;

import com.vimi.controller.Commands;
import com.vimi.controller.GeneralProcess;
import com.vimi.db.dao.HistoryObject;
import com.vimi.db.util.DataAccessService;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Chain;
import com.vimi.model.Domino;
import com.vimi.model.MenegerDomino;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.vimi.controller.processor.Welcome.HISTORY_SETS;

/**
 * Created by vymi1016 on 10/24/2017.
 */
public class GenerateSets implements GeneralProcess {
    public final static String ALL_SETS = "all_sets";
    public final static String GET_SET = "get_set";
    public final static String GET_THE_LONGEST_SET ="Get the longest set";
    
    private static final Logger LOG = LoggerFactory.getLogger(GenerateSets.class);
    private List<Domino> dominoList;
    private DataAccessService dataAccessService = DataAccessService.getInstance();;

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException, SQLException {
        String getAllSets = request.getParameter(GET_SET);
        Object holderForDominoList = request.getSession().getAttribute(GetChain.CHAIN);
        List<Chain> sets = null;
        if (holderForDominoList != null) {
            dominoList = (List<Domino>) holderForDominoList;

            
            LOG.debug("Get The Longest {}", getAllSets);
            if (getAllSets != null && getAllSets.equals(GET_THE_LONGEST_SET)) {
                sets = getLongestSet();
            } else {
                sets = getAllSets();
            }
            LOG.debug("sets = {} ", sets);
            
            dataAccessService.createSets(insertChain(), sets);
            request.getSession().setAttribute(ALL_SETS, sets);
            request.getSession().setAttribute(HISTORY_SETS, getAllHistoryList());
        }
        Commands.forward("/GenerateSets.jsp", request, response);
    }
    
    private int insertChain() throws DataBaseException {
        Date date = new Date();
        String chain = dominoList.toString();
        return dataAccessService.createChain(chain, date);
    }
    
    private List<HistoryObject> getAllHistoryList() throws DataBaseException {
        List<HistoryObject> historyObjectList = dataAccessService.getInstance().getAllHistory();
        LOG.debug("historyObjectList {} ", historyObjectList);
        return historyObjectList;
    }
    
    private List<Chain> getLongestSet() {
        List<Chain> sets = Collections.singletonList(Collections.max(MenegerDomino.generateChains(dominoList), new Comparator<Chain>() {
                    @Override
                    public int compare(Chain o1, Chain o2) {
                        int result;
                        if (o1.size() > o2.size()) {
                            result = 1;
                        }else if(o1.size() < o2.size()){
                            result = -1;
                        } else {
                            result = 0;
                        }
                        return result;
                    }
                }
        ));
        return sets;
    }
    
    private List<Chain> getAllSets() {
       return MenegerDomino.generateChains(dominoList);
    }
} 
