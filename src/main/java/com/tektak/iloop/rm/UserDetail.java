package com.tektak.iloop.rm;

import com.tektak.iloop.rm.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tektak on 7/2/14.
 */
public class UserDetail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        ResultSet resultSet = userDAO.fetchUser();
        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                System.out.println(name);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/userDetails.jsp");
        dispatcher.forward(request, response);
    }

}
