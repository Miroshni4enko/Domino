package com.vimi.controller;

import com.vimi.exception.DataBaseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by vymi on 17.10.2017.
 */
public interface GeneralProcess {

    void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException, SQLException;

}
