package com.stackroute.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class HomeController {
    Connection con = null;
    User user = new User();

    @RequestMapping(value = "/")
    public String userInfo(){
        return "index";
    }

    @RequestMapping(value = "login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
    {
        User user = new User();

        Connection con = null;
        int status;

        user.setUserName(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));

        String username = user.getUserName();
        String password = user.getPassword();

        try{
            //Resister Driver with driver manager service
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");//create connection
            //here User is database name, user is username and root123 is password
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/User","user","root123");
            System.out.println("got connected");
            //create statement object
            Statement st = con.createStatement();

            status = st.executeUpdate("insert into UserInfo(username,password)values('"+username+"','"+password+"')");

            System.out.println("data updated");
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally{
            try{
                con.close();
            }catch(SQLException e){}
        }

        String message = "Welcome to Stackroute " + username;
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("display");
        modelView.addObject("result",message);
        return modelView;
    }
}
