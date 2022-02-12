/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppermarket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author lukogo
 */
public class ProductsController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    
     @FXML
    private void btncategory2(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("categories.fxml"));
        rootPane.getChildren().setAll(pane);        
    }
    
     @FXML
    private void btnbrand(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("brand.fxml"));
        rootPane.getChildren().setAll(pane);        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
