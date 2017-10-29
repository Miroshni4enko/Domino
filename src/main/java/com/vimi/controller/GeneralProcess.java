package com.vimi.controller;

import com.vimi.exception.DataBaseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 */
public interface GeneralProcess {
    void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException, SQLException;

}
