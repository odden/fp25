package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class CalendarViewController {

	// Min Kalender
	
	//Ny hendelse
	@FXML private TextField tittel;
	@FXML private TextArea beskrivelse;
	@FXML private TextField startT;
	@FXML private TextField sluttT;
	@FXML private DatePicker dato;
	@FXML private Button leggTilHendelse;
	
	public void initialize(){
		dato.setValue(LocalDate.now());
		final Callback<DatePicker, DateCell> datoerSjekk = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(dato.getValue())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                        }   
                    }
                };
            }
        };
        dato.setDayCellFactory(datoerSjekk);
	}
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event){
		boolean check = true;
		
		//sjekker tittel
		if (tittel.getText().isEmpty()) {
			check = false;
			tittel.setTooltip(new Tooltip("Kan ike være tom"));
		}
		
		//sjekker beskrivelse
		else if (beskrivelse.getText().isEmpty()) {
			check = false;
		}

		//sjekk start tid format
		String str1 = startT.getText();
		String[] tid1 = str1.split(":");
		String str = sluttT.getText();
		String[] tid = str.split(":");
		try {
		int endH = (Integer.parseInt(tid[0]));
		int endM = (Integer.parseInt(tid[1]));
		int startH = (Integer.parseInt(tid1[0]));
		int startM = (Integer.parseInt(tid1[1]));
			if (!((tid1.length == 2) &&  (startH <= 23) && (startH >= 0) &&
					(startM >= 0) && (startM<= 59) && (tid1[1].length() == 2))) {
				check = false;
				System.out.println("feil format");
			}
		
		
		//sjekk slutt tid format
		
			else if (!((tid.length == 2) &&  (endH <= 23) && (endH >= 0) &&
					(endM >= 0 && (endM <= 59) && (tid[1].length() == 2)))) {
				check = false;
				System.out.println("format");
			}
		//sjekker om slutt tid er etter start
			else if ((startH > endH) || ((startH==endH) && (startM > endM))) {
				check = false;
				System.out.println("start etter slutt");
			}
		
		} catch (Exception e) {
			check = false;
			System.out.println("kun tall");
		} finally {
		} 
		//sjekker om dato er senere
		LocalDate dag = dato.getValue();
		if(dag.isBefore(LocalDate.now())) {
			System.out.println("Må være etter i dag");
		}
		
		
		
	}	
	//møter
	@FXML private ListView moteavtaler;
	@FXML private Tooltip tips;
	@FXML private ListView<String> moteinnkallinger;
	@FXML private ChoiceBox<String> alarm; 
			  
	
	public CalendarViewController() {
		this.alarm = new ChoiceBox<>(FXCollections.observableArrayList("First", "Second", "Third"));
		 alarm.getSelectionModel().selectFirst();
	}
	
	
//	@FXML
//	public void listView(MouseEvent event) {
//		alarm.getItems().addAll("ingen", "15 min","30 min","1 time","3 timer");
//		System.out.println("haha");
//	}
}
