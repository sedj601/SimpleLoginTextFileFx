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
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;

/**
 *
 * @author blj0011
 */
public class FXMLDocumentController implements Initializable
{    
    @FXML private TextField tfEnterUserName, tfEnterPassword;
    
    @FXML
    private void handleBTNAddUser(ActionEvent event)
    {
        if(tfEnterUserName.getText().length() > 0 && tfEnterPassword.getText().length() > 0)
        {
            if(!doesUsernameExists(tfEnterUserName.getText()))
            {
                addNewUserToFile(tfEnterUserName.getText(), tfEnterPassword.getText());

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Add New User Dialog");
                alert.setHeaderText("New user successfully added");
                alert.setContentText("Username: " + tfEnterUserName.getText() + "\nPassword: " + tfEnterPassword.getText());
                alert.showAndWait(); 

                tfEnterUserName.setText("");
                tfEnterPassword.setText("");
            }
            else
            {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Add New User Dialog");
                alert.setHeaderText("Username already in use");
                alert.setContentText("Use a different username");
                alert.showAndWait(); 

                tfEnterUserName.setText("");
                tfEnterPassword.setText("");
            }
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Add New User Dialog");
            alert.setHeaderText("Error adding new user");
            alert.setContentText("Make sure you have entered a username and a password in the appropriate textfield.");
            alert.showAndWait();            
        }
    }
    
    @FXML
    private void handleBTNGoToLoginScreen(ActionEvent event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Login Screen");
            stage.setScene(scene);
            stage.show();            
            
            ((Node)(event.getSource())).getScene().getWindow().hide(); //close current window after opening new Scene           
        }
        catch (IOException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       File file = new File("usersInfo.txt");
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }        
    
    private void addNewUserToFile(String username, String password)
    {        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usersInfo.txt", true))) 
        {
            bw.write(username + "=" + password);
            bw.newLine();
        } 
        catch (IOException ex) 
        {
            System.out.println(ex);
        }
        
    }
    
    private boolean doesUsernameExists(String username)
    {
        try
        {
            File file = new File("usersInfo.txt");
            Scanner input = new Scanner(file);
            
            while(input.hasNext())
            {
                String line = input.nextLine();
                String[] parts = line.split("=");
                System.out.println(parts[0]);
                if(username.equals(parts[0].trim()))
                {
                    return true;
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
