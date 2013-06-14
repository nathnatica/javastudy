package study.derby.demo;
/*
     Derby - WwdEmbedded.java

       Licensed to the Apache Software Foundation (ASF) under one
           or more contributor license agreements.  See the NOTICE file
           distributed with this work for additional information
           regarding copyright ownership.  The ASF licenses this file
           to you under the Apache License, Version 2.0 (the
           "License"); you may not use this file except in compliance
           with the License.  You may obtain a copy of the License at

             http://www.apache.org/licenses/LICENSE-2.0

           Unless required by applicable law or agreed to in writing,
           software distributed under the License is distributed on an
           "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
           KIND, either express or implied.  See the License for the
           specific language governing permissions and limitations
           under the License.

*/
/*
 **  This sample program is described in the Getting Started With Derby Manual
*/
//   ## INITIALIZATION SECTION ##
//  Include the java SQL classes
import java.sql.*;

public class WwdTest
{
    public static void main(String[] args)
   {
   //   ## DEFINE VARIABLES SECTION ##
   // define the driver to use
      String driver = "org.apache.derby.jdbc.EmbeddedDriver";
   // the database name
      String dbName="../../eclipse/DB";
   // define the Derby connection URL to use
      String connectionURL = "jdbc:derby:" + dbName + ";create=false";

      Connection conn = null;
      Statement s;
      ResultSet rs;
      String printLine = "__________________________________________________";

      //   Beginning of JDBC code sections
      //   ## LOAD DRIVER SECTION ##
      try	        {
          /*
          **  Load the Derby driver.
          **     When the embedded Driver is used this action start the Derby engine.
          **  Catch an error and suggest a CLASSPATH problem
           */
          Class.forName(driver);
          System.out.println(driver + " loaded. ");
      } catch(java.lang.ClassNotFoundException e)     {
          System.err.print("ClassNotFoundException: ");
          System.err.println(e.getMessage());
          System.out.println("\n    >>> Please check your CLASSPATH variable   <<<\n");
      }
      //  Beginning of Primary DB access section
      //   ## BOOT DATABASE SECTION ##
     try {
            // Create (if needed) and connect to the database
            conn = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);

            s = conn.createStatement();

            rs = s.executeQuery("select count(*) from airlines");

            System.out.println(printLine);
            while (rs.next())
            {
            	System.out.println(rs.getInt(1));
            }
            System.out.println(printLine);
            rs.close();
            s.close();
            conn.close();
            System.out.println("Closed connection");

            //   ## DATABASE SHUTDOWN SECTION ##
            /*** In embedded mode, an application should shut down Derby.
               Shutdown throws the XJ015 exception to confirm success. ***/
            if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
               boolean gotSQLExc = false;
               try {
                  DriverManager.getConnection("jdbc:derby:;shutdown=true");
               } catch (SQLException se)  {
                  if ( se.getSQLState().equals("XJ015") ) {
                     gotSQLExc = true;
                  }
               }
               if (!gotSQLExc) {
               	  System.out.println("Database did not shut down normally");
               }  else  {
                  System.out.println("Database shut down normally");
               }
            }

         }  catch (Throwable e)  {
            System.out.println(" . . . exception thrown:");
            errorPrint(e);
         }
      }
    
      static void errorPrint(Throwable e) {
         if (e instanceof SQLException)
            SQLExceptionPrint((SQLException)e);
         else {
            System.out.println("A non SQL error occured.");
            e.printStackTrace();
         }
      }  

      static void SQLExceptionPrint(SQLException sqle) {
         while (sqle != null) {
            System.out.println("\n---SQLException Caught---\n");
            System.out.println("SQLState:   " + (sqle).getSQLState());
            System.out.println("Severity: " + (sqle).getErrorCode());
            System.out.println("Message:  " + (sqle).getMessage());
            sqle.printStackTrace();
            sqle = sqle.getNextException();
      }
   }  
}
