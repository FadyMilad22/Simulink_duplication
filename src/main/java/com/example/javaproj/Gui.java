package com.example.javaproj;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import java.io.File;

public class Gui extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        browseCreate(primaryStage);




    }


    private void mainProgramCreate(Stage primaryStage ,String fileDirectory) throws NotVaildMDLFileException {


        // Main Scene
        Document doc = null;


            try {

                doc = MDLFile.readFile(fileDirectory);


            } catch (NotVaildMDLFileException | EmptyMDLFileException e ) {

                MDLHandler(primaryStage);
            }

        //      }
        Block [] blocks= MDLFile.assembleBlocks(doc);
        Line[] lines = MDLFile.assembleLines(doc);
        for(int i =0 ;i<blocks.length;i++ ) {
            blocks[i].getCoordinates_Ports();
        }

        BlockPane myBlockPane = new BlockPane(blocks);
        ConnectorPane myConnectors = new ConnectorPane(lines,blocks);
        //   BorderPane border = new BorderPane();

        StackPane Root = new StackPane(myConnectors,myBlockPane);


        Root.setAlignment(Pos.CENTER);
        Root.setBorder(Border.stroke(Color.BLACK));

        // Create Heading Label
        Label headingLabel = new Label("There is the presentation of your MDL file:");

        // Create Buttons
        Button exitButton = new Button("Exit");
        Button rebrowseButton = new Button("Re-browse");

        // Re-browse click listener

        rebrowseButton.setOnAction((e)->{

browseCreate(primaryStage);
        });

        // Exit button click listener
        exitButton.setOnAction(event -> {
            Platform.exit(); // Close the application
        });

        // Create HBox to hold the buttons
        HBox buttonBox = new HBox(exitButton, rebrowseButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);


        // Create VBox to hold all the components
        VBox root = new VBox(10, Root, headingLabel, buttonBox);
        root.setStyle("-fx-padding: 20px");
        root.setAlignment(Pos.CENTER);


        // Create the scene
        Scene scene = new Scene(root, 400, 300);


        primaryStage.setTitle("Simulink Duplicator");
        primaryStage.setScene(scene);
        primaryStage.setMaxHeight(Double.MAX_VALUE);
        primaryStage.setMaxWidth(Double.MAX_VALUE);
        primaryStage.setMaximized(true);
        primaryStage.setMinWidth(1300);
        primaryStage.setMinHeight(600);
        primaryStage.show();



    }

private void browseCreate(Stage primaryStage){

    // 1st Scene

    // Create welcome message label
    Text welcomeLabel = new Text("Welcome to Simulink Duplicate Program");
    welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    // Create directory label
    Label directoryLabel = new Label("Here would your directory be shown");

    // Create browse button
    Button browseButton = new Button("Browse");
    browseButton.setMinWidth(100); // Set a fixed width for the button



    // Create HBox for directory label and browse button
    HBox directoryBox = new HBox(10, directoryLabel, browseButton);
    directoryBox.setAlignment(Pos.CENTER_RIGHT);

    // Create VBox for the browse scene
    VBox browseScene = new VBox(20, welcomeLabel, directoryBox);
    browseScene.setAlignment(Pos.CENTER);
    browseScene.setStyle("-fx-padding: 20px"); // Add padding to the VBox

    // Create Ok button
    Button okButton = new Button("Ok");
    okButton.setFont(Font.font("Arial", 12));

    // Create message label for Ok button
    Label okMessageLabel = new Label("Click Ok after choosing the Correct file to move forward");
    okMessageLabel.setFont(Font.font("Arial", 10));

    // Create HBox for Ok button and message label
    HBox okBox = new HBox(10, okButton, okMessageLabel);
    okBox.setAlignment(Pos.CENTER);

    // warning label
    Label warningLabel = new Label("Please make sure to enter a Valid MDL file to move forward");
    warningLabel.setFont(Font.font("Arial", 10));
    warningLabel.setTextFill(Color.RED);
    // Create VBox for the entire scene
    VBox root1 = new VBox(20, browseScene, okBox);
    root1.setAlignment(Pos.CENTER);
    root1.setStyle("-fx-padding: 20px"); // Add padding to the VBox

    // Create the scene
    Scene scene1 = new Scene(root1, 400, 250);

    primaryStage.setTitle("Simulink Duplicator");
    primaryStage.setMaxHeight(500);
    primaryStage.setMaxWidth(600);
    primaryStage.setScene(scene1);
    primaryStage.show();
    // Create a new stage
    //   Stage primaryStage = new Stage();
    var ref = new Object() {
        File file;
    };
    browseButton.setOnAction((e)->{
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MDL Files","*mdl"));
         ref.file = fc.showOpenDialog(primaryStage);
        directoryLabel.setText(ref.file.toString());

    });

    okButton.setOnAction(event -> {


        if(ref.file ==null){
            root1.getChildren().add(warningLabel);}

else {
            //   }
            try {
                mainProgramCreate(primaryStage, ref.file.toString());
            } catch (NotVaildMDLFileException e) {
                throw new RuntimeException(e);
            }
        }

    });



}

