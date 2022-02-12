  
package suppermarket;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author lukogo
 */
public class SupperMarket extends Application {
    
    @Override
        public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("categories.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.setTitle("SuperMarket");
        //stage.setFullScreen(true);
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("images.png"));
        stage.getIcons().add(icon);
        
        stage.show();
        
        
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
