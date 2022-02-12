
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
public class CategoriesController implements Initializable {

    /**
     * Initializes the controller class.
     */
       @FXML
    private TableColumn<categories, Integer> col_id;

    @FXML
    private TableColumn<categories, String> col_cat;

    @FXML
    private TableView<categories> table_categories;

    @FXML
    private TableColumn<categories, String> col_status;

    @FXML
    private Label status;
    
     @FXML
    private TextField text_cat;
     
     @FXML
    private TextField text_id;

    
    @FXML
    private ComboBox<String> comb;
     
    @FXML
    private Label label;
    
    @FXML
    private TextField filterField;
    
    @FXML
    private AnchorPane rootPane;
    
    
    ObservableList<categories> listM;
    ObservableList<categories> dataList;
    
    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    public void Add_categories(){
        
        conn = Dbconnection.ConnectDb();
        String sql = "insert into categories (category,status) value(?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, text_cat.getText());
            pst.setString(2, comb.getValue());
            
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "category Add Succes");
            
            
            //Update authomaticaly the table//////////
            
            UpdateTable();
            search_category();
            
        } catch (Exception e){
            
        }
    }
    
    
    //////////////methode select categories/////////////
    @FXML
            
    void getSelected(MouseEvent event){
        index = table_categories.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            
            return;
        
        }
        text_id.setText(col_id.getCellData(index).toString());
        text_cat.setText(col_cat.getCellData(index).toString());
        comb.setValue(col_status.getCellData(index).toString());
    }
    
    public void Edit(){
        
        try{
            conn = Dbconnection.ConnectDb();
            String value1 = text_id.getText();
            String value2 = text_cat.getText();
            String value3 = comb.getValue();
            
            String sql = "update categories set id = '"+value1+"' ,category = '"+value2+"',status = '"+value3+"' where id ='"+value1+"' ";
            
            pst = conn.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "update");
            
            //Update authomaticaly the table//////////
            UpdateTable();
            search_category();
        
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     @FXML
    void textStatus(ActionEvent event){
        String s = comb.getSelectionModel().getSelectedItem().toString();
        label.setText(s);
        
    }
    
    @FXML
    private void btnbrand(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("brand.fxml"));
        rootPane.getChildren().setAll(pane);        
    }
    @FXML
    private void btncategory(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("categories.fxml"));
        rootPane.getChildren().setAll(pane);        
    }
    
    @FXML
    private void btnproduct(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("products.fxml"));
        rootPane.getChildren().setAll(pane);        
    }
    
    
    ///////delete from categories/////
    
    public void Delete(){
        
        conn = Dbconnection.ConnectDb();
        String sql = "delete from categories where id = ?";
        
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, text_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            
            //Update authomaticaly the table//////////
            UpdateTable();
            search_category();
            
        } catch (Exception e){
            
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        
    }
    
    //Update authomaticaly the table//////////
    
    public void UpdateTable(){
        
        //get data from database
       col_id.setCellValueFactory(new PropertyValueFactory<categories,Integer>("id"));
       col_cat.setCellValueFactory(new PropertyValueFactory<categories,String>("categories"));
       col_status.setCellValueFactory(new PropertyValueFactory<categories,String>("status"));
       
       listM = Dbconnection.getDatacategories();
       table_categories.setItems(listM);
    }
    
    
    ////////////////// Filter categories/////////////////////
    
     @FXML
    void search_category() {          
       col_id.setCellValueFactory(new PropertyValueFactory<categories,Integer>("id"));
       col_cat.setCellValueFactory(new PropertyValueFactory<categories,String>("categories"));
       col_status.setCellValueFactory(new PropertyValueFactory<categories,String>("status"));
        
       dataList = Dbconnection.getDatacategories();
       table_categories.setItems(dataList);
       FilteredList<categories> filteredData = new FilteredList<>(dataList, b -> true);  
       filterField.textProperty().addListener((observable, oldValue, newValue) -> {
       filteredData.setPredicate(category -> {
       if (newValue == null || newValue.isEmpty()) {
          return true;
       }    
       String lowerCaseFilter = newValue.toLowerCase();
    
       if (category.getCategories().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
       return true; // Filter matches category
    
       }
       else if (String.valueOf(category.getStatus()).indexOf(lowerCaseFilter)!=-1)
         return true;// Filter matches status
                                
        else  
          return false; // Does not match.
       
        });
       }); 
       
       SortedList<categories> sortedData = new SortedList<>(filteredData);  
       sortedData.comparatorProperty().bind(table_categories.comparatorProperty());  
       table_categories.setItems(sortedData); 
       
       }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       ObservableList<String> list = FXCollections.observableArrayList("Active","DeActive");
       comb.setItems(list);
       
       UpdateTable();
       search_category();
        
    }    
    
}
