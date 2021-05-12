
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="cmbNazioni"
    private ComboBox<Country> cmbNazioni; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnStatiRaggiungibili"
    private Button btnStatiRaggiungibili; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	int year;
    	
    	try {
    		year = Integer.parseInt(this.txtAnno.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("You must insert a NUMBER between 1816 and 2006");
    		return;
    	}
    	
    	this.model.createGraph(year);
    	this.txtResult.appendText(model.getNoOfVertexANDEdges()+"\n");
    	this.txtResult.appendText("Number of connected components: "+model.getNoOfConnectedComponents()+"\n\n");
    	
    	Map<Country, Integer> map = this.model.getVerticesWithDegree();
    	for(Country c : map.keySet()) 
    		this.txtResult.appendText(c.getStateNme()+": "+map.get(c)+"\n");
    	
    	this.btnStatiRaggiungibili.setDisable(false);
    	this.cmbNazioni.getItems().addAll(map.keySet());
    }
    
    @FXML
    void doStatiRaggiungibili(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Country country = this.cmbNazioni.getValue();
    	if(country == null) {
    		this.txtResult.appendText("You must select a country");
    		return;
    	}
    	
    	//Set<Country> reachableCountries = this.model.reachableCountries1(country);
    	//List<Country> reachableCountries = this.model.reachableCountries2(country);
    	List<Country> reachableCountries = this.model.reachableCountries3(country);
    	
    	this.txtResult.appendText("Reachable countries: "+reachableCountries.size()+"\n\n");
    	for(Country c : reachableCountries) 
    		this.txtResult.appendText(c+"\n");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazioni != null : "fx:id=\"cmbNazioni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStatiRaggiungibili != null : "fx:id=\"btnStatiRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";

        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
