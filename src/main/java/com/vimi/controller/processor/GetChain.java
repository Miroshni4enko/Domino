package com.vimi.controller.processor;

import com.vimi.controller.Commands;
import com.vimi.controller.GeneralProcess;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Domino;
import com.vimi.model.DominoPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Слава on 25.10.2017.
 */
public class GetChain implements GeneralProcess {
    public final static String AMOUNT_OF_DOMINOES = "amount_of_dominoes";
    public final static String CHAIN = "chain";
    private static final Logger LOG = LoggerFactory.getLogger(Commands.class);
    

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        String holderForCountOfDominoes = request.getParameter(AMOUNT_OF_DOMINOES);
        List<Domino> dominoList;
        if (holderForCountOfDominoes == null  || holderForCountOfDominoes.isEmpty()) {
            dominoList = DominoPool.getDominoesWithRandomSize();
        } else {
            dominoList = DominoPool.getDominoesWithFixedSize(Integer.valueOf(holderForCountOfDominoes));
        }
        
        request.getSession().setAttribute(CHAIN, dominoList);
        LOG.debug("Get chain from pool = {}" + dominoList);
        Commands.forward("/getChain.jsp", request, response);
    }
}
