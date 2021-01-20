package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class UserRegistrationController {

    private ResourceBundle rb = ResourceBundle.getBundle("lang");


    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField age;
    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private Label registerLabel;
    @FXML private Label errorLabel;
    @FXML private Label benefitsLabel;
    @FXML private Label favBenefitLabel;
    @FXML private Label profileBenefitLabel;
    @FXML private Label saveTimeBenefitLabel;
    @FXML private Text  text;
    @FXML private Button register;

    private DataHandler dataHandler = new DataHandler();

    private String lang;
    private String country;


    public void initialize() throws IOException {

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));

        ResourceBundle rb = ResourceBundle.getBundle("lang");


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

    public void changeLangEng(ActionEvent event) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "en");
        lines.set(1, "US");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too


        initialize();

    }

    public void changeLangNed(ActionEvent event) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "nl");
        lines.set(1, "NL");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too

        initialize();

    }
}
