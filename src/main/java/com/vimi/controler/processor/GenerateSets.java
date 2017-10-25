package com.vimi.controler.processor;

import com.vimi.controler.Commands;
import com.vimi.controler.GeneralProcess;
import com.vimi.exception.DataBaseException;
import com.vimi.model.Chain;
import com.vimi.model.Domino;
import com.vimi.model.DominoPool;
import com.vimi.model.SolveDominoes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by vymi1016 on 10/24/2017.
 */
public class GenerateSets implements GeneralProcess {
    public final static String ALL_SETS = "all_sets";
    public final static String GET_SETS = "get_all_sets";
    public final static String CHAIN = "chain";

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        String getAllSets = request.getParameter(GET_SETS);
        List<Domino> dominoList = (List<Domino>) request.getSession().getAttribute(GetChain.CHAIN);
        List<Chain> sets;
        if (getAllSets == null) {
            sets = SolveDominoes.generateChains(dominoList);
        } else {
            sets = SolveDominoes.generateChains(dominoList);;
        }
        request.getSession().setAttribute(ALL_SETS, sets);
        Commands.forward("/GenerateSets.jsp", request, response);
    }
}
