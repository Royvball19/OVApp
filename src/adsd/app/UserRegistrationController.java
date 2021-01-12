package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;


public class UserRegistrationController {

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField age;
    @FXML TextField userName;
    @FXML TextField password;
    @FXML Label registerLabel;
    @FXML Label errorLabel;
    @FXML Label benefitsLabel;
    @FXML Label favBenefitLabel;
    @FXML Label profileBenefitLabel;
    @FXML Label saveTimeBenefitLabel;
    @FXML Text  text;
    @FXML Button register;

    DataHandler dataHandler = new DataHandler();


    public void initialize() throws FileNotFoundException {


        dataHandler.readFromExternalData();

        registerLabel.setText(rb.getString("URregisterlabel"));
        firstName.setPromptText(rb.getString("URfirstName"));
        lastName.setPromptText(rb.getString("URlastName"));
        age.setPromptText(rb.getString("URage"));
        userName.setPromptText(rb.getString("URuserName"));
        password.setPromptText(rb.getString("URpassword"));

        benefitsLabel.setText(rb.getString("URbenefits"));
        favBenefitLabel.setText(rb.getString("URfavBenefits"));
        profileBenefitLabel.setText(rb.getString("URprofileBenefits"));
        saveTimeBenefitLabel.setText(rb.getString("URtimeBenefits"));
        text.setText(rb.getString("URtext"));
        register.setText(rb.getString("URregisterButton"));


//        errorLabel.setText(rb.getString("URerrorLabel"));

    }

public void userRegistration() throws FileNotFoundException, UnsupportedEncodingException {

    if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || age.getText().isEmpty() || userName.getText().isEmpty() || password.getText().isEmpty()) {
        errorLabel.setText(rb.getString("URerrorLabel"));
    } else {

        dataHandler.readFromExternalData();

        Profile p1 = new Profile(dataHandler.getProfileList().size(), userName.getText(), password.getText(), firstName.getText(), lastName.getText(), "0", Integer.parseInt(age.getText()));

        dataHandler.getProfileList().add(p1);

        dataHandler.writeToExternalData();

    }
}

    public void backToHomeScreen(ActionEvent event) throws IOException
    {

        Parent homeScreen = FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
        Scene homeScene = new Scene(homeScreen);

        homeScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(homeScene);
        window.show();
    }

}
