package adsd.app;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController
{
    // Get resource bundle
    private ResourceBundle rb = ResourceBundle.getBundle("lang");

    // Instance of datahandler
    private DataHandler dataHandler;

    // Attributes FXML
    @FXML private ToolBar myToolBar;
    @FXML private Label profileNotExist;
    @FXML private Label titelLoginLabel;
    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private Hyperlink noAccount;

    // Attributes
    private String lang;
    private String country;

    public void initialize() throws IOException
    {

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));

        ResourceBundle rb = ResourceBundle.getBundle("lang");

        titelLoginLabel.setText(rb.getString("LItitel"));
        userName.setPromptText(rb.getString("MPuserName"));
        password.setPromptText(rb.getString("MPpassword"));
        noAccount.setText(rb.getString("MPnoAccount"));

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

    }

    public int login(ActionEvent login) throws IOException {

        for (int i = 0; i < dataHandler.getProfileList().size(); i++) {


            if (dataHandler.getProfileList().get(i).getUserName().equals(userName.getText()) && dataHandler.getProfileList().get(i).getPassword().equals(password.getText())) {


                FileWriter myWriter = new FileWriter("currentuser.txt");
                myWriter.write(String.valueOf(i));
                myWriter.close();
                System.out.println("Successfully wrote to the file.");

                    Parent parent = null;

                    try {
                        parent = FXMLLoader.load(getClass().getResource("fxml/MyProfile.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene scene = new Scene(parent);
                    scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
                    Stage window = (Stage) myToolBar.getScene().getWindow();

                    window.setScene(scene);
                    window.show();

                return i;

            } else {
                profileNotExist.setText(rb.getString("MPnoProfile"));
                titelLoginLabel.setVisible(false);
            }
        }
        return 0;
    }

    public void showHomeScreen(ActionEvent event) throws IOException
    {
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
        Scene MyProfileScene = new Scene(HomeScreenParent);

        // Gets stage information

        Stage window = (Stage) myToolBar.getScene().getWindow();

        MyProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // Set scene to go back
        window.setScene(MyProfileScene);
        window.show();
    }

    public void showMyFavoriteTrips (ActionEvent event) throws IOException
    {
        File myObj = new File("currentuser.txt");
        if (myObj.length() == 0)
        {
            Parent loginScreenParent = FXMLLoader.load(getClass().getResource("fxml/LoginScreen.fxml"));
            Scene myProfileScene = new Scene(loginScreenParent);

            myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(myProfileScene);
            window.show();
        } else
        {
            Parent favTripParent = FXMLLoader.load(getClass().getResource("fxml/MyFavoriteTrips.fxml"));
            Scene scene = new Scene(favTripParent);

            scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }
    }

    public void toRegistration() throws IOException
    {

        Parent parent = FXMLLoader.load(getClass().getResource("fxml/UserRegistration.fxml"));
        Scene scene = new Scene(parent);

        parent.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // This line gets the stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        window.setScene(scene);
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
