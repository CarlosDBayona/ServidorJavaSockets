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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
      //output stream
      DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
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
              float[] fs=new float[4];
              for (int i = 0; i < Arreglo.length; i++) {
                  String[] SubStri=Arreglo[i].split(":");
                  fs[i]=Float.valueOf(SubStri[1]);
              }
              Edison e=new Edison(fs[0],fs[1],fs[2],fs[3]);
              Sql(e);
              int i=LedStatus();
              dataOutputStream.writeBytes(String.valueOf(i));
              i++;
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
        String password = "root";
        try (Connection connection = (Connection) DriverManager.getConnection(url, username, password)) {
            String query = " insert into DatosEdison (celsius,lux,thresh,absdeg)"
        + " values (?, ?,?,?)";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setFloat(1, ed.celcius);
        preparedStmt.setFloat(2, ed.lux);
        preparedStmt.setFloat(3, ed.thresh);
        preparedStmt.setFloat(4, ed.absdeg);
        preparedStmt.execute();
        connection.close();
        System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return true;
    }
    public int LedStatus(){
        int value=0;
        try
    { 
        Class.forName("com.mysql.jdbc.Driver").newInstance();
      String myUrl = "jdbc:mysql://localhost:3306/Edison";
      java.sql.Connection conn = DriverManager.getConnection(myUrl, "root", "root");
      String query = "SELECT estado FROM led order by id desc limit 1";
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);
      while (rs.next())
      {
          value=rs.getInt("estado");
      }
        rs.close();
         st.close();
         conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getCause());
    }
    
        return value;
    }
}