private void MDLHandler(Stage primaryStage){

    browseCreate(primaryStage);
    Label lbl = new Label("Please choose a Valid MDL file");
    lbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    Scene warningS = new Scene(lbl);
    Stage warning = new Stage();

    warning.setHeight(200);
    warning.setWidth(300);
    warning.setResizable(false);
    warning.setTitle("Warning");
    warning.setScene(warningS);
    warning.show();


}

















         public static void main(String[] args)
        {
            System.out.println("Launching App");
            launch(args);
        }

}

class ConnectorPane extends Pane{
    public ConnectorPane(Line lines[],Block blocks[]){
        // Polyline p = new Polyline(null);
        // p.getPoints().addAll(new Double[]{        
        //     780.0 , 200.0,
             
        //     935.0, 217.0 
        //  });
        //  this.getChildren().add(p);
        for(Line l : lines)
        {
            l.getCoordinates();
            DrawLine(l, blocks);
            if(l.BranchesFromLine != null)
            {
                for(Line branch : l.BranchesFromLine)
                {
                    branch.getCoordinates();
                    Block dst = null;
                    for(Block b : blocks)
                    {
                        if(branch.hasDst && b.SID.equals(branch.dstBlock))
                        {
                            dst = b;
                        }
                    }
                    
                    Polyline p =new Polyline(null);
                    //double x = dst.getInputPortsY()[branch.inputPort-1] - l.getEndPointY();

                    p.getPoints().addAll(
                        l.getEndPointX(), l.getEndPointY(),
                        l.getEndPointX(), dst.getInputPortsY()[branch.inputPort-1],
                        dst.getInputPortsX()[branch.inputPort-1],
                        dst.getInputPortsY()[branch.inputPort-1]
                    );
                    this.getChildren().add(p);
                
                    CreateArrowHead(
                        dst.getInputPortsX()[branch.inputPort-1],
                        dst.getInputPortsY()[branch.inputPort-1], dst.mirrorred);
                }
            }


        }
    }
    public void DrawLine(Line l,Block blocks[])
    {
        for(Block b : blocks)
        {
            if(l.hasSrc && b.SID.equals(l.srcBlock))
            {
                l.src = b;
            }
            if(l.hasDst && b.SID.equals(l.dstBlock))
            {
                l.dst = b;
            }
        }
        if(l.hasSrc && l.hasDst && l.Coordinates == null)
        {
            javafx.scene.shape.Line connector = new javafx.scene.shape.Line(
                l.src.getOutputPortsX()[l.outputPort-1],
                l.src.getOutputPortsY()[l.outputPort-1],
                l.dst.getInputPortsX()[l.inputPort-1],
                l.dst.getInputPortsY()[l.inputPort-1]
            );

            CreateArrowHead(
                l.dst.getInputPortsX()[l.inputPort-1],
                l.dst.getInputPortsY()[l.inputPort-1],
                l.dst.mirrorred
            );
        this.getChildren().add(connector);

        }
        else if(l.hasSrc && l.hasDst && l.Coordinates != null)
        {
            Polyline p =new Polyline(null);
                    double x = l.src.getOutputPortsX()[l.outputPort-1];
                    double y = l.src.getOutputPortsY()[l.outputPort-1];
                    p.getPoints().addAll(
                        l.src.getOutputPortsX()[l.outputPort-1],
                        l.src.getOutputPortsY()[l.outputPort-1]
                    );
                    for(int i = 0;i < l.Coordinates.length;i++){
                        p.getPoints().addAll(
                            x+=l.Coordinates[i++],
                            y+=l.Coordinates[i]
                        );
                    }
                    p.getPoints().addAll(
                        l.dst.getInputPortsX()[l.inputPort-1],
                        l.dst.getInputPortsY()[l.inputPort-1]
                    );
                    this.getChildren().add(p);

            CreateArrowHead(
                l.dst.getInputPortsX()[l.inputPort-1],
                l.dst.getInputPortsY()[l.inputPort-1],
                l.dst.mirrorred
            );

        }
        else if(!l.hasDst && l.Coordinates.length == 2)
        {
            javafx.scene.shape.Line connector = new javafx.scene.shape.Line(
                l.src.getOutputPortsX()[l.outputPort-1],
                l.src.getOutputPortsY()[l.outputPort-1],
                l.src.getOutputPortsX()[l.outputPort-1] + (l.Coordinates[0]),
                l.src.getOutputPortsY()[l.outputPort-1] + (l.Coordinates[1])
            );
        this.getChildren().add(connector);

        }
        
    }
    public void CreateArrowHead(double endx, double endy, boolean mirrorred)
    {
        double arrowLength = 8;
        double arrowWidth = 8;
        // Add arrowheads to the line

        double angle = Math.atan2(0, endx) - Math.PI / 2.0;

        // Create the arrowhead polygon
        Polygon arrowhead = new Polygon();
        arrowhead.getPoints().addAll(
            0.0, 0.0,
            -arrowWidth / 2.0, arrowLength,
            arrowWidth / 2.0, arrowLength
        );

        arrowhead.setFill(Color.BLACK);

        // Translate and rotate the arrowhead to the end of the line
    if(mirrorred)
        {
            arrowhead.setRotate(angle * 180 / Math.PI);
            arrowhead.setTranslateX(endx + 4);
            arrowhead.setTranslateY(endy-arrowWidth/2);
        }
    else
    {
        arrowhead.setRotate(3*angle * 180 / Math.PI);
        arrowhead.setTranslateX(endx - 4);
        arrowhead.setTranslateY(endy-arrowWidth/2);
    }
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLUE);
        shadow.setRadius(10); // Set the shadow radius to 10 pixels
        arrowhead.setEffect(shadow);
        this.getChildren().add(arrowhead);
    }
}

