package adsd.app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class UserRegistrationController {

    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField age;
    @FXML
    TextField userName;
    @FXML
    TextField password;
    @FXML
    Label errorLabel;

    DataHandler dataHandler = new DataHandler();

public void userRegistration() throws FileNotFoundException, UnsupportedEncodingException {

    if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || age.getText().isEmpty() || userName.getText().isEmpty() || password.getText().isEmpty()) {
        errorLabel.setText("Vul alles in a.u.b.");
    } else {

        dataHandler.readFromExternalData();

        Profile p1 = new Profile(dataHandler.getProfileList().size(), userName.getText(), password.getText(), firstName.getText(), lastName.getText(), "0", Integer.parseInt(age.getText()));

        dataHandler.getProfileList().add(p1);

        dataHandler.writeToExternalData();

    }

}

}
