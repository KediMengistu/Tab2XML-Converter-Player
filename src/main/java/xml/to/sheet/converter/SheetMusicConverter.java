package xml.to.sheet.converter;
import converter.*;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import utility.MusicXMLCreator;
import GUI.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SheetMusicConverter {
	
	private Score score;
	private MusicXMLCreator mxlc;
	private MainViewController mvc;
	private static final String FILENAME = null;
	public SheetMusicConverter(MainViewController mvc, Score score) {
		this.mvc = mvc;
		this.score = score;
	}
	public void update() {
		score = new Score(mvc.mainText.getText());
		mxlc = new MusicXMLCreator(score);
	}
	
	public String getMusicXML() {
		return mxlc.generateMusicXML();
		}
	
	public Score getScore() {
		return this.score;
	}
	@FXML
	public void musicXMLtoSheet() throws IOException {
		
		String xml = this.getMusicXML();
		
		java.io.FileWriter fw = new java.io.FileWriter("my-file.xml");
		fw.write(xml);
		fw.close();
		
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 try {
			 dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          Document doc = db.parse(new File("my-file.xml"));
	          NodeList measurelist = doc.getElementsByTagName("measure");
	          
	          for (int i = 0; i < measurelist.getLength(); i++) {

	              Node node = measurelist.item(i);
	              
	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	            	  
	              }
	              
	          }

		 }
		 catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
		  
		}
}
}
