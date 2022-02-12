
package suppermarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author lukogo
 */
public class Dbconnection {
    Connection conn = null;
    
    //your database url
    public static Connection ConnectDb(){
       try{
           Class.forName("com.mysql.jdbc.Driver");
           Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/supermarket","root","");
           //JOptionPane.showMessageDialog(null,"ConnectionEstablished");
           return conn;
           
       } catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           return null;
       }  
        
    }
    
    
    /////categories//////////////
    
    public static ObservableList<categories> getDatacategories(){ 
        
        Connection conn = ConnectDb();
        
        ObservableList<categories> list = FXCollections.observableArrayList();
        
        try{
            PreparedStatement ps = conn.prepareStatement("select * from categories ");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                
                list.add(new categories(Integer.parseInt(rs.getString("id")), rs.getString("category"),rs.getString("status")));                                  
               
            
            }
            
        }
        catch(Exception e){
        }
        return list; 
    }
    
    /////brands//////////////
    
    public static ObservableList<brands> getDatabrands(){ 
        
        Connection conn = ConnectDb();
        
        ObservableList<brands> list = FXCollections.observableArrayList();
        
        try{
            PreparedStatement ps = conn.prepareStatement("select * from brands ");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                
                list.add(new brands(Integer.parseInt(rs.getString("id")), rs.getString("brand"),rs.getString("status")));                                  
               
            
            }
            
        }
        catch(Exception e){
        }
        return list; 
    }
    
}
