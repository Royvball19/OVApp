package adsd.app;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class MyProfileController
{

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    DataHandler dataHandler;

    @FXML ToolBar myToolBar;
    @FXML Label welcomeText;
    @FXML Label profileNotExist;
    @FXML Button showSelectedProfile;
    @FXML TextField userName;
    @FXML TextField password;
    @FXML Hyperlink noAccount;

    public void initialize() throws FileNotFoundException
    {

        userName.setPromptText(rb.getString("MPuserName"));
        password.setPromptText(rb.getString("MPpassword"));
        noAccount.setText(rb.getString("MPnoAccount"));

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

    }

    public int login(ActionEvent login) throws IOException {

        for (int i = 0; i < dataHandler.getProfileList().size(); i++) {


            if (dataHandler.getProfileList().get(i).getUserName().equals(userName.getText()) && dataHandler.getProfileList().get(i).getPassword().equals(password.getText())) {

                welcomeText.setText(rb.getString("MPwelcomeText") + dataHandler.getProfile(i).getFirstName());

                FileWriter myWriter = new FileWriter("currentuser.txt");
                myWriter.write(String.valueOf(i));
                myWriter.close();
                System.out.println("Successfully wrote to the file.");


                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished( event -> {

                    Parent homeScreenParent = null;

                    try {
                        homeScreenParent = FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene myFavoriteTrips = new Scene(homeScreenParent);
                    myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
                    Stage window = (Stage) myToolBar.getScene().getWindow();

                    window.setScene(myFavoriteTrips);
                    window.show();
                });

                delay.play();

                return i;

            } else {
                profileNotExist.setText(rb.getString("MPnoProfile"));
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
        Parent homeScreenParent = FXMLLoader.load(getClass().getResource("fxml/MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }

    public void toRegistration() throws IOException
    {

        Parent homeScreenParent = FXMLLoader.load(getClass().getResource("fxml/UserRegistration.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent);

        homeScreenParent.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // This line gets the stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();

    }

}
