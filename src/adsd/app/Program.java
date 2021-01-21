package adsd.app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/HomeScreen.fxml"));
        Parent startScherm = loader.load();
        primaryStage.setTitle("TravelYroute OV App");
        startScherm.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(new Scene(startScherm));
        primaryStage.getIcons().add(new Image("https://i.ibb.co/zxym2f8/train.png"));
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

        launch(args);

    }
}
