package adsd.app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Program extends Application
{

    private static DataHandler datahandler;

    private String lang;
    private String country;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FileWriter myWriter = new FileWriter("currentuser.txt");
        myWriter.write("");
        myWriter.close();

        lang = Files.readAllLines(Paths.get("currentLang.txt")).get(0);
        country = Files.readAllLines(Paths.get("currentLang.txt")).get(1);
        Locale.setDefault(new Locale(lang, country));


        ResourceBundle rb = ResourceBundle.getBundle("lang");
//        Locale.setDefault(new Locale("nl", "NL"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/HomeScreen.fxml"));
        Parent startScherm = loader.load();
        primaryStage.setTitle("TravelYroute OV App");
        startScherm.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(new Scene(startScherm));
        primaryStage.show();
        primaryStage.setResizable(false);



    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
//        System.out.println("Loading driver...");
//        try
//        {
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("Driver loaded!\n");
//        } catch (ClassNotFoundException e)
//        {
//            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
//        }

        // Datahandler import
        datahandler = new DataHandler();
        datahandler.readFromExternalData();

        System.out.println(datahandler.getProfile(0).getFirstName());

        System.out.println(datahandler.getTrip(0).getLocationFrom());

        // Application.launch(GUI.class, args);
        launch(args);


    }

    public void changeLangEng(ActionEvent event) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "en");
        lines.set(1, "US");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too

    }

    public void changeLangNed(ActionEvent event) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("currentLang.txt"));
        lines.set(0, "nl");
        lines.set(1, "NL");
        Files.write(Paths.get("currentLang.txt"), lines); // You can add a charset and other options too

    }
}
