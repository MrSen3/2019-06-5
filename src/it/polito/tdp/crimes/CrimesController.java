/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<AnnoCount> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	AnnoCount annoScelto=boxAnno.getValue();
    	model.creaGrafo(annoScelto);
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#vertici: "+model.getNVertici()+"\n");
    	txtResult.appendText("#archi: "+model.getNArchi()+"\n");
    	
    	Map<Distretto, List<Distretto>> mappaVicini=model.getDistrettiOrdinati();
    	
    	for(Distretto d: mappaVicini.keySet()) {
    		List<Distretto>vicini=mappaVicini.get(d);
    		txtResult.appendText("La lista di vicini in ordine di distanza crescente del distretto "+d.toString()+ " è:\n");
    		for(Distretto d1: vicini) {
    			txtResult.appendText(d1.toString()+"\n");
    		}
    	}
    		
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	AnnoCount annoScelto=boxAnno.getValue();
    	int meseScelto = boxMese.getValue();
    	int giornoScelto = boxGiorno.getValue();
    	
    	if(!model.cercaData(annoScelto.getAnno(), meseScelto, giornoScelto)) {
    		txtResult.setText("La data selezionata e' errata! Cambia data!\n");
    		return;
    	}
    	
    	if(txtN.getText().isEmpty()) {
    		txtResult.setText("Casella N vuota! Devi inserire un numero intero di poliziotti compreso tra 1 e 10!\n");
    	}
    	
    	
    	try {
    	Integer nPoliziotti = Integer.parseInt(txtN.getText());
    	
    	if(nPoliziotti<1 || nPoliziotti>10) {
    		txtResult.setText("Numero N errato! Devi inserire un numero intero compreso tra 1 e 10!\n");
    		return;
    	}
    	
    	//I poliziotti all'istante 0 devono essere tutti nel distretto con meno criminalita
    	Distretto partenza = model.getDistrettoMenoCriminalita();
    	txtResult.appendText("Il distretto con minor criminalita' e': "+partenza.toString());
    	
    	//Per la simulazione servono i dati inseriti dall'utente
    	//Distretto con meno criminalita
    	//Numero di poliziotti tot
    	//Data
    	model.simula(partenza, nPoliziotti, annoScelto.getAnno(), meseScelto, giornoScelto);
    	
    	txtResult.appendText("Il numero di eventi mal gestiti e': "+model.getEventiMalGestiti());
    	
    	}catch(NumberFormatException e) {
    		System.err.println("Errore: conversione da stringa a numero intero di txtN non riuscita!");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(model.getAnni());
    	boxMese.getItems().addAll(model.getMesi());
    	boxGiorno.getItems().addAll(model.getGiorni());
    }
}
