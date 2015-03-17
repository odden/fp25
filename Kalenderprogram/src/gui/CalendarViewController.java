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
import java.util.Optional;

import core.Appointment;
import core.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CalendarViewController {

	private Person me;
	private static Stage stage;
	private Gui gui;
	private ArrayList<Person> users;
	
	private void resetBoksFarge(TextField tittel, TextArea beskrivelse, TextField sted, TextField antall, TextField start, TextField slutt) {
		tittel.setStyle("-fx-border-color:black");
		beskrivelse.setStyle("-fx-border-color:black");
		sted.setStyle("-fx-border-color:black");
		antall.setStyle("-fx-border-color:black");
		slutt.setStyle("-fx-border-color:black");
		start.setStyle("-fx-border-color:black");
		
		
	}
	
	private void stedValgt(TextField antall, Button finnRom, RadioButton romKnapp, TextField sted, ComboBox ledigRom) {
		antall.setDisable(true);
		finnRom.setDisable(true);
		romKnapp.setSelected(false);
		sted.setDisable(false);
		ledigRom.setDisable(true);
	}
	
	private void romValgt(TextField sted, RadioButton stedKnapp, TextField antall, Button finnRom, ComboBox ledigRom) {
		sted.setDisable(true);
		System.out.println("sånn");
		stedKnapp.setSelected(false);
		antall.setDisable(false);
		finnRom.setDisable(false);
		ledigRom.setDisable(false);
	}
	
	public boolean validateAvtale(TextField tittel, TextArea beskrivelse, TextField startT, TextField sluttT, TextField sted, ComboBox rom, TextField antall, RadioButton stedValgt, ListView<Person> rediger) { {
		boolean check = true;
		
		//sjekker tittel
		if (tittel.getText().isEmpty()) {
			check = false;
			tittel.setStyle("-fx-border-color:red");
			tittel.setTooltip(new Tooltip("Kan ike være tom"));
		}else {
			tittel.setStyle("-fx-border-color:green");
		}
		
		//sjekker beskrivelse
		if (beskrivelse.getText().isEmpty()) {
			beskrivelse.setStyle("-fx-border-color:red");
			check = false;
		}else {
			beskrivelse.setStyle("-fx-border-color:green");
		}
		
		//sjekker sted
		if (stedValgt.isSelected() && sted.getText().isEmpty()) {
			sted.setStyle("-fx-border-color:red");
			check = false;
		}else {
			sted.setStyle("-fx-border-color:green");
		}
		
		if (!stedValgt.isSelected() && (!antall.getText().matches("[0-9]+"))) {
			antall.setStyle("-fx-border-color:red");
			check = false;
		}else {
			antall.setStyle("-fx-border-color:green");
			if (!stedValgt.isSelected() && Integer.parseInt(antall.getText()) < rediger.getItems().size()){
				antall.setStyle("-fx-border-color:red");
				check = false;
			}
			else {
				antall.setStyle("-fx-border-color:green");
			}
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
				startT.setStyle("-fx-border-color:red");
				System.out.println("feil format");
			}else {
				startT.setStyle("-fx-border-color:green");
			}
		
		
		//sjekk slutt tid format
		
			if (!((tid.length == 2) &&  (endH <= 23) && (endH >= 0) &&
					(endM >= 0 && (endM <= 59) && (tid[1].length() == 2)))) {
				check = false;
				sluttT.setStyle("-fx-border-color:red");
				System.out.println("format");
			}else {
				sluttT.setStyle("-fx-border-color:green");
			}
		//sjekker om slutt tid er etter start
			if ((startH > endH) || ((startH==endH) && (startM > endM))) {
				check = false;
				System.out.println("start etter slutt");
				sluttT.setStyle("-fx-border-color:red");
			}
		
		} catch (Exception e) {
			check = false;
			System.out.println("kun tall");
			startT.setStyle("-fx-border-color:red");
			sluttT.setStyle("-fx-border-color:red");
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
			if (! this.personerIKalender.contains(person)) {
				this.personerIKalender.add(person);
				updateAppointments();
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
		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		for (Person p : this.personerIKalender) {
			ArrayList<Appointment> a = p.getMyAppointments();
			for (Appointment appointment : a) {
				if(!apps.contains(appointment)){
					apps.add(appointment);
				}
			}
		}
		for (Appointment appointment : apps) {
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
		lordagL.setText("L\u00F8rdag " + lordagDato.getDayOfMonth() + ".");
		sondagL.setText("S\u00F8ndag " + sondagDato.getDayOfMonth() + ".");
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
			if (this.personerIKalender.contains(person)) {
				this.personerIKalender.remove(person);
				updateAppointments();
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
	@FXML private Label avtaleApprove;
	
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
		
		check = validateAvtale(tittelNH, beskrivelseNH, startNH, sluttNH, stedNH, romCBNH, antallNH, velgStedNH, velgPerson);
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
		String tittel = tittelNH.getText();
		String sted = stedNH.getText();
		String rom = null;
		if (velgRomNH.isSelected()) {
			rom = romCBNH.getValue().toString();
		}
		
		String dato = datoNH.getValue().toString();
		String start = startNH.getText();
		String slutt = sluttNH.getText();
		
		int id = gui.tryCreateAppointment(this.me, tittel,sted,rom,dato, start, slutt, personer);
		if(id != 0){
			Appointment avtale = new Appointment(id,this.me, tittel,sted, (rom == null ? 0 : Integer.parseInt(rom)),dato, start, slutt, personer);
			moteinnkallinger.getItems().add(avtale);
			me.addAppointment(avtale);
			updateAppointments();
			for (Person person : personer) {
				person.addAppointment(avtale);
			}
			avtaleApprove.setText("Avtale '"  + avtale.getTitle() + "' opprettet!" );
			
		}
			
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
		if (validateAvtale(tittelNH, beskrivelseNH, startNH, sluttNH, stedNH, romCBNH, antallNH, velgStedNH,velgPerson)) {
			
			ArrayList<String> ledigRom = gui.getRoom(datoNH.getValue()+"", startNH.getText(), sluttNH.getText(), Integer.parseInt(antallNH.getText()));
		if(!ledigRom.isEmpty()) {
			romCBNH.getItems().clear();
			romCBNH.getItems().addAll(ledigRom);
			romCBNH.getSelectionModel().selectFirst();
		}
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
	@FXML private Button deltar;
	@FXML private Button deltarIkke;
	@FXML private Tab moter;



	@FXML
	public void moteInfoTilView() {
		resetBoksFarge(tittelM, beskrivelseM, stedM, antallM, startM, sluttM);
		if(!moteinnkallinger.getSelectionModel().isEmpty()) {
			Appointment mote = moteinnkallinger.getSelectionModel().getSelectedItem();
			ArrayList<Person> ikkeInvitert = users;
			sluttM.setText(mote.getSlutt());
			startM.setText(mote.getStart());
			tittelM.setText(mote.getTitle());
			stedM.setText(mote.getSted());
			beskrivelseM.setText(mote.getTitle());
			datoM.setValue(mote.getDate());
			System.out.println(mote.getRom());
			
			romCBM.getItems().removeAll(romCBM.getItems());
			if (mote.getRom() != 0){
				velgStedM.setSelected(false);
				velgRomM.setSelected(true);
				romCBM.getItems().add(mote.getRom());
				romCBM.getSelectionModel().selectFirst();;
			}
			if(mote.getHost().equals(me)) {
				
				notHostValg.setVisible(false);
				hostValg.setVisible(true);
				beskrivelseM.setDisable(false);
				datoM.setDisable(false);
				tittelM.setDisable(false);
				sluttM.setDisable(false);
				startM.setDisable(false);
				stedM.setDisable(false);
				velgRomM.setDisable(false);
				velgStedM.setDisable(false);
				invitertePersoner.getItems().clear();
				for (Person person : mote.getParticipants()) {
					invitertePersoner.getItems().add(person);
					ikkeInvitert.remove(person);
				}
				inviterEkstraPerson.getItems().clear();
				inviterEkstraPerson.getItems().addAll(ikkeInvitert);
			} else {
				velgStedM.setDisable(true);
				velgRomM.setDisable(true);
				hostValg.setVisible(false);
				notHostValg.setVisible(true);
				beskrivelseM.setDisable(true);
				datoM.setDisable(true);
				tittelM.setDisable(true);
				sluttM.setDisable(true);
				startM.setDisable(true);
				stedM.setDisable(true);
				
			}
			if(mote.getSted().equals("")){
				velgStedM.setSelected(false);
				velgRomM.setSelected(true);
				romValgt(stedM, velgStedM, antallM, finnRomM, romCBM);
				
			} else {
				velgRomM.setSelected(false);
				velgStedM.setSelected(true);
				stedValgt(antallM, finnRomM, velgRomM, stedM, romCBM);
			}
			
		}
	}
	
	@FXML
	public void lagreMoteEndring(ActionEvent event) {
		//Metoden skal lagre alle endringene gjort
		Appointment avtale = moteinnkallinger.getSelectionModel().getSelectedItem();
		ArrayList<Person> personer = new ArrayList<Person>();
		
		if(validateAvtale(tittelM, beskrivelseM, startM, sluttM, stedM, romCBM, antallM, velgStedM,invitertePersoner)) {
			for (Person person : invitertePersoner.getItems()) {
				personer.add(person);
			}
			String tittel = tittelM.getText();
			String sted = stedM.getText();
			String rom = null;
			if (velgRomM.isSelected()) {
				rom = romCBM.getValue().toString();
			}
			LocalDate dato = datoM.getValue();
			String start = startM.getText();
			String slutt = sluttM.getText();
			if (gui.tryEditAppointment(avtale.getId(), this.me, tittel, sted, rom, dato.toString(), start, slutt, personer)) {
				avtale.setDate(dato);
				if (rom == null) {
					avtale.setRom(0);
				}else {
					avtale.setRom(Integer.parseInt(rom));
				}
				for (Person person : personer) {
					avtale.addParticipant(person);
				}
				avtale.setStart(start);
				avtale.setSlutt(slutt);
				avtale.setSted(sted);
				avtale.setTitle(tittel);
			}
		}

	}
	
	@FXML
	public void slettMote(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		ButtonType ja = new ButtonType("JA");
		ButtonType nei = new ButtonType("NEI");
		alert.getButtonTypes().set(0, nei);
		alert.getButtonTypes().set(1, ja);
		
		alert.setTitle("Sikker p\u00E5 at du vil slette?");
		alert.setHeaderText("Slette m\u00F8te?");
		alert.setContentText("Er du sikker p\u00E5 at du vil slette dette m\u00F8tet?");
		
		Optional<ButtonType> svar = alert.showAndWait();
		 
		
		if(svar.get().equals(ja)) {
			Appointment slett = moteinnkallinger.getItems().get(moteinnkallinger.getSelectionModel().getSelectedIndex());
			moteinnkallinger.getItems().remove(slett);
			for (Person person : slett.getParticipants()) {
				person.removeAppointment(slett);
			}
			me.removeAppointment(slett);
			gui.sc.deleteAppointment(slett.getId());
			
			if (!moteinnkallinger.getItems().isEmpty()) {
				moteinnkallinger.getSelectionModel().clearSelection();
				moteinnkallinger.getSelectionModel().selectFirst();
				moteInfoTilView();
			}else {
			beskrivelseM.clear();
			tittelM.clear();
			stedM.clear();
			sluttM.clear();
			startM.clear();
			antallM.clear();
			invitertePersoner.getItems().clear();
			inviterEkstraPerson.getItems().clear();
			}
			updateAppointments();
		}
		
		//Metoden skal slette møte
		//alle andres kalendere oppdateres  
	}
	
	@FXML
	public void slettDeltaker(ActionEvent event) {
		invitertePersoner.getItems().remove(invitertePersoner.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	public void inviterEkstraDeltaker(ActionEvent event) {
		if(!inviterEkstraPerson.getSelectionModel().isEmpty()){
		invitertePersoner.getItems().add(inviterEkstraPerson.getSelectionModel().getSelectedItem());
		inviterEkstraPerson.getItems().remove(inviterEkstraPerson.getSelectionModel().getSelectedItem());
		}
		//Metoden flytter person over til deltaker-ruten 
		//Da skal personen få en invitasjon (møtet blir synlig i møter) etter at "lagre endringer" 
		//er trykket.
	}
	
	@FXML
	public void finnRomM(ActionEvent event) {
		Appointment mote = moteinnkallinger.getSelectionModel().getSelectedItem();
		romCBM.getItems().removeAll(romCBM.getItems());
		if (mote.getRom() != 0){
			velgStedM.setSelected(false);
			velgRomM.setSelected(true);
			romCBM.getItems().add(mote.getRom());
			romCBM.getSelectionModel().selectFirst();;
		}
		if (validateAvtale(tittelM, beskrivelseM, startM, sluttM, stedM, romCBM, antallM, velgStedM,invitertePersoner)) {
			ArrayList<String> ledigRom = gui.getRoom(datoM.getValue()+"", startM.getText(), sluttM.getText(), Integer.parseInt(antallM.getText()));
			if(!ledigRom.isEmpty()) {
				romCBM.getItems().addAll(ledigRom);
				romCBM.getSelectionModel().selectFirst();
			}
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
	
	@FXML
	public void deltar(ActionEvent event) {
		gui.sc.setStatus(me.getUsername(), moteinnkallinger.getSelectionModel().getSelectedItem().getId(), true);
	}
	
	@FXML
	public void deltarIkke(ActionEvent event) {
		gui.sc.setStatus(me.getUsername(), moteinnkallinger.getSelectionModel().getSelectedItem().getId(), false);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		ButtonType ja = new ButtonType("JA");
		ButtonType nei = new ButtonType("NEI");
		alert.getButtonTypes().set(0, nei);
		alert.getButtonTypes().set(1, ja);
		
		alert.setTitle("Møte");
		alert.setHeaderText("Vise møtet?");
		alert.setContentText("Vil du slette møtet fra kalenderen?");
		
		Optional<ButtonType> svar = alert.showAndWait();
		
		if(svar.get().equals(ja)) {
			Appointment slett = moteinnkallinger.getItems().get(moteinnkallinger.getSelectionModel().getSelectedIndex());
			moteinnkallinger.getItems().remove(slett);
			me.removeAppointment(slett);
			if (!moteinnkallinger.getItems().isEmpty()) {
			moteinnkallinger.getSelectionModel().clearSelection();
			moteinnkallinger.getSelectionModel().selectFirst();
			moteInfoTilView();
			} else {
				beskrivelseM.clear();
				tittelM.clear();
				stedM.clear();
				sluttM.clear();
				startM.clear();
				antallM.clear();
				invitertePersoner.getItems().clear();
				inviterEkstraPerson.getItems().clear();
			}
			updateAppointments();
			
		}
		
	}
		
	

	public void initData(Stage stage, Gui gui, ArrayList<Person> users, Person me) {
		CalendarViewController.stage = stage;
		this.me = me;
		this.gui = gui;
		this.users = users;
		ArrayList<Appointment> appointments = me.getMyAppointments();
		for (Person person : users) {
			leggTilKalender.getItems().add(person);
			velgPerson.getItems().add(person);
		}
		for (Appointment appointment : appointments) {
			moteinnkallinger.getItems().add(appointment);
		}
		if(!moteinnkallinger.getItems().isEmpty()){
		moteinnkallinger.getSelectionModel().selectLast();
		moteInfoTilView();
		}
		this.personerIKalender.add(me);
		updateAppointments();
	}
	

}
