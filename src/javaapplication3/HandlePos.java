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
         String Response ;
          System.out.println(this.serverSocket.isClosed());
         if (this.serverSocket.isClosed()==true) {
            this.serverSocket=new ServerSocket(10);
        }
          while ((Response=in.readLine())!=null) {
              buffer.append(Response);
              area.append(Response+"\n");
              Response=Response.replace("{", "");
              Response=Response.replace("}", "");
              Response=Response.replaceAll("\\s+","");
              String Arreglo[]=Response.split(",");
              float[] fs=new float[3];
              for (int i = 0; i < Arreglo.length; i++) {
                  String[] SubStri=Arreglo[i].split(":");
                  fs[i]=Float.valueOf(SubStri[1]);
              }
              Edison e=new Edison(fs[0],fs[1],fs[2]);
              Sql(e);
          }
          in.close();
          inputFromClient.close();
      }
     
    }
        
    catch(IOException ex) {
      
    }
    }

    public int getDireccionx() {
        return direccionx;
    }

    public int getDirecciony() {
        return direcciony;
    }
    public boolean Sql(Edison ed){
        String url = "jdbc:mysql://localhost:3306/Edison";
        String username = "root";
        String password = "";
        try (Connection connection = (Connection) DriverManager.getConnection(url, username, password)) {
            String query = " insert into Datos (temp,lux,sound)"
        + " values (?, ?,?)";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setFloat(1, ed.temp);
        preparedStmt.setFloat(2, ed.lux);
        preparedStmt.setFloat(3, ed.sound);
        preparedStmt.execute();
        connection.close();
        System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return true;
    }
}