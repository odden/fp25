package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import core.Appointment;
import core.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CalendarViewController {

	// Min Kalender
	private static Stage stage;
	private Gui gui;
	@FXML private ListView<Person> leggTilKalender;
	@FXML private ListView<Appointment> mandag;
	@FXML private Label startK;
	@FXML private Label sluttK;
	@FXML private Label beskrivelseK;
	@FXML private Label stedK;
	@FXML private Label datoK;
	@FXML private DatePicker datoVelgK;
	@FXML private Label labUkeNr;
	
	@FXML
	public void visUkeFraDato(ActionEvent event) {
		//bla frem til riktig uke
		//Alle avtalene må lates inn på riktig måte
	}
	
	@FXML
	public void moteBeskrivelseTilInfo(MouseEvent event) {
		Appointment mote = mandag.getSelectionModel().getSelectedItem(); //testish - velger ikke alle dager
		sluttK.setText(mote.getSlutt());
		startK.setText(mote.getStart());
		beskrivelseK.setText(mote.getBeskrivelse());
		stedK.setText(mote.getRom());
		datoK.setText("" + mote.getDate());
	}
	
	@FXML
	public void blaTilbake(ActionEvent event) {
		//blar tilbake til forrige uke
		//og endrer hva som vises i kalenderen
	}
	
	@FXML 
	public void blaFremover(ActionEvent event) {
		//blar til neste uke
		//og endrer hva som vises i kalenderen
	}
	
	@FXML
	public void seKalender(ActionEvent event) {
		if(!leggTilKalender.getSelectionModel().isEmpty()){
			Person person = leggTilKalender.getSelectionModel().getSelectedItem();
			if(person.hasAvtale()){
				for (Appointment avtale : person.getAvtaler()) {
					if(!leggTilKalender.getSelectionModel().isEmpty()){ 
						mandag.getItems().add(avtale); //test
						//må velge riktig dag å sette inn avtalen på bakgrunn av datoen lagret i avtalen 
					}
				}
			}
		}
	}
	
	public void resetBeskrivelse() {
		startK.setText("-");
		sluttK.setText("-");
		beskrivelseK.setText("-");
		stedK.setText("-");
		datoK.setText("-");
	}
	
	@FXML
	public void fjernKalender(ActionEvent event) {
		//fjerner valgt kalender fra kalenderen
		if(!leggTilKalender.getSelectionModel().isEmpty()){ 
			Person person = leggTilKalender.getSelectionModel().getSelectedItem();
			if(person.hasAvtale()){
				for (Appointment avtale : person.getAvtaler()) {
					if(!mandag.getSelectionModel().isEmpty()){ //må velge riktig dag(listview)
						if(mandag.getSelectionModel().getSelectedItem().equals(avtale)) { //må velge riktig dag(listView) her
							resetBeskrivelse();
						}
						mandag.getItems().remove(avtale); //test
						//må finne avtalene knyttet til personen. Kan kobles til "this" kanskje?
						//Hva om andre også har denne avtalen? Må sjekke alle kalendre
					}	
				}
			}
		}
	}
	
	//Ny hendelse
	@FXML private TextField tittel;
	@FXML private TextArea beskrivelse;
	@FXML private Label labRomNrNH;
	@FXML private TextField startT;
	@FXML private TextField sluttT;
	@FXML private DatePicker dato;
	@FXML private Button leggTilHendelse;
	@FXML private ListView<Person> velgPerson;
	@FXML private ListView<Person> valgtePersoner;
	
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
        
        datoM.setValue(LocalDate.now());
		final Callback<DatePicker, DateCell> datoerSjekk1 = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(datoM.getValue())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                        }   
                    }
                };
            }
        };
        datoM.setDayCellFactory(datoerSjekk1);
        
        alarm.setItems(FXCollections.observableArrayList("Ingen","15 min","30 min","1 time"));
        alarm.getSelectionModel().selectFirst();
        //test start
        velgPerson.setItems(FXCollections.observableArrayList(new Person("Ollef", "Ollef","ollef@gmail.com","22225555"),new Person("Fridus", "fridus","fridus@gmail.com","22235555")));
        valgtePersoner.setItems(FXCollections.observableArrayList(new Person("Oline", "Oline","olle@gmail.com","22245555"),new Person("Frode", "frodiss","frode@gmail.com","22255555")));
        leggTilKalender.setItems(FXCollections.observableArrayList(new Person("Oline", "Oline","olle@gmail.com","22245555"),new Person("Frode", "frodiss","frode@gmail.com","22255555")));
        leggTilKalender.getItems().get(0).addAvtale(new Appointment());
        Person oline = leggTilKalender.getItems().get(0);
        ArrayList<Appointment> avtaler = oline.getAvtaler();
        Appointment avtale = avtaler.get(0);
        avtale.setBeskrivelse("Kompoiss");
        avtale.setRom("Kinoen");
        avtale.setSlutt("11:13");
        avtale.setStart("11:10");
        avtale.setTitle("klasse1");
        avtale.setDate(LocalDate.now());
        mandag.getItems().add(avtale);
        //test slutt
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
		
		if(check) {
			lagNyAvtale();
		}
		
		
	}	
	
	public void lagNyAvtale() {
		Appointment avtale = new Appointment();
		
		avtale.setStart(startT.getText());
		avtale.setSlutt(sluttT.getText());
		avtale.setBeskrivelse(beskrivelse.getText());
		avtale.setTitle(tittel.getText());
		System.out.println(valgtePersoner.getItems().size());
		System.out.println(valgtePersoner.getItems().get(0));
		for (int i = 0; i < valgtePersoner.getItems().size(); i++) {
			
			avtale.addParticipant(valgtePersoner.getItems().get(i));
			System.out.println(valgtePersoner.getItems().get(i));
		}
		moteinnkallinger.getItems().add(avtale);
		
	}
	
	@FXML
	public void leggTilPerson(ActionEvent event) {
		Person valg = velgPerson.getSelectionModel().getSelectedItem();
		if (valg != null) {
		valgtePersoner.getItems().add(valg);
		velgPerson.getItems().remove(valg);
		}
	}
	
	@FXML
	public void fjernPerson(ActionEvent event) {
		Person valg = valgtePersoner.getSelectionModel().getSelectedItem();
		if (valg != null) {
		valgtePersoner.getItems().remove(valg);
		velgPerson.getItems().add(valg);
		}
	
	}
	//møter
	@FXML private Tooltip tips;
	@FXML private ListView<Appointment> moteinnkallinger;
	@FXML private ChoiceBox alarm;
	@FXML private TextField sluttTid;
	@FXML private TextField startTid;
	@FXML private TextField tittelM;
	@FXML private TextArea beskrivelseM;
	@FXML private ListView<Person> invitertePersoner;
	@FXML private ListView<Person> inviterEkstraPerson;
	@FXML private DatePicker datoM;
	@FXML private Label labRomNrM;

	@FXML
	public void moteInfoTilView(MouseEvent event) {
		Appointment mote = moteinnkallinger.getSelectionModel().getSelectedItem();
		sluttTid.setText(mote.getSlutt());
		startTid.setText(mote.getStart());
		tittelM.setText(mote.getTitle());
		beskrivelseM.setText(mote.getBeskrivelse());
		invitertePersoner.getItems().clear();
		for (Person person : mote.getParticipants()) {
			invitertePersoner.getItems().add(person);
		}
	}
	
	@FXML
	public void lagreMoteEndring(ActionEvent event) {
		//Metoden skal lagre alle endringene gjort
	}
	
	@FXML
	public void slettMote(ActionEvent event) {
		//Metoden skal slette møte
		//alle andres kalendere oppdateres  
	}
	
	@FXML
	public void slettDeltaker(ActionEvent event) {
		//Metoden fjerner deltaker fra statusliste 
		//dette blir ikke lagret før man trykker lagre endring
	}
	
	@FXML
	public void inviterEkstraDeltaker(ActionEvent event) {
		//Metoden flytter person over til deltaker-ruten 
		//Da skal personen få en invitasjon (møtet blir synlig i møter) etter at "lagre endringer" 
		//er trykket.
	}
	
	@FXML
	public void finnRom(ActionEvent event) {
		//Finner et passende rom utifra antall folk invitert
	}

	public void initData(Stage stage, Gui gui) {
		this.stage = stage;
		this.gui = gui;
	}
	
	
	
	
	

}
