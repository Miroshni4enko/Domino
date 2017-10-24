package com.vimi.controler.processor;

import com.vimi.controler.Commands;
import com.vimi.controler.GeneralProcess;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Chain;
import com.vimi.model.Domino;
import com.vimi.model.DominoPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Слава on 25.10.2017.
 */
public class GetChain implements GeneralProcess {

    public final static String COUNT_OF_DOMINOES = "count_of_dominoes";
    public final static String CHAIN = "chain";

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        Integer countOfDominoes = Integer.valueOf(request.getParameter(COUNT_OF_DOMINOES));
        List<Domino> dominoList;
        if (countOfDominoes == null) {
            dominoList = DominoPool.getDominoesWithRandomSize();
        } else {
            dominoList = DominoPool.getDominoesWithFixedSize(countOfDominoes);
        }
        request.getSession().setAttribute(CHAIN, dominoList);
        Commands.forward("/?action=" + Commands.GET_CHAIN, request, response);
    }
}
