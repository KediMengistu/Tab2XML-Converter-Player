package GUI;


import java.io.IOException;

//import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//
//import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import xml.to.sheet.converter.ListOfMeasureAndNote;
//import javafx.scene.paint.*;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import javafx.stage.Window;
import xml.to.sheet.converter.POJOClasses.ScorePartwise2;
import xml.to.sheet.converter.POJOClasses.XmlToJava;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import converter.Converter;
/*
Sample tab
|-----------0-----|-0---------------|
|---------0---0---|-0---------------|
|-------1-------1-|-1---------------|
|-----2-----------|-2---------------|
|---2-------------|-2---------------|
|-0---------------|-0---------------|

*/

public class PreviewMXLController {

	
//	@FXML public Canvas canvas;
	@FXML TextField gotoMeasureField;
	@FXML Button gotoMeasureButton;
	@FXML Button savePDF;
	private GraphicsContext gc;
	public FXMLLoader loader;
	
	@FXML
	public void savePDF() {
	}
	
	@FXML
	public void handleGotoMeasure() {
	}
	
	

//	public void drawLines() {
//		gc = canvas.getGraphicsContext2D();
//        gc.setFill(Color.WHITE);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        System.out.println("color set to white and background rectangle drawn");
//        
//        gc.strokeLine(10, 100, 200, 100);
//        gc.strokeLine(10, 110, 200, 110);
//        gc.strokeLine(10, 120, 200, 120);
//        gc.strokeLine(10, 130, 200, 130);
//        gc.strokeLine(10, 140, 200, 140);
//        System.out.println("drawn lines");
//	}
//	
//	public void drawTestRectangle() {
//		gc = canvas.getGraphicsContext2D();
//        gc.setFill(Color.BLACK);
//        System.out.println("color set to black");
//        gc.fillRect(50, 50, 100, 100);
//        System.out.println("draw rectangle");
//	}
//	// paint the canvas
//	public void paint(GraphicsContext g) {
//		// set color to red
//		//g.setColor(Color.BLACK);
//		g.setFill(Color.ALICEBLUE);
//
//		// set Font
//		g.setFont(new Font("Bold", 1));
//		g.lineTo(100, 100);
//
//	}
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//	}
	
    private MainViewController mvc;
	@FXML 
    private Pane pane;
	
	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
    }
	
	//Draws the Music lines based on the instrument and adds them to the GUI
    public void instrumentMusicLines(String instrument, double y) {	
     	if (instrument.equalsIgnoreCase("guitar")) {
     		int i = 1;
     		while (i <= 6) {
             	DrawLine dl = new DrawLine(0.0, y, this.pane.getMaxWidth(), y);
            	pane.getChildren().add(dl.getLine());
            	i++;
            	y += 13;
        	}
     	}
     	else if (instrument.equalsIgnoreCase("drumset")) {
     		int i = 1;
     		while (i <= 5) {
             	DrawLine dl = new DrawLine(0.0, y, this.pane.getMaxWidth(), y);
            	pane.getChildren().add(dl.getLine());
            	i++;
            	y += 13;
        	}
     	}
     	else if (instrument.equalsIgnoreCase("bass")) {
     		int i = 1;
     		while (i <= 4) {
             	DrawLine dl = new DrawLine(0.0, y, this.pane.getMaxWidth(), y);
            	pane.getChildren().add(dl.getLine());
            	i++;
            	y += 13;
        	}
     	}
 	}	

    //TAB = guitar; (II) = bass and drum;
    public void drawClef(String symbol, double x, double y) {
        for (int i = 0; i < symbol.length(); i++, y += 22) {
            //Get the letter
            Text t = new Text(x, y, symbol.substring(i, i+1));
            t.setFont(Font.font("impact", 24));
            pane.getChildren().add(t);
        }
    }

    //Change the bar line length depending on the instrument
    private void barLines(double x, double y, String instrument) {
    	if (instrument.equalsIgnoreCase("Guitar")) {
    		DrawLine middleBar = new DrawLine(x, y, x, y + 64);
        	DrawLine endBar = new DrawLine(x + 470, y, x + 470, y + 64);
        	pane.getChildren().add(middleBar.getLine());
          	pane.getChildren().add(endBar.getLine());
    	}
    	else if (instrument.equalsIgnoreCase("Drumset")) {
    		DrawLine middleBar = new DrawLine(x, y, x, y + 52);
        	DrawLine endBar = new DrawLine(x + 470, y, x + 470, y + 52);
        	pane.getChildren().add(middleBar.getLine());
          	pane.getChildren().add(endBar.getLine());
    	}
    	else if (instrument.equalsIgnoreCase("Bass")) {
    		DrawLine middleBar = new DrawLine(x, y, x, y + 40);
        	DrawLine endBar = new DrawLine(x + 470, y, x + 470, y + 40);
        	pane.getChildren().add(middleBar.getLine());
          	pane.getChildren().add(endBar.getLine());
    	}
    }

    //Update the GUI
    public void update() throws IOException { 	
    	
    	ScorePartwise2 sc;
		try {
			sc = XmlToJava.unmarshal(mvc.converter.getMusicXML(), ScorePartwise2.class);
			 int numMeasures = ListOfMeasureAndNote.getlistOfMeasures(sc).size();
		     String instName = sc.getPartlist().getScorepart().get(0).getPartname();
		     String cleff = sc.getListOfParts().get(0).getListOfMeasures().get(0).getAttributes().getClef().getSign();
		   //Draw the Music lines on the GUI
		      	int y = 0;
		      	double limit = Math.ceil(numMeasures/2);
		    	for (int i = 1; i <= limit; i++) {
		      		instrumentMusicLines(instName, y);
		      		//Draw TAB
		        	drawClef(cleff, 6, 20+y);
		        	//Draw Bar lines
		        	barLines(450, y, instName);
		      		y += 120;
		      	}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  

    }

}
