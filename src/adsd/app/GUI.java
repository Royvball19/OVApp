package adsd.app;


import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;

public class GUI extends Application
{
    private Scene start, viewinfo, myprofile;
    Stage primaryStage;
    Scene window;
    DataHandler dataHandler;
    int currentTrip;

    @Override
    public void start(Stage primaryStage) throws Exception {

        dataHandler = new DataHandler();
        dataHandler.readFromJSON();

        ChoiceBox routeOption = new ChoiceBox();


        for (int i = 0; i < dataHandler.getTripList().size(); i++ )
        {
            routeOption.getItems().add(dataHandler.getTrip(i).getLocationFrom() + " -> " + dataHandler.getTrip(i).getLocationTo());
        }



        Label label3 = new Label("Vertrek tijd");
        label3.setStyle("-fx-font-weight: bold");

        Label label4 = new Label("Reis informatie");
        label4.setStyle("-fx-font-weight: bold");




        routeOption.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> System.out.println(currentTrip=Integer.valueOf(newValue.toString())) );

        // Reis informatie
        Label label5 = new Label("Vertrekplaats:        " + dataHandler.getTrip(currentTrip).getLocationFrom());
        Label label6 = new Label("Aankomstplaats:       " + dataHandler.getTrip(currentTrip).getLocationTo());
        Label label7 = new Label("Reistijd:             " + dataHandler.getTrip(currentTrip).getTravelTime());
        Label label8 = new Label("Kost:                 " + dataHandler.getTrip(currentTrip).getPrice());







        Label label9 = new Label("Gegevens:");
        label9.setStyle("-fx-font-weight: bold");
        Label label10 = new Label("Naam:            Roy van Ballegooijen\nLeeftijd:          21\nWoonplaats:  Linschoten");

        ToolBar toolBar = new ToolBar();
        toolBar.setId("toolbar");

        Button button1 = new Button("Mijn profiel");
        toolBar.getItems().add(button1);

        Button button2 = new Button("Mijn favorieten");
        toolBar.getItems().add(button2);


        ChoiceBox choicebox = new ChoiceBox();
        choicebox.getItems().addAll("Welke tijd:", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
        choicebox.getSelectionModel().select(0);

        Button b1 = new Button("Plan uw reis in");

        Button b2 = new Button("Andere route");

        Button b3 = new Button("Toevoegen als favoriet");


        b1.setOnAction(e ->
        {
            primaryStage.setScene(viewinfo);
        });

        b2.setOnAction(e ->
        {
            primaryStage.setScene(start);
        });

        button1.setOnAction(e ->
        {
            primaryStage.setScene(myprofile);
        });

/*        button2.setOnAction(e ->
{
            window.setScene(myfavorites);
        });*/

        GridPane layout1 = new GridPane();
        layout1.setId("layout");
        layout1.setHgap(10);
        layout1.setVgap(12);
        layout1.add(toolBar, 4, 0);
        layout1.add(routeOption, 4, 5);
        layout1.add(label3, 4, 7);
        layout1.add(choicebox, 4, 8);
        layout1.add(b1, 4, 16);
        layout1.setHalignment(label3, HPos.CENTER);
        layout1.setHalignment(routeOption, HPos.CENTER);
        layout1.setHalignment(choicebox, HPos.CENTER);
        layout1.setHalignment(b1, HPos.CENTER);
        start = new Scene(layout1, 265, 400);


        GridPane layout2 = new GridPane();
        layout2.setId("layout2");
        layout2.setHgap(10);
        layout2.setVgap(12);
        layout2.add(label4, 5, 2);
        layout2.add(label5, 5, 4);
        layout2.add(label7, 5, 5);
        layout2.add(label6, 5, 6);
        layout2.add(label8, 5, 7);
        layout2.add(b2, 5, 10);
        layout2.add(b3, 5, 11);
        layout2.setHalignment(label4, HPos.CENTER);
        layout2.setHalignment(b2, HPos.CENTER);
        layout2.setHalignment(b3, HPos.CENTER);
        viewinfo = new Scene(layout2, 265, 400);

        GridPane layout3 = new GridPane();
        layout3.setId("layout3");
        layout3.setHgap(10);
        layout3.setVgap(12);
        layout3.add(label9, 4, 2);
        layout3.add(label10, 4, 3);
        layout3.setHalignment(label9, HPos.CENTER);
        layout3.setHalignment(label10, HPos.LEFT);
        myprofile = new Scene(layout3, 265, 400);


        viewinfo.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        start.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        myprofile.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("OV_App");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setScene(start);

    }
}
