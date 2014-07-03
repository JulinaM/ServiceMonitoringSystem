package com.tektak.iLoop.Rm.view.loginSystem;

import com.tektak.iLoop.Rm.view.loggingSystem.log.Log;
import com.tektak.iLoop.Rm.view.loggingSystem.log.LogFactory;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.connection.MySqlConnection;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/2/14.
 */
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    /**
     * Authenthicate the email and password with database record
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String uType=request.getParameter("uType");
        if(email==null||password==null||uType==null){
            RequestDispatcher dispatcher= request.getRequestDispatcher("/loginSystem/login.jsp");
            dispatcher.forward(request,response);
        }else{
            MySqlConnection mySqlConnection = new MySqlConnection();
            mySqlConnection.setDatabaseName("anil");
            mySqlConnection.setUsername("root");
            mySqlConnection.setPassword("tektak");
            //mySqlConnection.setUrl("jdbc:mysql://localhost:3306/");
            mySqlConnection.setUrl("jdbc:mysql://10.0.3.107:3306/");
            mySqlConnection.setDriver("com.mysql.jdbc.Driver");



            try {
                MySql mySql = new MySql();
                mySql.setConnection(mySqlConnection);
                mySql.InitConnection();
                MySqlQuery mySqlQuery=new MySqlQuery(mySql,"select * from student");

                System.out.println("-->>"+mySqlQuery.getPreparedStatement());
                ResultSet resultSet=mySqlQuery.Drl();
                ResultSetMetaData rsMetaData = resultSet.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount();
                while(resultSet.next()){
                    for(int i=1;i<numberOfColumns+1;i++)
                        System.out.print("  " + resultSet.getString(i));
                        System.out.println();
                }



                String SQLQuery1="CREATE TABLE IF NOT EXISTS UserActivityLog( logId int(11) Primary key auto_increment ,UId int(11) not null, Activity varchar(255), Timestamp timestamp );";

                //MySqlQuery mySqlQuery1=new MySqlQuery(mySql1,"insert into student (name,address,sex)  values ('ram','nepal','m')");
                MySqlQuery mySqlQuery1=new MySqlQuery(mySql,SQLQuery1);
                mySqlQuery1.Dml();

                LogFactory logFactory=new LogFactory();
                Log log1=logFactory.getLog("Password");
                PrintWriter ps=response.getWriter();
                ps.println(log1.generateLog());

                java.util.Date date= new java.util.Date();
                Timestamp timestamp=new Timestamp(date.getTime());

                String SQLQuery2="insert into UserActivityLog (UId,Activity,timestamp)  values ('120','"+log1.generateLog()+"','"+timestamp+"')";

                MySqlQuery mySqlQuery2=new MySqlQuery(mySql,SQLQuery2);
                mySqlQuery2.Dml();

                mySql.CloseConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            } catch (SQLException e){

            }catch (RmodelException.CommonException e){

            }

            System.out.println("email:"+email+" password:"+password+"    User Type:"+uType);
            RequestDispatcher dispatcher= request.getRequestDispatcher("/loginSystem/home.jsp");
            request.setAttribute("email",email);
            request.setAttribute("password",password);
            request.setAttribute("uType",uType);
            dispatcher.forward(request,response);
        }

    }
}
