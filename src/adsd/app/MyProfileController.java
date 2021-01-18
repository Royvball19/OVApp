package adsd.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MyProfileController
{
    ResourceBundle rb = ResourceBundle.getBundle("lang");

    @FXML ToolBar myToolBar;

    @FXML Label userNameInfo;
    @FXML Label nameInfo;
    @FXML Label ageInfo;
    @FXML Label residenceInfo;

    private String lang;
    private String country;



    private DataHandler dataHandler;


    public void initialize() throws IOException
    {
        dataHandler = new DataHandler();
        dataHandler.readFromExternalData();

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));

        try
        {
            File myObj = new File("currentuser.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                String data = myReader.nextLine();
                userNameInfo.setText(dataHandler.getProfile(Integer.parseInt((data))).getUserName());
                nameInfo.setText(dataHandler.getProfile(Integer.parseInt((data))).getFirstName() + " " + dataHandler.getProfile(Integer.parseInt((data))).getLastName());
                ageInfo.setText(String.valueOf(dataHandler.getProfile(Integer.parseInt((data))).getAge()));
                residenceInfo.setText(dataHandler.getProfile(Integer.parseInt((data))).getResidence());
            }
            myReader.close();
        } catch (FileNotFoundException e)
        {
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

    public void showMyFavoriteTrips(ActionEvent event) throws IOException
    {

        //todo fix if else if not logged in
        Parent homeScreenParent3 = FXMLLoader.load(getClass().getResource("fxml/MyFavoriteTrips.fxml"));
        Scene myFavoriteTrips = new Scene(homeScreenParent3);

        myFavoriteTrips.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        // This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(myFavoriteTrips);
        window.show();
    }

    public void logOut(ActionEvent event) throws IOException
    {
        FileWriter myWriter = new FileWriter("currentuser.txt");
        myWriter.write("");
        myWriter.close();

        Parent tripInfoParent = FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
        Scene routeInfoScene = new Scene(tripInfoParent);

        // Gets stage information
        Stage window = (Stage) myToolBar.getScene().getWindow();

        routeInfoScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        // Set scene to go back
        window.setScene(routeInfoScene);
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

    public void changeLangNed(ActionEvent event) throws IOException
    {

        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "nl");
        lines.set(1, "NL");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too

        initialize();

    }
}