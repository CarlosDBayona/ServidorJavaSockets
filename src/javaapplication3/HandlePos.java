/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import com.mysql.jdbc.Connection;
import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author Carlos
 */
public class HandlePos implements Runnable{
        private Socket socket;
        private int direccionx;
        int direcciony;
        ServerSocket serverSocket;
        JTextArea area;
    public HandlePos(ServerSocket serverSocket,JTextArea area) throws IOException {
        this.serverSocket=serverSocket;
        this.area=area;
        socket = serverSocket.accept();
    }
        
        public void run (){
        
        try {
            
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      // Create a server socket
        System.out.println("Server started at " + new Date() + '\n');
      // Create data input and output streams
      DataInputStream inputFromClient = new DataInputStream(
        socket.getInputStream());
      StringBuilder buffer=new StringBuilder();
      while (true) {
          //String s=inputFromClient.readUTF();
         String Response ;
         
          while ((Response=in.readLine())!=null) {
              buffer.append(Response);
              area.append(Response+"\n");
              
          }
         inputFromClient.close();
        //int direcciony = inputFromClient.readInt();
        //System.out.println(direccionx);
        //System.out.println(direcciony);
        //Sql(direccionx,direcciony);
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
    }

    public int getDireccionx() {
        return direccionx;
    }

    public int getDirecciony() {
        return direcciony;
    }
    public boolean Sql(int x,int y){
        String url = "jdbc:mysql://localhost:3306/Datos";
        String username = "root";
        String password = "";
        try (Connection connection = (Connection) DriverManager.getConnection(url, username, password)) {
            String query = " insert into Datos (x,y)"
        + " values (?, ?)";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1, x);
        preparedStmt.setInt(2, y);
        preparedStmt.execute();
        connection.close();
        System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return true;
    }
}