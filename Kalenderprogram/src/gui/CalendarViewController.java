package gui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import core.Appointment;
import core.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CalendarViewController {

	private Person me;
	private static Stage stage;
	private Gui gui;
	private ArrayList<Person> users;
	private ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();
	
	private void stedValgt(TextField antall, Button finnRom, RadioButton romKnapp, TextField sted, ComboBox ledigRom) {
		antall.setDisable(true);
		finnRom.setDisable(true);
		romKnapp.setSelected(false);
		sted.setDisable(false);
		ledigRom.setDisable(true);
	}
	
	private void romValgt(TextField sted, RadioButton stedKnapp, TextField antall, Button finnRom, ComboBox ledigRom) {
		sted.setDisable(true);
		stedKnapp.setSelected(false);
		antall.setDisable(false);
		finnRom.setDisable(false);
		ledigRom.setDisable(false);
	}
	
	public boolean validateAvtale(TextField tittel, TextArea beskrivelse, TextField startT, TextField sluttT, TextField sted, ComboBox rom, String antall, RadioButton stedValgt) { {
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
		
		//sjekker sted
		else if (stedValgt.isSelected() && sted.getText().isEmpty()) {
			check = false;
		}
		
		else if (!stedValgt.isSelected() && (!antall.matches("[0-9]+"))) {
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
			return check;
			} 
		}
	}
	
	// Min Kalender
	
	@FXML private ListView<Person> leggTilKalender;
	@FXML private ListView<Appointment> mandag;
	@FXML private ListView<Appointment> tirsdag;
	@FXML private ListView<Appointment> onsdag;
	@FXML private ListView<Appointment> torsdag;
	@FXML private ListView<Appointment> fredag;
	@FXML private ListView<Appointment> lordag;
	@FXML private ListView<Appointment> sondag;
	@FXML private Label mandagL;
	@FXML private Label tirsdagL;
	@FXML private Label onsdagL;
	@FXML private Label torsdagL;
	@FXML private Label fredagL;
	@FXML private Label lordagL;
	@FXML private Label sondagL;
	private LocalDate mandagDato;
	private LocalDate tirsdagDato;
	private LocalDate onsdagDato;
	private LocalDate torsdagDato;
	private LocalDate fredagDato;
	private LocalDate lordagDato;
	private LocalDate sondagDato;
	@FXML private Label startK;
	@FXML private Label sluttK;
	@FXML private Label beskrivelseK;
	@FXML private Label stedK;
	@FXML private Label datoK;
	@FXML private DatePicker datoVelgK;
	@FXML private Label labUkeNr;
	@FXML private Label maned;
	private ArrayList<LocalDate> ukeDatoer = new ArrayList<LocalDate>();
	private ArrayList<ListView<Appointment>> ukeDagListe =  new ArrayList<ListView<Appointment>>();
	private ArrayList<Person> personerIKalender = new ArrayList<Person>();
	
	@FXML
	public void visUkeFraDato(Event event) {
		System.out.println("hei");
		if (datoVelgK.getValue() != null){
			LocalDate tilDato = datoVelgK.getValue();
			settKalenderDato(tilDato);
			updateAppointments();
		}
	}
	
	@FXML
	public void moteBeskrivelseTilInfo(MouseEvent event) {
		resetBeskrivelse();
		Appointment mote;
		ListView<Appointment> valgt = (ListView<Appointment>) event.getSource();
		if(!valgt.getSelectionModel().isEmpty()){
			mote = valgt.getSelectionModel().getSelectedItem();
			sluttK.setText(mote.getSlutt());
			startK.setText(mote.getStart());
			beskrivelseK.setText(mote.getTitle());
			stedK.setText(Integer.toString(mote.getRom()));
			datoK.setText("" + mote.getDate());
		}
	}
	
	@FXML
	public void blaTilbake(ActionEvent event) {
		mandagDato = mandagDato.minusWeeks(1);
		tirsdagDato = tirsdagDato.minusWeeks(1);
		onsdagDato = onsdagDato.minusWeeks(1);
		torsdagDato = torsdagDato.minusWeeks(1);
		fredagDato = fredagDato.minusWeeks(1);
		lordagDato = lordagDato.minusWeeks(1);
		sondagDato = sondagDato.minusWeeks(1);
		updateUkeDatoer();
		updateKalenderDato();
		updateAppointments();
		//endrer ikke hva som vises i kalenderen
	}
	
	@FXML 
	public void blaFremover(ActionEvent event) {
		mandagDato = mandagDato.plusWeeks(1);
		tirsdagDato = tirsdagDato.plusWeeks(1);
		onsdagDato = onsdagDato.plusWeeks(1);
		torsdagDato = torsdagDato.plusWeeks(1);
		fredagDato = fredagDato.plusWeeks(1);
		lordagDato = lordagDato.plusWeeks(1);
		sondagDato = sondagDato.plusWeeks(1);
		updateUkeDatoer();
		updateKalenderDato();
		updateAppointments();
		//endrer ikke hva som vises i kalenderen
	}
	
	@FXML
	public void seKalender(ActionEvent event) {
		if(!leggTilKalender.getSelectionModel().isEmpty()){
			Person person = leggTilKalender.getSelectionModel().getSelectedItem();
			this.personerIKalender.add(person);
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
	
	private void updateUkeDatoer(){
		this.ukeDatoer.clear();
		this.ukeDatoer.add(mandagDato);
		this.ukeDatoer.add(tirsdagDato);
		this.ukeDatoer.add(onsdagDato);
		this.ukeDatoer.add(torsdagDato);
		this.ukeDatoer.add(fredagDato);
		this.ukeDatoer.add(lordagDato);
		this.ukeDatoer.add(sondagDato);
	}
	
	private void settKalenderDato(LocalDate date) {
		DayOfWeek dag = date.getDayOfWeek();	       
		Month month = date.getMonth();
		System.out.println(dag);
		maned.setText(month + "");
		if (dag.getValue() == 1) {
			mandagDato = date;
			tirsdagDato = date.plusDays(1);
			onsdagDato = date.plusDays(2);
			torsdagDato = date.plusDays(3);
			fredagDato = date.plusDays(4);
			lordagDato = date.plusDays(5);
			sondagDato = date.plusDays(6);
			updateKalenderDato();
		}
		else if (dag.getValue() == 2) {
			mandagDato = date.minusDays(1);
			tirsdagDato = date;
			onsdagDato = date.plusDays(1);
			torsdagDato = date.plusDays(2);
			fredagDato = date.plusDays(3);
			lordagDato = date.plusDays(4);
			sondagDato = date.plusDays(5);
			updateKalenderDato();
		}
		else if (dag.getValue() == 3) {
			mandagDato = date.minusDays(2);
			tirsdagDato = date.minusDays(1);
			onsdagDato = date;
			torsdagDato = date.plusDays(1);
			fredagDato = date.plusDays(2);
			lordagDato = date.plusDays(3);
			sondagDato = date.plusDays(4);
			updateKalenderDato();
			
		}
		else if (dag.getValue() == 4) {
			mandagDato = date.minusDays(3);
			tirsdagDato = date.minusDays(2);
			onsdagDato = date.minusDays(1);
			torsdagDato = date;
			fredagDato = date.plusDays(1);
			lordagDato = date.plusDays(2);
			sondagDato = date.plusDays(3);
			updateKalenderDato();
		}
		else if (dag.getValue() == 5) {
			mandagDato = date.minusDays(4);
			tirsdagDato = date.minusDays(3);
			onsdagDato = date.minusDays(2);
			torsdagDato = date.minusDays(1);
			fredagDato = date;
			lordagDato = date.plusDays(1);
			sondagDato = date.plusDays(2);
			updateKalenderDato();
		}
		else if (dag.getValue() == 6) {
			mandagDato = date.minusDays(5);
			tirsdagDato = date.minusDays(4);
			onsdagDato = date.minusDays(3);
			torsdagDato = date.minusDays(2);
			fredagDato = date.minusDays(1);
			lordagDato = date;
			sondagDato = date.plusDays(1);
			updateKalenderDato();
		}
		else {
			mandagDato = date.minusDays(6);
			tirsdagDato = date.minusDays(5);
			onsdagDato = date.minusDays(4);
			torsdagDato = date.minusDays(3);
			fredagDato = date.minusDays(2);
			lordagDato = date.minusDays(1);
			sondagDato = date;
			updateKalenderDato();
		}
		updateUkeDatoer();
	}
	
	private void updateAppointments(){
		for (ListView<Appointment> list : ukeDagListe) {
			list.getItems().clear();
		}
		for (Appointment appointment : this.myAppointments) {
			for (LocalDate date : this.ukeDatoer) {
				if (appointment.getDate().equals(date)) {
					int dayInt = ukeDatoer.indexOf(date);
					if (!this.ukeDagListe.get(dayInt).getItems().contains(appointment)) {
						this.ukeDagListe.get(dayInt).getItems().add(appointment);
					}
				}
			}
		}
		
		
	}
	
	public void updateKalenderDato() {
		WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
		mandagL.setText("Mandag " + mandagDato.getDayOfMonth() + ".");
		tirsdagL.setText("Tirsdag " + tirsdagDato.getDayOfMonth() + ".");
		onsdagL.setText("Onsdag " + onsdagDato.getDayOfMonth() + ".");
		torsdagL.setText("Torsdag " + torsdagDato.getDayOfMonth() + ".");
		fredagL.setText("Fredag " + fredagDato.getDayOfMonth() + ".");
		lordagL.setText("Lørdag " + lordagDato.getDayOfMonth() + ".");
		sondagL.setText("Søndag " + sondagDato.getDayOfMonth() + ".");
		maned.setText(mandagDato.getMonth() +" " + mandagDato.getYear());
		labUkeNr.setText("Uke " + mandagDato.get(weekFields.weekOfWeekBasedYear()));
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
	@FXML private TextField tittelNH;
	@FXML private TextArea beskrivelseNH;
	@FXML private Label labRomNrNH;
	@FXML private TextField startNH;
	@FXML private TextField sluttNH;
	@FXML private DatePicker datoNH;
	@FXML private Button leggTilHendelse;
	@FXML private ListView<Person> velgPerson;
	@FXML private ListView<Person> valgtePersoner;
	@FXML private TextField stedNH;
	@FXML private ComboBox romCBNH; 
	@FXML private TextField antallNH;
	@FXML private RadioButton velgStedNH;
	@FXML private RadioButton velgRomNH;
	@FXML private Button finnRomNH;
	
	public void initialize(){
		this.ukeDagListe.add(this.mandag);
		this.ukeDagListe.add(this.tirsdag);
		this.ukeDagListe.add(this.onsdag);
		this.ukeDagListe.add(this.torsdag);
		this.ukeDagListe.add(this.fredag);
		this.ukeDagListe.add(this.lordag);
		this.ukeDagListe.add(this.sondag);
		datoNH.setValue(LocalDate.now());
		final Callback<DatePicker, DateCell> datoerSjekk = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                        }   
                    }
                };
            }
        };
        datoNH.setDayCellFactory(datoerSjekk);
        
        datoM.setValue(LocalDate.now());
		
        datoM.setDayCellFactory(datoerSjekk);

        LocalDate date = LocalDate.now();
        settKalenderDato(date);
        velgStedM.setSelected(true);
        velgStedNH.setSelected(true);
        stedValgt(antallM, finnRomM, velgRomM, stedM, romCBM);
        stedValgt(antallNH, finnRomNH, velgRomNH, stedNH, romCBNH);
        alarm.setItems(FXCollections.observableArrayList("Ingen","15 min","30 min","1 time"));
        alarm.getSelectionModel().selectFirst();
	}
	
	@FXML
	public void stedValgtNH(ActionEvent event) {
		stedValgt(antallNH, finnRomNH, velgRomNH, stedNH, romCBNH);
	}
	

	@FXML 
	public void romValgtNH(ActionEvent event) {
		romValgt(stedNH, velgStedNH, antallNH, finnRomNH, romCBNH);
	}

	
	

	@FXML protected void handleSubmitButtonAction(ActionEvent event){
		boolean check = true;
		
		check = validateAvtale(tittelNH, beskrivelseNH, startNH, sluttNH, stedNH, romCBNH, antallNH.getText(), velgStedNH);
		//sjekker om dato er senere
	
		
		if(check) {
			lagNyAvtale();
		}
		
		
	}	
	
	public void lagNyAvtale() {
		ArrayList<Person> personer = new ArrayList<Person>();
		for (Person person : valgtePersoner.getItems()) {
			personer.add(person);
		}
		Appointment avtale = new Appointment(1,me.getUsername(), tittelNH.getText(),stedNH.getText(),0,datoNH.getValue().toString(),startNH.getText(),sluttNH.getText(), personer);
		moteinnkallinger.getItems().add(avtale);
		myAppointments.add(avtale);
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
	
	@FXML
	public void finnRomNH(ActionEvent event) {
		if (validateAvtale(tittelNH, beskrivelseNH, startNH, sluttNH, stedNH, romCBNH, antallNH.getText(), velgStedNH)) {
		gui.getRoom(datoNH.getValue()+"", startNH.getText(), sluttNH.getText(), Integer.parseInt(antallNH.getText()));
		//Finner et passende rom utifra antall folk invitert
		}
	}
	//møter
	@FXML private Tooltip tips;
	@FXML private ListView<Appointment> moteinnkallinger;
	@FXML private ChoiceBox alarm;
	@FXML private TextField sluttM;
	@FXML private TextField startM;
	@FXML private TextField tittelM;
	@FXML private TextField stedM;
	@FXML private TextArea beskrivelseM;
	@FXML private ListView<Person> invitertePersoner;
	@FXML private ListView<Person> inviterEkstraPerson;
	@FXML private DatePicker datoM;
	@FXML private Label labRomNrM;
	@FXML private Pane hostValg;
	@FXML private Pane notHostValg;
	@FXML private Button finnRomM;
	@FXML private ComboBox romCBM;
	@FXML private TextField antallM;
	@FXML private RadioButton velgStedM;
	@FXML private RadioButton velgRomM;

	

	@FXML
	public void moteInfoTilView(MouseEvent event) {
		if(!moteinnkallinger.getSelectionModel().isEmpty()) {
			Appointment mote = moteinnkallinger.getSelectionModel().getSelectedItem();
			sluttM.setText(mote.getSlutt());
			startM.setText(mote.getStart());
			tittelM.setText(mote.getTitle());
			beskrivelseM.setText(mote.getTitle());
			if(mote.getHost().equals(me.getUsername())) {
				notHostValg.setVisible(false);
				hostValg.setVisible(true);
				invitertePersoner.getItems().clear();
				for (Person person : mote.getParticipants()) {
					invitertePersoner.getItems().add(person);
				}
			} else {
				hostValg.setVisible(false);
				notHostValg.setVisible(true);
				beskrivelseM.setDisable(true);
				datoM.setDisable(true);
				tittelM.setDisable(true);
				sluttM.setDisable(true);
				startM.setDisable(true);
				stedM.setDisable(true);
				
			}
			
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
		invitertePersoner.getItems().remove(invitertePersoner.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	public void inviterEkstraDeltaker(ActionEvent event) {
		invitertePersoner.getItems().add(inviterEkstraPerson.getSelectionModel().getSelectedItem());
		inviterEkstraPerson.getItems().remove(inviterEkstraPerson.getSelectionModel().getSelectedItem());
		//Metoden flytter person over til deltaker-ruten 
		//Da skal personen få en invitasjon (møtet blir synlig i møter) etter at "lagre endringer" 
		//er trykket.
	}
	
	@FXML
	public void finnRomM(ActionEvent event) {
		if (validateAvtale(tittelM, beskrivelseM, startM, sluttM, stedM, romCBM, antallM.getText(), velgStedM)) {
		gui.getRoom(datoM.getValue()+"", startM.getText(), sluttM.getText(), Integer.parseInt(antallM.getText()));
		//Finner et passende rom utifra antall folk invitert
		}
	}
	
	@FXML
	public void stedValgtM(ActionEvent event) {
		stedValgt(antallM, finnRomM, velgRomM, stedM, romCBM);
	}
	

	@FXML 
	public void romValgtM(ActionEvent event) {
		romValgt(stedM, velgStedM, antallM, finnRomM, romCBM);
	}
		
	

	public void initData(Stage stage, Gui gui, ArrayList<Person> users, Person me) {
		CalendarViewController.stage = stage;
		this.me = me;
		this.gui = gui;
		this.users = users;
		for (Person person : users) {
			leggTilKalender.getItems().add(person);
		}
		ArrayList<Appointment> appointments = me.getMyAppointments();
		for (Appointment appointment : appointments) {
			moteinnkallinger.getItems().add(appointment);
		}
		updateAppointments();
	}
	

}
