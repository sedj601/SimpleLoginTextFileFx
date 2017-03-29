/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleloginfx;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author blj0011
 */
public class LoginScreenController implements Initializable
{
    @FXML TextField tfUsername, tfPassword;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    private final HashMap<String, String> usersInfo = new HashMap();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }    
    
    @FXML void handleBTNLogin(ActionEvent event)
    {
        if(tfUsername.getText().length() > 0 && tfPassword.getText().length() > 0)
        {
            
            if(checkUsernameAndPassword(tfUsername.getText(), tfPassword.getText()))
            {                
                try 
                {
                    System.out.println("Login successful!");

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();

                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                System.out.println("Password incorrect!");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sign In Dialog");
                alert.setHeaderText("Login Unsuccessful");
                alert.setContentText("Your username and/or password is incorrect!");
                alert.showAndWait(); 
            }           
        }
    }
    
    private boolean checkUsernameAndPassword(String username, String password)
    {
        File file = new File("usersInfo.txt");
        if(file.exists())
        {
            try {
                Scanner input = new Scanner(file);
                
                while(input.hasNext())
                {
                    
                    String line = input.nextLine();
                    System.out.println(line);
                    String[] parts = line.split("=");
                    if(username.equals(parts[0].trim()) && password.equals(parts[1].trim()))
                    {
                        return true;
                    }
                }
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("file does not exist!");
        }
        
        return false;
    }
}
