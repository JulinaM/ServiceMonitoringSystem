package com.tektak.iloop.rm;

import com.tektak.iloop.rm.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by tektak on 7/2/14.
 */
public class UserDetail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        ResultSet resultSet = userDAO.fetchUser();
        ArrayList<String> dataList = new ArrayList<String>();
        try {
            while (resultSet.next()) {
                ResultSetMetaData rs = resultSet.getMetaData();
                int count = rs.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    dataList.add(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("userDetails", dataList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/userDetails.jsp");
        dispatcher.forward(request, response);
    }

}
