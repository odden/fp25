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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

	private String meg;
	private static Stage stage;
	private Gui gui;
	private ArrayList<Person> users;
	private ArrayList<Appointment> myAppointments;
	
	// Min Kalender
	
	@FXML private ListView<Person> leggTilKalender;
	@FXML private ListView<Appointment> mandag;
	@FXML private ListView<Appointment> tirsdag;
	@FXML private ListView<Appointment> onsdag;
	@FXML private ListView<Appointment> tordag;
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
	
	@FXML
	public void visUkeFraDato(Event event) {
		System.out.println("hei");
		if (datoVelgK.getValue() != null){
		LocalDate tilDato = datoVelgK.getValue();
		settKalenderDato(tilDato);
		}
		//Alle avtalene må lates inn på riktig måte
	}
	
	@FXML
	public void moteBeskrivelseTilInfo(MouseEvent event) {
		if (mandag.isFocused()){
			System.out.println("var det jeg sa jo!");
		Appointment mote = mandag.getSelectionModel().getSelectedItem(); //testish - velger ikke alle dager
		sluttK.setText(mote.getSlutt());
		startK.setText(mote.getStart());
		beskrivelseK.setText(mote.getBeskrivelse());
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
		updateKalenderDato();
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
		updateKalenderDato();
		//endrer ikke hva som vises i kalenderen
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
	@FXML private TextField tittel;
	@FXML private TextArea beskrivelse;
	@FXML private Label labRomNrNH;
	@FXML private TextField startT;
	@FXML private TextField sluttT;
	@FXML private DatePicker dato;
	@FXML private Button leggTilHendelse;
	@FXML private ListView<Person> velgPerson;
	@FXML private ListView<Person> valgtePersoner;
	@FXML private TextField stedNH;
	
	public void initialize(){
		dato.setValue(LocalDate.now());
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
        dato.setDayCellFactory(datoerSjekk);
        
        datoM.setValue(LocalDate.now());
		
        datoM.setDayCellFactory(datoerSjekk);

        LocalDate date = LocalDate.now();
        settKalenderDato(date);
        
        alarm.setItems(FXCollections.observableArrayList("Ingen","15 min","30 min","1 time"));
        alarm.getSelectionModel().selectFirst();
        //test start
        velgPerson.setItems(FXCollections.observableArrayList(new Person("Ollef", "Ollef","ollef@gmail.com","22225555"),new Person("Fridus", "fridus","fridus@gmail.com","22235555")));
        valgtePersoner.setItems(FXCollections.observableArrayList(new Person("Oline", "Oline","olle@gmail.com","22245555"),new Person("Frode", "frodiss","frode@gmail.com","22255555")));
        leggTilKalender.setItems(FXCollections.observableArrayList(new Person("Oline", "Oline","olle@gmail.com","22245555"),new Person("Frode", "frodiss","frode@gmail.com","22255555")));
        Person oline = leggTilKalender.getItems().get(0);
        ArrayList<Appointment> avtaler = oline.getAvtaler();
        Appointment avtale = new Appointment();
        avtale.setBeskrivelse("Kompoiss");
        avtale.setRom(123);
        avtale.setSlutt("11:13");
        avtale.setStart("11:10");
        avtale.setTitle("klasse1");
        avtale.setDate(LocalDate.now());
        avtale.setHost("stefan");
        mandag.getItems().add(avtale);
        moteinnkallinger.getItems().add(avtale);
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
		ArrayList<Person> personer = new ArrayList<Person>();
		for (Person person : valgtePersoner.getItems()) {
			personer.add(person);
		}
		Appointment avtale = new Appointment(startT.getText(),sluttT.getText(),stedNH.getText(),tittel.getText(),beskrivelse.getText(),dato.getValue(),meg, personer);
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
	//møter
	@FXML private Tooltip tips;
	@FXML private ListView<Appointment> moteinnkallinger;
	@FXML private ChoiceBox alarm;
	@FXML private TextField sluttTid;
	@FXML private TextField startTid;
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
	

	@FXML
	public void moteInfoTilView(MouseEvent event) {
		if(!moteinnkallinger.getSelectionModel().isEmpty()) {
			Appointment mote = moteinnkallinger.getSelectionModel().getSelectedItem();
			sluttTid.setText(mote.getSlutt());
			startTid.setText(mote.getStart());
			tittelM.setText(mote.getTitle());
			beskrivelseM.setText(mote.getBeskrivelse());
			meg = "stefn";
			if(mote.getHost().equals(meg)) {
				notHostValg.setVisible(false);
				hostValg.setVisible(true);
				invitertePersoner.getItems().clear();
				for (Person person : mote.getParticipants()) {
					invitertePersoner.getItems().add(person);
				}
			} else {
				hostValg.setVisible(false);
				notHostValg.setVisible(true);
				beskrivelseM.setEditable(false);
				datoM.setEditable(false);
				tittelM.setEditable(false);
				sluttTid.setEditable(false);
				startTid.setEditable(false);
				stedM.setEditable(false);
				
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
	

	public void initData(Stage stage, Gui gui, ArrayList<Person> users, ArrayList<Appointment> appointments, String meg) {
		this.stage = stage;
		this.meg = meg;
		this.gui = gui;
		this.users = users;
		for (Person person : users) {
			leggTilKalender.getItems().add(person);
		}
		this.myAppointments = appointments;
		for (Appointment appointment : appointments) {
			moteinnkallinger.getItems().add(appointment);
		}
	}
	

}