class BlockPane extends Pane
{
    
     public BlockPane(Block[] blocks){
        for(Block b :blocks){
        
        Rectangle r= new Rectangle(b.getX(), b.getY(),b.getWidth(),b.getHeight());
         Tooltip tooltip = new Tooltip("This is all the attributes of the this Block : \n" +b.toString());
         Tooltip.install(r, tooltip);

         r.setFill(Color.WHITE);
         r.setStroke(Color.BLACK);
         DropShadow shadow = new DropShadow();
         shadow.setColor(Color.BLUE);
         shadow.setRadius(10); // Set the shadow radius to 10 pixels
         r.setEffect(shadow);
         this.getChildren().add(r);

         Text r_txt = new Text(b.getX(), b.getY()+b.getHeight()+10, b.name);
         r_txt.setFill(Color.BLACK);
         r_txt.setFont(Font.font("Arial", FontWeight.THIN, 10));
         this.getChildren().add(r_txt);

         for(int i =0 ;i<b.properties.length;i++){

            if(b.properties[i][0].equalsIgnoreCase("Inputs")){
                for(int j =0;j<b.properties[i][1].length();j++) {
                    Text plus = new Text(b.getX() + 2,b.getInputPortsY()[j] + 3 ,b.properties[i][1].charAt(j)+"");
                    plus.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    plus.setFill(Color.BLACK);
                    this.getChildren().add(plus);}}


      } }}}











































