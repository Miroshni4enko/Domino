package com.vimi.controller.processor;

import com.vimi.controller.Commands;
import com.vimi.controller.GeneralProcess;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Domino;
import com.vimi.model.DominoPool;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Слава on 25.10.2017.
 */
public class GetChain implements GeneralProcess {
    public final static String COUNT_OF_DOMINOES = "count_of_dominoes";
    public final static String CHAIN = "chain";
    private static final Logger LOG = Logger.getLogger(Commands.class);
    

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        String holderForCountOfDominoes = request.getParameter(COUNT_OF_DOMINOES);
        List<Domino> dominoList;
        if (holderForCountOfDominoes == null  || holderForCountOfDominoes.isEmpty()) {
            dominoList = DominoPool.getDominoesWithRandomSize();
        } else {
            dominoList = DominoPool.getDominoesWithFixedSize(Integer.valueOf(holderForCountOfDominoes));
        }
        
        request.getSession().setAttribute(CHAIN, dominoList);
        LOG.debug("Get chain from pool :" + dominoList);
        Commands.forward("/GetChain.jsp", request, response);
    }
}
