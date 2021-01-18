package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MyFavoriteTripsController
{

    ResourceBundle rb = ResourceBundle.getBundle("lang");

    DataHandler dataHandler;
    @FXML TableView<Trip> favTrips;
    @FXML TableColumn<Trip, String> favTripsFromCollum;
    @FXML TableColumn<Trip, String> favTripsToCollum;
    @FXML ToolBar myToolBar;
    @FXML MenuButton myLangButton;
    @FXML Button removeFavTripButton;
    @FXML Button planFavTripButton;
    @FXML Label noFavTripSelected;

    int currentUser;

    private String lang;
    private String country;



    public void initialize() throws IOException
    {

        favTrips.getItems().clear();

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));

        ResourceBundle rb = ResourceBundle.getBundle("lang");

        noFavTripSelected.setText(rb.getString("MFTnoFavSelect"));
        noFavTripSelected.setVisible(false);

        favTripsFromCollum.setText(rb.getString("MFTfavTripFrom"));
        favTripsToCollum.setText(rb.getString("MFTfavTripTo"));
        removeFavTripButton.setText(rb.getString("MFTdeleteFavTrip"));
        planFavTripButton.setText(rb.getString("MFTplanFavTrip"));

        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();


        favTripsFromCollum.setCellValueFactory( new PropertyValueFactory<>("locationFrom"));
        favTripsToCollum.setCellValueFactory( new PropertyValueFactory<>("locationTo"));

        try {
            File myObj = new File("currentuser.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < dataHandler.getProfile(Integer.parseInt(data)).myTripList().size(); i++)
                {
                    favTrips.getItems().add(dataHandler.getProfile(Integer.parseInt(data)).myTripList().get(i));
                }
                dataHandler.writeToExternalData();

                currentUser = Integer.parseInt(data);
            }
            myReader.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void showHomeScreen(ActionEvent event) throws IOException
    {
        Parent tripInfoParent = FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
        Scene routeInfoScene = new Scene(tripInfoParent);

        // Gets stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        routeInfoScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // Set scene to go back
        window.setScene(routeInfoScene);
        window.show();
    }

    public void showMyProfile(ActionEvent event) throws IOException
    {
        File myObj = new File("currentuser.txt");
        if(myObj.length() == 0)
        {
            Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("fxml/LoginScreen.fxml"));
            Scene myProfileScene = new Scene(homeScreenParent2);

            myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(myProfileScene);
            window.show();
        } else
        {
            Parent homeScreenParent2 = FXMLLoader.load(getClass().getResource("fxml/MyProfile.fxml"));
            Scene myProfileScene = new Scene(homeScreenParent2);

            myProfileScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(myProfileScene);
            window.show();
        }
    }

    public void removeFavTripButton(ActionEvent event) throws IOException
    {
        dataHandler.getProfile(currentUser).removeTrip(favTrips.getSelectionModel().getSelectedIndex());
        favTrips.getItems().remove(favTrips.getSelectionModel().getSelectedIndex());
        dataHandler.writeToExternalData();
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
    public void planFavTripButton(ActionEvent event) throws IOException{


        if (favTrips.getSelectionModel().getSelectedIndex() > -1)
        {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/HomeScreen.fxml"));
            Parent homeScreenParent = loader.load();

            Scene homeScreenScene = new Scene(homeScreenParent);
            // this loads the correct text into labels
            homeScreenScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

            HomeScreenController homeScreenController = loader.getController();
            homeScreenController.planFavTrip(favTrips.getSelectionModel().getSelectedItem().getLocationFrom(),
                    favTrips.getSelectionModel().getSelectedItem().getLocationTo());


            // This line gets the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(homeScreenScene);
            window.show();
        }
        else {
            noFavTripSelected.setVisible(true);
        }


    }
}
