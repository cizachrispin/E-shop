/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppermarket;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lukogo
 */

public class BrandController implements Initializable {
    
    
    
    @FXML
    private TextField text_id;

    @FXML
    private TextField filterField;


    @FXML
    private TableColumn<brands, Integer> col_id;

    @FXML
    private TableView<brands> table_brands;
    

    @FXML
    private TextField text_brand;

    @FXML
    private TableColumn<brands, String> col_brand;

    @FXML
    private TableColumn<brands, String> col_status;

    @FXML
    private ComboBox<String> comb;
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label label;
    
    
    ObservableList<brands> listM;
    ObservableList<brands> dataList;
    
    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    
    public void Add_brands(){
        
        conn = Dbconnection.ConnectDb();
        String sql = "insert into brands (brand,status) value(?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, text_brand.getText());
            pst.setString(2, comb.getValue());
            
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "brand Add Succes");
            
            
            //Update authomaticaly the table//////////
            
            UpdateTable();
            search_brand();
            
        } catch (Exception e){
            
        }
    }
    
    //////////////methode select brand/////////////
    @FXML
            
    void getSelected(MouseEvent event){
        index = table_brands.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            
            return;
        
        }
        text_id.setText(col_id.getCellData(index).toString());
        text_brand.setText(col_brand.getCellData(index).toString());
        comb.setValue(col_status.getCellData(index).toString());
    }
    
    //////edit 
     public void Edit(){
        
        try{
            conn = Dbconnection.ConnectDb();
            String value1 = text_id.getText();
            String value2 = text_brand.getText();
            String value3 = comb.getValue();
            
            String sql = "update brands set id = '"+value1+"' ,brand = '"+value2+"',status = '"+value3+"' where id ='"+value1+"' ";
            
            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "update");
            
            //Update authomaticaly the table//////////
            UpdateTable();
            search_brand();
        
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
      @FXML
    void textStatus(ActionEvent event){
        String s = comb.getSelectionModel().getSelectedItem().toString();
        label.setText(s);
        
    }
    
     ///////delete from categories/////
    
    public void Delete(){
        
        conn = Dbconnection.ConnectDb();
        String sql = "delete from brands where id = ?";
        
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, text_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            
            //Update authomaticaly the table//////////
            UpdateTable();
            search_brand();
            
        } catch (Exception e){
            
            JOptionPane.showMessageDialog(null, e);
        }
  
    }
    
    //Update authomaticaly the table//////////
    
    public void UpdateTable(){
        
        //get data from database
       col_id.setCellValueFactory(new PropertyValueFactory<brands,Integer>("id"));
       col_brand.setCellValueFactory(new PropertyValueFactory<brands,String>("brand"));
       col_status.setCellValueFactory(new PropertyValueFactory<brands,String>("status"));
       
       listM = Dbconnection.getDatabrands();
       table_brands.setItems(listM);
    }
    
      ////////////////// Filter categories/////////////////////
    
     @FXML
    void search_brand() {          
       col_id.setCellValueFactory(new PropertyValueFactory<brands,Integer>("id"));
       col_brand.setCellValueFactory(new PropertyValueFactory<brands,String>("brand"));
       col_status.setCellValueFactory(new PropertyValueFactory<brands,String>("status"));
        
       dataList = Dbconnection.getDatabrands();
       table_brands.setItems(dataList);
       FilteredList<brands> filteredData = new FilteredList<>(dataList, b -> true);  
       filterField.textProperty().addListener((observable, oldValue, newValue) -> {
       filteredData.setPredicate(brand -> {
       if (newValue == null || newValue.isEmpty()) {
          return true;
       }    
       String lowerCaseFilter = newValue.toLowerCase();
    
       if (brand.getBrand().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
       return true; // Filter matches category
    
       }
       else if (String.valueOf(brand.getStatus()).indexOf(lowerCaseFilter)!=-1)
         return true;// Filter matches status
                                
        else  
          return false; // Does not match.
       
        });
       }); 
       
       SortedList<brands> sortedData = new SortedList<>(filteredData);  
       sortedData.comparatorProperty().bind(table_brands.comparatorProperty());  
       table_brands.setItems(sortedData); 
       
       }
    
     @FXML
    private void btncat(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("categories.fxml"));
        rootPane.getChildren().setAll(pane);        
    }
    
    
     @FXML
    private void btnproduct(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("products.fxml"));
        rootPane.getChildren().setAll(pane);        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       ObservableList<String> list = FXCollections.observableArrayList("Active","DeActive");
       comb.setItems(list);
       
       UpdateTable();
       search_brand();
    }    
    
}
