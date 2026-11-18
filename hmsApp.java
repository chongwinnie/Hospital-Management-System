import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class hmsApp extends Application {

	// Doctor Variables
    private Doctor[] doctors = new Doctor[25];
    private int doctorCount = 0;

    private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
    private FilteredList<Doctor> filteredDoctors = new FilteredList<>(doctorList, d -> true);

    private TableView<Doctor> doctorTable;
    private TextField idField, nameField, specField, timeField, qualField, roomField;
    private Button addBtn, saveBtn, deleteBtn, clearFormBtn;
    private ComboBox<String> searchMode;
    private TextField searchField;
    private Label roleLabel;
    
    // Patient Variables
    private Patient[] patients = new Patient[100];
    private int patientCount = 0;

    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private FilteredList<Patient> filteredPatients = new FilteredList<>(patientList, p -> true);

    private TableView<Patient> patientTable;
    private TextField pidField, pnameField, pdiseaseField, psexField, pstatusField, pageField;
    private Button paddBtn, psaveBtn, pdeleteBtn, pclearFormBtn;
    private ComboBox<String> psearchMode;
    private TextField psearchField;
    
    // Medicine Variables
    private Medicine[] medicines = new Medicine[100];
    private int medicineCount = 0;

    private ObservableList<Medicine> medicineList = FXCollections.observableArrayList();
    private FilteredList<Medicine> filteredMedicines = new FilteredList<>(medicineList, m -> true);

    private TableView<Medicine> medicineTable;
    private TextField mnameField, mmanuField, mexpiryField, mcostField, mcountField;
    private Button maddBtn, msaveBtn, mdeleteBtn, mclearFormBtn;
    private ComboBox<String> msearchMode;
    private TextField msearchField;
    
    // Lab Variables
    private Lab[] labs = new Lab[20];
    private int labCount = 0;

    private ObservableList<Lab> labList = FXCollections.observableArrayList();
    private FilteredList<Lab> filteredLabs = new FilteredList<>(labList, l -> true);

    private TableView<Lab> labTable;
    private TextField labNameField, labCostField;
    private Button labAddBtn, labSaveBtn, labDeleteBtn, labClearBtn;
    private ComboBox<String> labSearchMode;
    private TextField labSearchField;

    // Facility Variables
    private Facility[] facilities = new Facility[20];
    private int facilityCount = 0;

    private ObservableList<Facility> facilityList = FXCollections.observableArrayList();
    private FilteredList<Facility> filteredFacilities = new FilteredList<>(facilityList, f -> true);

    private TableView<Facility> facilityTable;
    private TextField fNameField;
    private Button fAddBtn, fSaveBtn, fDeleteBtn, fClearBtn;
    private ComboBox<String> fSearchMode;
    private TextField fSearchField;
    
    // ===== Staff Variables =====
    private Staff[] staffs = new Staff[100];
    private int staffCount = 0;

    private ObservableList<Staff> staffList = FXCollections.observableArrayList();
    private FilteredList<Staff> filteredStaffs = new FilteredList<>(staffList, s -> true);

    private TableView<Staff> staffTable;
    private TextField sidField, snameField, sdesignationField, ssexField, ssalaryField;
    private Button saddBtn, ssaveBtn, sdeleteBtn, sclearFormBtn;
    private ComboBox<String> ssearchMode;
    private TextField ssearchField;

    // Login Role
    private String currentRole = "staff";

    @Override
    public void start(Stage primaryStage) {
        showLoginScene(primaryStage);
    }

    private void showLoginScene(Stage stage) {
        GridPane g = new GridPane();
        g.setPadding(new Insets(30));
        g.setHgap(15);
        g.setVgap(15);
        g.setAlignment(Pos.CENTER);
        g.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f4f7, #d9e2ec);"
                 + "-fx-border-radius: 10");

        Label title = new Label("Hospital Management System - Login");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        g.add(title, 0, 0, 2, 1);
        GridPane.setHalignment(title, HPos.CENTER);

        Label userLabel = new Label("Username:");
        userLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField userField = new TextField();
        userField.setPromptText("admin / staff");
        userField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5;");

        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        PasswordField passField = new PasswordField();
        passField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5;");

        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        loginBtn.setStyle(
            "-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-background-radius: 5; -fx-padding: 6 12 6 12;"
        );

        g.add(userLabel, 0, 1);
        g.add(userField, 1, 1);
        g.add(passLabel, 0, 2);
        g.add(passField, 1, 2);
        g.add(loginBtn, 1, 3);
        GridPane.setHalignment(loginBtn, HPos.RIGHT);

        loginBtn.setOnAction(e -> {
            String u = userField.getText().trim();
            String p = passField.getText().trim();
            if (u.isEmpty()) { showAlert("Username cannot be empty."); return; }
            if (p.isEmpty()) { showAlert("Password cannot be empty."); return; }

            if ("admin".equalsIgnoreCase(u) && "admin123".equals(p)) {
                currentRole = "admin";
            } else if ("staff".equalsIgnoreCase(u) && "staff123".equals(p)) {
                currentRole = "staff";
            } else {
                currentRole = "staff";
                showAlert("Unknown credentials. Defaulting to STAFF role.");
            }

            loadSampleDoctors();
            loadSamplePatients();
            loadSampleMedicines();
            loadSampleLabs();
            loadSampleFacilities();
            loadSampleStaff();
            showMainScene(stage);
        });

        Scene s = new Scene(g, 450, 260);
        stage.setTitle("HMS - Login");
        stage.setScene(s);
        stage.show();
    }

    /* ---------------- Main Scene ---------------- */
    private void showMainScene(Stage stage) {
        BorderPane root = new BorderPane();

        HBox top = new HBox();
        top.setPadding(new Insets(10));
        Label header = new Label("Hospital Management System (HMS)");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        roleLabel = new Label("Role: " + currentRole.toUpperCase());
        roleLabel.setStyle("-fx-font-size: 12px; -fx-font-style: italic;");
        top.getChildren().addAll(header, spacer, roleLabel);

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab doctorsTab = new Tab("Doctors", buildDoctorsPane());
        Tab patientsTab = new Tab("Patients", buildPatientsPane());
        Tab medicinesTab = new Tab("Medicines", buildMedicinesPane());
        Tab labsTab = new Tab("Laboratories", buildLabsPane());
        Tab facilitiesTab = new Tab("Facilities", buildFacilityPane());
        Tab staffTab = new Tab("Staff", buildStaffPane());

        tabs.getTabs().addAll(doctorsTab, patientsTab, medicinesTab, labsTab, facilitiesTab, staffTab);

        root.setTop(top);
        root.setCenter(tabs);

        Scene scene = new Scene(root, 950, 600);
        stage.setTitle("HMS - Main (Logged in as " + currentRole.toUpperCase() + ")");
        stage.setScene(scene);
        stage.show();
    }

    /* ---------------- Doctors Pane ---------------- */
    @SuppressWarnings("unchecked")
	private BorderPane buildDoctorsPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        // Table
        doctorTable = new TableView<>();
        doctorTable.setItems(filteredDoctors);

        TableColumn<Doctor, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(80);

        TableColumn<Doctor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(160);

        TableColumn<Doctor, String> specCol = new TableColumn<>("Specialist");
        specCol.setCellValueFactory(new PropertyValueFactory<>("specialist"));
        specCol.setPrefWidth(180);

        TableColumn<Doctor, String> timeCol = new TableColumn<>("Work Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("workTime"));
        timeCol.setPrefWidth(100);

        TableColumn<Doctor, String> qualCol = new TableColumn<>("Qualification");
        qualCol.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        qualCol.setPrefWidth(140);

        TableColumn<Doctor, Integer> roomCol = new TableColumn<>("Room");
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        roomCol.setPrefWidth(80);

        doctorTable.getColumns().addAll(idCol, nameCol, specCol, timeCol, qualCol, roomCol);

        // Form (Grid)
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        idField = new TextField(); idField.setPromptText("DXXX");
        nameField = new TextField(); nameField.setPromptText("Full name");
        specField = new TextField(); specField.setPromptText("E.g. Cardiology");
        timeField = new TextField(); timeField.setPromptText("E.g. 9-5");
        qualField = new TextField(); qualField.setPromptText("E.g. MBBS");
        roomField = new TextField(); roomField.setPromptText("Room no");

        form.add(new Label("ID:"), 0, 0); form.add(idField, 1, 0);
        form.add(new Label("Name:"), 2, 0); form.add(nameField, 3, 0);
        form.add(new Label("Specialist:"), 0, 1); form.add(specField, 1, 1);
        form.add(new Label("Work Time:"), 2, 1); form.add(timeField, 3, 1);
        form.add(new Label("Qualification:"), 0, 2); form.add(qualField, 1, 2);
        form.add(new Label("Room No:"), 2, 2); form.add(roomField, 3, 2);

        addBtn = new Button("Add Doctor");
        saveBtn = new Button("Save Changes");
        deleteBtn = new Button("Remove Doctor");
        clearFormBtn = new Button("New / Clear");

        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);

        HBox actions = new HBox(8, addBtn, saveBtn, deleteBtn, clearFormBtn);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(8, 0, 0, 0));

        VBox bottomBox = new VBox(6, form, actions);
        bottomBox.setPadding(new Insets(6));

        // Search & Sort area
        HBox searchArea = new HBox(8);
        searchMode = new ComboBox<>();
        searchMode.getItems().addAll("ID", "Name");
        searchMode.setValue("Name");
        searchField = new TextField();
        searchField.setPromptText("Search text");
        Button searchBtn = new Button("Search");
        Button clearSearchBtn = new Button("Clear Search");
        Button sortNameBtn = new Button("Sort by Name");
        Button sortRoomBtn = new Button("Sort by Room");

        searchArea.getChildren().addAll(new Label("Search:"), searchMode, searchField, searchBtn, clearSearchBtn,
                new Region(), sortNameBtn, sortRoomBtn);
        HBox.setHgrow(searchArea.getChildren().get(6), Priority.ALWAYS); // spacer

        pane.setTop(searchArea);
        pane.setCenter(doctorTable);
        pane.setBottom(bottomBox);

        // Event handlers
        addBtn.setOnAction(e -> handleAddDoctor());
        saveBtn.setOnAction(e -> handleSaveChanges());
        deleteBtn.setOnAction(e -> handleDeleteDoctor());
        clearFormBtn.setOnAction(e -> clearFormAndSelection());

        doctorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromSelected(newSel);
                addBtn.setDisable(true);
                saveBtn.setDisable(false);
                deleteBtn.setDisable(false);
            } else {
                clearFormAndSelection();
            }
        });

        searchBtn.setOnAction(e -> handleSearch());
        clearSearchBtn.setOnAction(e -> {
            filteredDoctors.setPredicate(d -> true);
            searchField.clear();
        });

        sortNameBtn.setOnAction(e -> {
            FXCollections.sort(doctorList, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            // table shows sorted list automatically
        });

        sortRoomBtn.setOnAction(e -> {
            FXCollections.sort(doctorList, (a, b) -> Integer.compare(a.getRoom(), b.getRoom()));
        });

        return pane;
    }
    
    /* ---------------- Patients Pane ---------------- */
    @SuppressWarnings("unchecked")
	private BorderPane buildPatientsPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        // Table
        patientTable = new TableView<>();
        patientTable.setItems(filteredPatients);

        TableColumn<Patient, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(80);

        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(160);

        TableColumn<Patient, String> diseaseCol = new TableColumn<>("Disease");
        diseaseCol.setCellValueFactory(new PropertyValueFactory<>("disease"));
        diseaseCol.setPrefWidth(140);

        TableColumn<Patient, String> sexCol = new TableColumn<>("Sex");
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        sexCol.setPrefWidth(80);

        TableColumn<Patient, String> statusCol = new TableColumn<>("Admit Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("admitStatus"));
        statusCol.setPrefWidth(120);

        TableColumn<Patient, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageCol.setPrefWidth(60);

        patientTable.getColumns().addAll(idCol, nameCol, diseaseCol, sexCol, statusCol, ageCol);

        // Form
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        pidField = new TextField(); pidField.setPromptText("PXXX");
        pnameField = new TextField(); pnameField.setPromptText("Full name");
        pdiseaseField = new TextField(); pdiseaseField.setPromptText("Disease");
        psexField = new TextField(); psexField.setPromptText("Sex");
        pstatusField = new TextField(); pstatusField.setPromptText("Admitted / Discharged");
        pageField = new TextField(); pageField.setPromptText("Age");

        form.add(new Label("ID:"), 0, 0); form.add(pidField, 1, 0);
        form.add(new Label("Name:"), 2, 0); form.add(pnameField, 3, 0);
        form.add(new Label("Disease:"), 0, 1); form.add(pdiseaseField, 1, 1);
        form.add(new Label("Sex:"), 2, 1); form.add(psexField, 3, 1);
        form.add(new Label("Admit Status:"), 0, 2); form.add(pstatusField, 1, 2);
        form.add(new Label("Age:"), 2, 2); form.add(pageField, 3, 2);

        paddBtn = new Button("Add Patient");
        psaveBtn = new Button("Save Changes");
        pdeleteBtn = new Button("Remove Patient");
        pclearFormBtn = new Button("New / Clear");

        psaveBtn.setDisable(true);
        pdeleteBtn.setDisable(true);

        HBox actions = new HBox(8, paddBtn, psaveBtn, pdeleteBtn, pclearFormBtn);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(8, 0, 0, 0));

        VBox bottomBox = new VBox(6, form, actions);
        bottomBox.setPadding(new Insets(6));

        // Search & Sort area
        HBox searchArea = new HBox(8);
        psearchMode = new ComboBox<>();
        psearchMode.getItems().addAll("ID", "Name", "Disease");
        psearchMode.setValue("Name");
        psearchField = new TextField();
        psearchField.setPromptText("Search text");
        Button searchBtn = new Button("Search");
        Button clearSearchBtn = new Button("Clear Search");
        Button sortNameBtn = new Button("Sort by Name");
        Button sortAgeBtn = new Button("Sort by Age");

        searchArea.getChildren().addAll(new Label("Search:"), psearchMode, psearchField, searchBtn, clearSearchBtn,
                new Region(), sortNameBtn, sortAgeBtn);
        HBox.setHgrow(searchArea.getChildren().get(6), Priority.ALWAYS);

        pane.setTop(searchArea);
        pane.setCenter(patientTable);
        pane.setBottom(bottomBox);

        // Event handlers
        paddBtn.setOnAction(e -> handleAddPatient());
        psaveBtn.setOnAction(e -> handleSavePatient());
        pdeleteBtn.setOnAction(e -> handleDeletePatient());
        pclearFormBtn.setOnAction(e -> clearPatientForm());

        patientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populatePatientForm(newSel);
                paddBtn.setDisable(true);
                psaveBtn.setDisable(false);
                pdeleteBtn.setDisable(false);
            } else {
                clearPatientForm();
            }
        });

        searchBtn.setOnAction(e -> handlePatientSearch());
        clearSearchBtn.setOnAction(e -> {
            filteredPatients.setPredicate(p -> true);
            psearchField.clear();
        });

        sortNameBtn.setOnAction(e -> FXCollections.sort(patientList, (a, b) -> a.getName().compareToIgnoreCase(b.getName())));
        sortAgeBtn.setOnAction(e -> FXCollections.sort(patientList, (a, b) -> Integer.compare(a.getAge(), b.getAge())));

        return pane;
    }
    
    /* ---------------- Medicines Pane ---------------- */
    @SuppressWarnings("unchecked")
	private BorderPane buildMedicinesPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        // Table
        medicineTable = new TableView<>();
        medicineTable.setItems(filteredMedicines);

        TableColumn<Medicine, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(160);

        TableColumn<Medicine, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setPrefWidth(160);

        TableColumn<Medicine, String> expCol = new TableColumn<>("Expiry");
        expCol.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        expCol.setPrefWidth(100);

        TableColumn<Medicine, Integer> costCol = new TableColumn<>("Cost (RM)");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        costCol.setPrefWidth(80);

        TableColumn<Medicine, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        stockCol.setPrefWidth(80);

        medicineTable.getColumns().addAll(nameCol, manuCol, expCol, costCol, stockCol);

        // Form
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        mnameField = new TextField(); mnameField.setPromptText("Name");
        mmanuField = new TextField(); mmanuField.setPromptText("Manufacturer");
        mexpiryField = new TextField(); mexpiryField.setPromptText("YYYY-MM");
        mcostField = new TextField(); mcostField.setPromptText("Cost (RM)");
        mcountField = new TextField(); mcountField.setPromptText("Stock");

        form.add(new Label("Name:"), 0, 0); form.add(mnameField, 1, 0);
        form.add(new Label("Manufacturer:"), 2, 0); form.add(mmanuField, 3, 0);
        form.add(new Label("Expiry:"), 0, 1); form.add(mexpiryField, 1, 1);
        form.add(new Label("Cost:"), 2, 1); form.add(mcostField, 3, 1);
        form.add(new Label("Stock:"), 0, 2); form.add(mcountField, 1, 2);

        maddBtn = new Button("Add Medicine");
        msaveBtn = new Button("Save Changes");
        mdeleteBtn = new Button("Remove Medicine");
        mclearFormBtn = new Button("New / Clear");

        msaveBtn.setDisable(true);
        mdeleteBtn.setDisable(true);

        HBox actions = new HBox(8, maddBtn, msaveBtn, mdeleteBtn, mclearFormBtn);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(8, 0, 0, 0));

        VBox bottomBox = new VBox(6, form, actions);
        bottomBox.setPadding(new Insets(6));

        // Search & Sort
        HBox searchArea = new HBox(8);
        msearchMode = new ComboBox<>();
        msearchMode.getItems().addAll("Name", "Manufacturer");
        msearchMode.setValue("Name");
        msearchField = new TextField();
        msearchField.setPromptText("Search text");
        Button searchBtn = new Button("Search");
        Button clearSearchBtn = new Button("Clear Search");
        Button sortNameBtn = new Button("Sort by Name");
        Button sortStockBtn = new Button("Sort by Stock");

        searchArea.getChildren().addAll(new Label("Search:"), msearchMode, msearchField, searchBtn, clearSearchBtn,
                new Region(), sortNameBtn, sortStockBtn);
        HBox.setHgrow(searchArea.getChildren().get(6), Priority.ALWAYS);

        pane.setTop(searchArea);
        pane.setCenter(medicineTable);
        pane.setBottom(bottomBox);

        // Event handlers
        maddBtn.setOnAction(e -> handleAddMedicine());
        msaveBtn.setOnAction(e -> handleSaveMedicine());
        mdeleteBtn.setOnAction(e -> handleDeleteMedicine());
        mclearFormBtn.setOnAction(e -> clearMedicineForm());

        medicineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateMedicineForm(newSel);
                maddBtn.setDisable(true);
                msaveBtn.setDisable(false);
                mdeleteBtn.setDisable(false);
            } else {
                clearMedicineForm();
            }
        });

        searchBtn.setOnAction(e -> handleMedicineSearch());
        clearSearchBtn.setOnAction(e -> {
            filteredMedicines.setPredicate(m -> true);
            msearchField.clear();
        });

        sortNameBtn.setOnAction(e -> FXCollections.sort(medicineList, (a, b) -> a.getName().compareToIgnoreCase(b.getName())));
        sortStockBtn.setOnAction(e -> FXCollections.sort(medicineList, (a, b) -> Integer.compare(a.getCount(), b.getCount())));

        return pane;
    }

    /* ---------------- Laboratories Pane ---------------- */
    @SuppressWarnings("unchecked")
	private BorderPane buildLabsPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        // Table
        labTable = new TableView<>();
        labTable.setItems(filteredLabs);

        TableColumn<Lab, String> nameCol = new TableColumn<>("Lab Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("lab"));
        nameCol.setPrefWidth(200);

        TableColumn<Lab, Integer> costCol = new TableColumn<>("Cost (RM)");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        costCol.setPrefWidth(100);

        labTable.getColumns().addAll(nameCol, costCol);

        // Form
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        labNameField = new TextField(); labNameField.setPromptText("Lab Name");
        labCostField = new TextField(); labCostField.setPromptText("Cost (RM)");

        form.add(new Label("Lab Name:"), 0, 0); form.add(labNameField, 1, 0);
        form.add(new Label("Cost (RM):"), 0, 1); form.add(labCostField, 1, 1);

        labAddBtn = new Button("Add Lab");
        labSaveBtn = new Button("Save Changes");
        labDeleteBtn = new Button("Remove Lab");
        labClearBtn = new Button("New / Clear");

        labSaveBtn.setDisable(true);
        labDeleteBtn.setDisable(true);

        HBox actions = new HBox(8, labAddBtn, labSaveBtn, labDeleteBtn, labClearBtn);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(8, 0, 0, 0));

        VBox bottomBox = new VBox(6, form, actions);
        bottomBox.setPadding(new Insets(6));

        // Search & Sort
        HBox searchArea = new HBox(8);
        labSearchMode = new ComboBox<>();
        labSearchMode.getItems().addAll("Lab Name");
        labSearchMode.setValue("Lab Name");
        labSearchField = new TextField();
        labSearchField.setPromptText("Search text");
        Button searchBtn = new Button("Search");
        Button clearSearchBtn = new Button("Clear Search");
        Button sortNameBtn = new Button("Sort by Name");
        Button sortCostBtn = new Button("Sort by Cost (RM)");

        searchArea.getChildren().addAll(new Label("Search:"), labSearchMode, labSearchField, searchBtn, clearSearchBtn,
                new Region(), sortNameBtn, sortCostBtn);
        HBox.setHgrow(searchArea.getChildren().get(6), Priority.ALWAYS); // spacer

        pane.setTop(searchArea);
        pane.setCenter(labTable);
        pane.setBottom(bottomBox);

        // Event handlers
        labAddBtn.setOnAction(e -> handleAddLab());
        labSaveBtn.setOnAction(e -> handleSaveLab());
        labDeleteBtn.setOnAction(e -> handleDeleteLab());
        labClearBtn.setOnAction(e -> clearLabForm());

        labTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateLabForm(newSel);
                labAddBtn.setDisable(true);
                labSaveBtn.setDisable(false);
                labDeleteBtn.setDisable(false);
            } else {
                clearLabForm();
            }
        });

        searchBtn.setOnAction(e -> handleLabSearch());
        clearSearchBtn.setOnAction(e -> {
            filteredLabs.setPredicate(l -> true);
            labSearchField.clear();
        });

        sortNameBtn.setOnAction(e -> FXCollections.sort(labList, (a,b) -> a.getLab().compareToIgnoreCase(b.getLab())));
        sortCostBtn.setOnAction(e -> FXCollections.sort(labList, (a,b) -> Integer.compare(a.getCost(), b.getCost())));

        return pane;
    }
    
    /* ---------------- Facilities Pane ---------------- */
    private BorderPane buildFacilityPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        // Table
        facilityTable = new TableView<>();
        facilityTable.setItems(filteredFacilities);

        TableColumn<Facility, String> nameCol = new TableColumn<>("Facility Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("facility"));
        nameCol.setPrefWidth(300);

        facilityTable.getColumns().add(nameCol);

        // Form
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        fNameField = new TextField(); 
        fNameField.setPromptText("Facility Name");

        form.add(new Label("Facility Name:"), 0, 0);
        form.add(fNameField, 1, 0);

        fAddBtn = new Button("Add Facility");
        fSaveBtn = new Button("Save Changes");
        fDeleteBtn = new Button("Remove Facility");
        fClearBtn = new Button("New / Clear");

        fSaveBtn.setDisable(true);
        fDeleteBtn.setDisable(true);

        HBox actions = new HBox(8, fAddBtn, fSaveBtn, fDeleteBtn, fClearBtn);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(8, 0, 0, 0));

        VBox bottomBox = new VBox(6, form, actions);
        bottomBox.setPadding(new Insets(6));

        // Search & Sort
        HBox searchArea = new HBox(8);
        fSearchMode = new ComboBox<>();
        fSearchMode.getItems().addAll("Name");
        fSearchMode.setValue("Name");

        fSearchField = new TextField();
        fSearchField.setPromptText("Search text");

        Button searchBtn = new Button("Search");
        Button clearSearchBtn = new Button("Clear Search");
        Button sortNameBtn = new Button("Sort by Name");

        searchArea.getChildren().addAll(new Label("Search:"), fSearchMode, fSearchField, searchBtn, clearSearchBtn,
                new Region(), sortNameBtn);
        HBox.setHgrow(searchArea.getChildren().get(6), Priority.ALWAYS);

        pane.setTop(searchArea);
        pane.setCenter(facilityTable);
        pane.setBottom(bottomBox);

        // Event handlers
        fAddBtn.setOnAction(e -> handleAddFacility());
        fSaveBtn.setOnAction(e -> handleSaveFacility());
        fDeleteBtn.setOnAction(e -> handleDeleteFacility());
        fClearBtn.setOnAction(e -> clearFacilityForm());

        facilityTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFacilityForm(newSel);
                fAddBtn.setDisable(true);
                fSaveBtn.setDisable(false);
                fDeleteBtn.setDisable(false);
            } else {
                clearFacilityForm();
            }
        });

        searchBtn.setOnAction(e -> handleFacilitySearch());
        clearSearchBtn.setOnAction(e -> {
            filteredFacilities.setPredicate(f -> true);
            fSearchField.clear();
        });

        sortNameBtn.setOnAction(e -> FXCollections.sort(facilityList, 
            (a, b) -> a.getFacility().compareToIgnoreCase(b.getFacility())
        ));

        return pane;
    }
    
    /* ---------------- Staff Pane ---------------- */
    @SuppressWarnings("unchecked")
	private BorderPane buildStaffPane() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        // Table
        staffTable = new TableView<>();
        staffTable.setItems(filteredStaffs);

        TableColumn<Staff, String> sidCol = new TableColumn<>("ID");
        sidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        sidCol.setPrefWidth(80);

        TableColumn<Staff, String> snameCol = new TableColumn<>("Name");
        snameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        snameCol.setPrefWidth(160);

        TableColumn<Staff, String> sdesignationCol = new TableColumn<>("Designation");
        sdesignationCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getRole())
        );
        sdesignationCol.setPrefWidth(140);

        TableColumn<Staff, String> ssexCol = new TableColumn<>("Sex");
        ssexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        ssexCol.setPrefWidth(60);

        TableColumn<Staff, Integer> ssalaryCol = new TableColumn<>("Salary (RM)");
        ssalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ssalaryCol.setPrefWidth(100);

        staffTable.getColumns().addAll(sidCol, snameCol, sdesignationCol, ssexCol, ssalaryCol);

        // Form (Grid)
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        sidField = new TextField(); sidField.setPromptText("SXXX");
        snameField = new TextField(); snameField.setPromptText("Full name");
        sdesignationField = new TextField();
        sdesignationField.setPromptText("Auto-assigned by ID");
        sdesignationField.setEditable(false);
        sdesignationField.setFocusTraversable(false);
        ssexField = new TextField(); ssexField.setPromptText("M/F");
        ssalaryField = new TextField(); ssalaryField.setPromptText("Salary (RM)");

        form.add(new Label("ID:"), 0, 0); form.add(sidField, 1, 0);
        form.add(new Label("Name:"), 2, 0); form.add(snameField, 3, 0);
        form.add(new Label("Designation:"), 0, 1); form.add(sdesignationField, 1, 1);
        form.add(new Label("Sex:"), 2, 1); form.add(ssexField, 3, 1);
        form.add(new Label("Salary (RM):"), 0, 2); form.add(ssalaryField, 1, 2);

        saddBtn = new Button("Add Staff");
        ssaveBtn = new Button("Save Changes");
        sdeleteBtn = new Button("Remove Staff");
        sclearFormBtn = new Button("New / Clear");

        ssaveBtn.setDisable(true);
        sdeleteBtn.setDisable(true);

        HBox actions = new HBox(8, saddBtn, ssaveBtn, sdeleteBtn, sclearFormBtn);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(8, 0, 0, 0));

        VBox bottomBox = new VBox(6, form, actions);
        bottomBox.setPadding(new Insets(6));

        // Search & Sort area
        HBox searchArea = new HBox(8);
        ssearchMode = new ComboBox<>();
        ssearchMode.getItems().addAll("ID", "Name", "Designation");
        ssearchMode.setValue("Name");
        ssearchField = new TextField();
        ssearchField.setPromptText("Search text");
        Button searchBtn = new Button("Search");
        Button clearSearchBtn = new Button("Clear Search");
        Button sortNameBtn = new Button("Sort by Name");
        Button sortSalaryBtn = new Button("Sort by Salary");

        searchArea.getChildren().addAll(new Label("Search:"), ssearchMode, ssearchField, searchBtn, clearSearchBtn,
                new Region(), sortNameBtn, sortSalaryBtn);
        HBox.setHgrow(searchArea.getChildren().get(6), Priority.ALWAYS); // spacer

        pane.setTop(searchArea);
        pane.setCenter(staffTable);
        pane.setBottom(bottomBox);

        // Event handlers
        saddBtn.setOnAction(e -> handleAddStaff());
        ssaveBtn.setOnAction(e -> handleSaveStaffChanges());
        sdeleteBtn.setOnAction(e -> handleDeleteStaff());
        sclearFormBtn.setOnAction(e -> clearStaffFormAndSelection());

        staffTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateStaffFormFromSelected(newSel);
                saddBtn.setDisable(true);
                ssaveBtn.setDisable(false);
                sdeleteBtn.setDisable(false);
            } else {
                clearStaffFormAndSelection();
            }
        });

        searchBtn.setOnAction(e -> handleStaffSearch());
        clearSearchBtn.setOnAction(e -> {
            filteredStaffs.setPredicate(s -> true);
            ssearchField.clear();
        });

        sortNameBtn.setOnAction(e -> {
            FXCollections.sort(staffList, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        });

        sortSalaryBtn.setOnAction(e -> {
            FXCollections.sort(staffList, (a, b) -> Integer.compare(a.getSalary(), b.getSalary()));
        });

        return pane;
    }
    
    /* ---------------- Handlers & Helpers ---------------- */
    
    //Doctor Features
    private void handleAddDoctor() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String spec = specField.getText().trim();
        String time = timeField.getText().trim();
        String qual = qualField.getText().trim();
        String roomS = roomField.getText().trim();

        // validations
        if (id.isEmpty() || name.isEmpty() || spec.isEmpty() || time.isEmpty() || qual.isEmpty() || roomS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }
        int roomNo;
        try {
            roomNo = Integer.parseInt(roomS);
            if (roomNo <= 0) { showAlert("Room number must be positive."); return; }
        } catch (NumberFormatException ex) {
            showAlert("Room must be a valid integer.");
            return;
        }
        // capacity
        if (doctorList.size() >= doctors.length) {
            showAlert("Cannot add doctor: maximum capacity reached.");
            return;
        }
        // unique ID check
        for (Doctor d : doctorList) {
            if (d.getId() != null && d.getId().equalsIgnoreCase(id)) {
                showAlert("Doctor ID already exists.");
                return;
            }
        }

        Doctor d = new Doctor(id, name, spec, time, qual, roomNo);
        doctorList.add(d);
        syncArrayFromList();
        clearFormAndSelection();
    }

    private void handleSaveChanges() {
        Doctor sel = doctorTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No doctor selected to update."); return; }

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String spec = specField.getText().trim();
        String time = timeField.getText().trim();
        String qual = qualField.getText().trim();
        String roomS = roomField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || spec.isEmpty() || time.isEmpty() || qual.isEmpty() || roomS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }
        int roomNo;
        try {
            roomNo = Integer.parseInt(roomS);
            if (roomNo <= 0) { showAlert("Room must be positive."); return; }
        } catch (NumberFormatException ex) {
            showAlert("Room must be a valid integer.");
            return;
        }

        // If ID changed, ensure it's not duplicate of another record
        if (!sel.getId().equalsIgnoreCase(id)) {
            for (Doctor d : doctorList) {
                if (d != sel && d.getId() != null && d.getId().equalsIgnoreCase(id)) {
                    showAlert("Another record already has that ID.");
                    return;
                }
            }
        }

        // apply changes
        sel.setId(id);
        sel.setName(name);
        sel.setSpecialist(spec);
        sel.setWorkTime(time);
        sel.setQualification(qual);
        sel.setRoom(roomNo);

        doctorTable.refresh();
        syncArrayFromList();
        clearFormAndSelection();
    }

    private void handleDeleteDoctor() {
        Doctor sel = doctorTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No doctor selected to delete."); return; }

        Alert confirm = new Alert(
        	    Alert.AlertType.CONFIRMATION,
        	    "Confirm deletion of selected doctor?",
        	    ButtonType.YES, ButtonType.NO
        	);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                doctorList.remove(sel);
                syncArrayFromList();
                clearFormAndSelection();
            }
        });
    }

    private void handleSearch() {
        String mode = searchMode.getValue();
        String q = searchField.getText().trim();
        if (q.isEmpty()) {
            showAlert("Enter search text.");
            return;
        }
        if ("ID".equals(mode)) {
            filteredDoctors.setPredicate(d -> d.getId() != null && d.getId().equalsIgnoreCase(q));
        } else { // Name contains
            String low = q.toLowerCase();
            filteredDoctors.setPredicate(d -> d.getName() != null && d.getName().toLowerCase().contains(low));
        }
    }

    private void populateFormFromSelected(Doctor d) {
        if (d == null) return;
        idField.setText(d.getId());
        nameField.setText(d.getName());
        specField.setText(d.getSpecialist());
        timeField.setText(d.getWorkTime());
        qualField.setText(d.getQualification());
        roomField.setText(String.valueOf(d.getRoom()));
    }

    private void clearFormAndSelection() {
        doctorTable.getSelectionModel().clearSelection();
        idField.clear(); nameField.clear(); specField.clear(); timeField.clear(); qualField.clear(); roomField.clear();
        addBtn.setDisable(false);
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    private void syncArrayFromList() {
        doctorCount = doctorList.size();
        for (int i = 0; i < doctorCount && i < doctors.length; i++) {
            doctors[i] = doctorList.get(i);
        }
        for (int i = doctorCount; i < doctors.length; i++) {
            doctors[i] = null;
        }
    }
    
    //Patient Features
    private void handleAddPatient() {
        String id = pidField.getText().trim();
        String name = pnameField.getText().trim();
        String disease = pdiseaseField.getText().trim();
        String sex = psexField.getText().trim();
        String status = pstatusField.getText().trim();
        String ageS = pageField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || disease.isEmpty() || sex.isEmpty() || status.isEmpty() || ageS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageS);
            if (age <= 0) { showAlert("Age must be positive."); return; }
        } catch (NumberFormatException ex) {
            showAlert("Age must be an integer.");
            return;
        }
        for (Patient p : patientList) {
            if (p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                showAlert("Patient ID already exists.");
                return;
            }
        }

        Patient p = new Patient(id, name, disease, sex, status, age);
        patientList.add(p);
        syncPatients();
        clearPatientForm();
    }

    private void handleSavePatient() {
        Patient sel = patientTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No patient selected."); return; }

        String id = pidField.getText().trim();
        String name = pnameField.getText().trim();
        String disease = pdiseaseField.getText().trim();
        String sex = psexField.getText().trim();
        String status = pstatusField.getText().trim();
        String ageS = pageField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || disease.isEmpty() || sex.isEmpty() || status.isEmpty() || ageS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageS);
            if (age <= 0) { showAlert("Age must be positive."); return; }
        } catch (NumberFormatException ex) {
            showAlert("Age must be an integer.");
            return;
        }
        if (!sel.getId().equalsIgnoreCase(id)) {
            for (Patient p : patientList) {
                if (p != sel && p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                    showAlert("Another patient already has that ID.");
                    return;
                }
            }
        }

        sel.setId(id);
        sel.setName(name);
        sel.setDisease(disease);
        sel.setSex(sex);
        sel.setAdmitStatus(status);
        sel.setAge(age);

        patientTable.refresh();
        syncPatients();
        clearPatientForm();
    }

    private void handleDeletePatient() {
        Patient sel = patientTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No patient selected."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected patient?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                patientList.remove(sel);
                syncPatients();
                clearPatientForm();
            }
        });
    }

    private void handlePatientSearch() {
        String mode = psearchMode.getValue();
        String q = psearchField.getText().trim();
        if (q.isEmpty()) { showAlert("Enter search text."); return; }

        if ("ID".equals(mode)) {
            filteredPatients.setPredicate(p -> p.getId() != null && p.getId().equalsIgnoreCase(q));
        } else if ("Disease".equals(mode)) {
            String low = q.toLowerCase();
            filteredPatients.setPredicate(p -> p.getDisease() != null && p.getDisease().toLowerCase().contains(low));
        } else {
            String low = q.toLowerCase();
            filteredPatients.setPredicate(p -> p.getName() != null && p.getName().toLowerCase().contains(low));
        }
    }

    private void populatePatientForm(Patient p) {
        pidField.setText(p.getId());
        pnameField.setText(p.getName());
        pdiseaseField.setText(p.getDisease());
        psexField.setText(p.getSex());
        pstatusField.setText(p.getAdmitStatus());
        pageField.setText(String.valueOf(p.getAge()));
    }

    private void clearPatientForm() {
        patientTable.getSelectionModel().clearSelection();
        pidField.clear(); pnameField.clear(); pdiseaseField.clear(); psexField.clear(); pstatusField.clear(); pageField.clear();
        paddBtn.setDisable(false);
        psaveBtn.setDisable(true);
        pdeleteBtn.setDisable(true);
    }

    private void syncPatients() {
        patientCount = patientList.size();
        for (int i = 0; i < patientCount && i < patients.length; i++) {
            patients[i] = patientList.get(i);
        }
        for (int i = patientCount; i < patients.length; i++) {
            patients[i] = null;
        }
    }
    
    // Medicine Features
    private void handleAddMedicine() {
        String name = mnameField.getText().trim();
        String manu = mmanuField.getText().trim();
        String exp = mexpiryField.getText().trim();
        String costS = mcostField.getText().trim();
        String stockS = mcountField.getText().trim();

        if (name.isEmpty() || manu.isEmpty() || exp.isEmpty() || costS.isEmpty() || stockS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }
        int cost, stock;
        try { cost = Integer.parseInt(costS); } catch (Exception ex) { showAlert("Cost must be number."); return; }
        try { stock = Integer.parseInt(stockS); } catch (Exception ex) { showAlert("Stock must be number."); return; }

        for (Medicine m : medicineList) {
            if (m.getName().equalsIgnoreCase(name)) {
                showAlert("Medicine with that name already exists.");
                return;
            }
        }

        Medicine m = new Medicine(name, manu, exp, cost, stock);
        medicineList.add(m);
        syncMedicines();
        clearMedicineForm();
    }

    private void handleSaveMedicine() {
        if (!currentRole.equalsIgnoreCase("admin")) {
            showAlert("Only ADMIN can update medicines.");
            return;
        }
        Medicine sel = medicineTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No medicine selected."); return; }

        String name = mnameField.getText().trim();
        String manu = mmanuField.getText().trim();
        String exp = mexpiryField.getText().trim();
        String costS = mcostField.getText().trim();
        String stockS = mcountField.getText().trim();

        if (name.isEmpty() || manu.isEmpty() || exp.isEmpty() || costS.isEmpty() || stockS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }
        int cost, stock;
        try { cost = Integer.parseInt(costS); } catch (Exception ex) { showAlert("Cost must be number."); return; }
        try { stock = Integer.parseInt(stockS); } catch (Exception ex) { showAlert("Stock must be number."); return; }

        sel.setName(name);
        sel.setManufacturer(manu);
        sel.setExpiryDate(exp);
        sel.setCost(cost);
        sel.setCount(stock);

        medicineTable.refresh();
        syncMedicines();
        clearMedicineForm();
    }

    private void handleDeleteMedicine() {
        if (!currentRole.equalsIgnoreCase("admin")) {
            showAlert("Only ADMIN can delete medicines.");
            return;
        }
        Medicine sel = medicineTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No medicine selected."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected medicine?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                medicineList.remove(sel);
                syncMedicines();
                clearMedicineForm();
            }
        });
    }

    private void handleMedicineSearch() {
        String mode = msearchMode.getValue();
        String q = msearchField.getText().trim();
        if (q.isEmpty()) { showAlert("Enter search text."); return; }

        if ("Manufacturer".equals(mode)) {
            String low = q.toLowerCase();
            filteredMedicines.setPredicate(m -> m.getManufacturer().toLowerCase().contains(low));
        } else {
            String low = q.toLowerCase();
            filteredMedicines.setPredicate(m -> m.getName().toLowerCase().contains(low));
        }
    }

    private void populateMedicineForm(Medicine m) {
        mnameField.setText(m.getName());
        mmanuField.setText(m.getManufacturer());
        mexpiryField.setText(m.getExpiryDate());
        mcostField.setText(String.valueOf(m.getCost()));
        mcountField.setText(String.valueOf(m.getCount()));
    }

    private void clearMedicineForm() {
        medicineTable.getSelectionModel().clearSelection();
        mnameField.clear(); mmanuField.clear(); mexpiryField.clear();
        mcostField.clear(); mcountField.clear();
        maddBtn.setDisable(false);
        msaveBtn.setDisable(true);
        mdeleteBtn.setDisable(true);
    }

    private void syncMedicines() {
        medicineCount = medicineList.size();
        for (int i = 0; i < medicineCount && i < medicines.length; i++) {
            medicines[i] = medicineList.get(i);
        }
        for (int i = medicineCount; i < medicines.length; i++) {
            medicines[i] = null;
        }
    }

    //Lab Features
    private void handleAddLab() {
        String name = labNameField.getText().trim();
        String costS = labCostField.getText().trim();

        if (name.isEmpty() || costS.isEmpty()) { showAlert("All fields are required."); return; }

        int cost;
        try { cost = Integer.parseInt(costS); if (cost <= 0) { showAlert("Cost must be positive."); return; } } 
        catch (Exception ex) { showAlert("Cost must be a number."); return; }

        for (Lab l : labList) {
            if (l.getLab().equalsIgnoreCase(name)) { showAlert("Lab name already exists."); return; }
        }

        Lab l = new Lab(name, cost);
        labList.add(l);
        syncLabs();
        clearLabForm();
    }

    private void handleSaveLab() {
        Lab sel = labTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No lab selected."); return; }

        String name = labNameField.getText().trim();
        String costS = labCostField.getText().trim();

        if (name.isEmpty() || costS.isEmpty()) { showAlert("All fields are required."); return; }

        int cost;
        try { cost = Integer.parseInt(costS); if (cost <= 0) { showAlert("Cost must be positive."); return; } } 
        catch (Exception ex) { showAlert("Cost must be a number."); return; }

        // Ensure unique name
        if (!sel.getLab().equalsIgnoreCase(name)) {
            for (Lab l : labList) { if (l != sel && l.getLab().equalsIgnoreCase(name)) { showAlert("Lab name already exists."); return; } }
        }

        sel.setLab(name);
        sel.setCost(cost);
        labTable.refresh();
        syncLabs();
        clearLabForm();
    }

    private void handleDeleteLab() {
        Lab sel = labTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No lab selected."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected lab?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                labList.remove(sel);
                syncLabs();
                clearLabForm();
            }
        });
    }

    private void handleLabSearch() {
        String q = labSearchField.getText().trim();
        if (q.isEmpty()) { showAlert("Enter search text."); return; }

        filteredLabs.setPredicate(l -> l.getLab() != null && l.getLab().toLowerCase().contains(q.toLowerCase()));
    }

    private void populateLabForm(Lab l) {
        labNameField.setText(l.getLab());
        labCostField.setText(String.valueOf(l.getCost()));
    }

    private void clearLabForm() {
        labTable.getSelectionModel().clearSelection();
        labNameField.clear();
        labCostField.clear();
        labAddBtn.setDisable(false);
        labSaveBtn.setDisable(true);
        labDeleteBtn.setDisable(true);
    }

    private void syncLabs() {
        labCount = labList.size();
        for (int i = 0; i < labCount && i < labs.length; i++) {
            labs[i] = labList.get(i);
        }
        for (int i = labCount; i < labs.length; i++) {
            labs[i] = null;
        }
    }
    
    // Facility Features
    private void handleAddFacility() {
        String name = fNameField.getText().trim();
        if (name.isEmpty()) { showAlert("Facility name cannot be empty."); return; }

        for (Facility f : facilityList) {
            if (f.getFacility().equalsIgnoreCase(name)) {
                showAlert("Facility already exists.");
                return;
            }
        }

        Facility f = new Facility(name);
        facilityList.add(f);
        syncFacilities();
        clearFacilityForm();
    }

    private void handleSaveFacility() {
        Facility sel = facilityTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No facility selected."); return; }

        String name = fNameField.getText().trim();
        if (name.isEmpty()) { showAlert("Facility name cannot be empty."); return; }

        // check duplicate
        for (Facility f : facilityList) {
            if (f != sel && f.getFacility().equalsIgnoreCase(name)) {
                showAlert("Another facility already has that name.");
                return;
            }
        }

        sel.setFacility(name);
        facilityTable.refresh();
        syncFacilities();
        clearFacilityForm();
    }

    private void handleDeleteFacility() {
        Facility sel = facilityTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No facility selected."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected facility?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                facilityList.remove(sel);
                syncFacilities();
                clearFacilityForm();
            }
        });
    }

    private void handleFacilitySearch() {
        String q = fSearchField.getText().trim();
        if (q.isEmpty()) { showAlert("Enter search text."); return; }

        filteredFacilities.setPredicate(f -> f.getFacility() != null && f.getFacility().toLowerCase().contains(q.toLowerCase()));
    }

    private void populateFacilityForm(Facility f) {
        fNameField.setText(f.getFacility());
    }

    private void clearFacilityForm() {
        facilityTable.getSelectionModel().clearSelection();
        fNameField.clear();
        fAddBtn.setDisable(false);
        fSaveBtn.setDisable(true);
        fDeleteBtn.setDisable(true);
    }

    private void syncFacilities() {
        facilityCount = facilityList.size();
        for (int i = 0; i < facilityCount && i < facilities.length; i++) {
            facilities[i] = facilityList.get(i);
        }
        for (int i = facilityCount; i < facilities.length; i++) {
            facilities[i] = null;
        }
    }
    
    // Staff Features
    private void handleAddStaff() {
        String id = sidField.getText().trim();
        String name = snameField.getText().trim();
        String sex = ssexField.getText().trim();
        String salaryS = ssalaryField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || sex.isEmpty() || salaryS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        int salary;
        try {
            salary = Integer.parseInt(salaryS);
            if (salary <= 0) { showAlert("Salary must be positive."); return; }
        } catch (NumberFormatException ex) {
            showAlert("Salary must be a valid integer.");
            return;
        }

        if (staffList.size() >= staffs.length) {
            showAlert("Cannot add staff: maximum capacity reached.");
            return;
        }

        // unique ID check
        for (Staff s : staffList) {
            if (s.getId() != null && s.getId().equalsIgnoreCase(id)) {
                showAlert("Staff ID already exists.");
                return;
            }
        }

        Staff s;
        if (id.startsWith("N")) {
            s = new Nurses(id, name, sex, salary);
        } else if (id.startsWith("PH")) {
            s = new Pharmacists(id, name, sex, salary);
        } else if (id.startsWith("S")) {
            s = new Security(id, name, sex, salary);
        } else {
            showAlert("Invalid staff ID prefix. Use N..., PH..., or S...");
            return;
        }

        staffList.add(s);
        syncStaff();
        clearStaffFormAndSelection();
    }

    private void handleSaveStaffChanges() {
        Staff sel = staffTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No staff selected to update."); return; }

        String id = sidField.getText().trim();
        String name = snameField.getText().trim();
        String sex = ssexField.getText().trim();
        String salaryS = ssalaryField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || sex.isEmpty() || salaryS.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        int salary;
        try {
            salary = Integer.parseInt(salaryS);
            if (salary <= 0) { showAlert("Salary must be positive."); return; }
        } catch (NumberFormatException ex) {
            showAlert("Salary must be a valid integer.");
            return;
        }

        // check unique ID if changed
        if (!sel.getId().equalsIgnoreCase(id)) {
            for (Staff s : staffList) {
                if (s != sel && s.getId() != null && s.getId().equalsIgnoreCase(id)) {
                    showAlert("Another record already has that ID.");
                    return;
                }
            }
        }

        sel.setId(id);
        sel.setName(name);
        sel.setSex(sex);
        sel.setSalary(salary);

        staffTable.refresh();
        syncStaff();
        clearStaffFormAndSelection();
    }

    private void handleDeleteStaff() {
        Staff sel = staffTable.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("No staff selected to delete."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm deletion of selected staff?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                staffList.remove(sel);
                syncStaff();
                clearStaffFormAndSelection();
            }
        });
    }

    private void handleStaffSearch() {
        String mode = ssearchMode.getValue();
        String q = ssearchField.getText().trim();
        if (q.isEmpty()) { showAlert("Enter search text."); return; }

        if ("ID".equals(mode)) {
            filteredStaffs.setPredicate(s -> s.getId() != null && s.getId().equalsIgnoreCase(q));
        } else if ("Name".equals(mode)) {
            String low = q.toLowerCase();
            filteredStaffs.setPredicate(s -> s.getName() != null && s.getName().toLowerCase().contains(low));
        } else { // Designation
        	String low = q.toLowerCase();
            filteredStaffs.setPredicate(s -> s.getRole() != null && s.getRole().toLowerCase().contains(low));
        }
    }

    private void populateStaffFormFromSelected(Staff s) {
        if (s == null) return;
        sidField.setText(s.getId());
        snameField.setText(s.getName());
        ssexField.setText(s.getSex());
        ssalaryField.setText(String.valueOf(s.getSalary()));
        sdesignationField.setText(s.getRole());
    }

    private void clearStaffFormAndSelection() {
        staffTable.getSelectionModel().clearSelection();
        sidField.clear();
        snameField.clear();
        ssexField.clear();
        ssalaryField.clear();
        saddBtn.setDisable(false);
        ssaveBtn.setDisable(true);
        sdeleteBtn.setDisable(true);
    }

    private void syncStaff() {
        staffCount = staffList.size();
        for (int i = 0; i < staffCount && i < staffs.length; i++) {
            staffs[i] = staffList.get(i);
        }
        for (int i = staffCount; i < staffs.length; i++) {
            staffs[i] = null;
        }
    }
    
    // Load Sample Data 
    // ===== Patients =====
    private void loadSampleDoctors() {
        doctorList.clear();
        doctorList.addAll(
                new Doctor("D271", "Chong Min Hing",     "Cardiology",   "9-5",  "MBBS", 101),
                new Doctor("D895", "Chong Win Nie",     "Neurology",   "10-6",  "MD", 102),
        		new Doctor("D001", "Ali",     "Cardiology",   "9-5",  "MBBS", 103),
                new Doctor("D002", "Siti",    "Neurology",    "10-6", "MD",   104),
                new Doctor("D003", "John",    "Orthopedics",  "11-7", "PhD",  105),
                new Doctor("D004", "Amina",   "Pediatrics",   "8-4",  "MBBS", 106),
                new Doctor("D005", "Ravi",    "Dermatology",  "12-8", "MD",   107),
                new Doctor("D006", "Mei",     "Oncology",     "9-3",  "FRCS", 108),
                new Doctor("D007", "Omar",    "ENT",          "10-7", "MBBS", 109),
                new Doctor("D008", "Grace",   "Gynecology",   "9-5",  "MD",   110),
                new Doctor("D009", "Chen",    "Radiology",    "8-2",  "PhD",  111),
                new Doctor("D010", "Samuel",  "Psychiatry",   "1-9",  "MMed", 112)
        );
        syncArrayFromList();
    }
    
    // ===== Patients =====
    private void loadSamplePatients() {
        patientList.clear();
        patientList.addAll(
        		new Patient("P001", "Ahmad",    "Fever",     "M", "Admitted", 24),
        		new Patient("P002", "Aisha",    "Flu",       "F", "Released", 30),
        		new Patient("P003", "Michael",  "Asthma",    "M", "Admitted", 40),
        		new Patient("P004", "Nurul",    "Diabetes",  "F", "Admitted", 55),
        		new Patient("P005", "Hassan",   "Hypertension","M","Released",62),
        		new Patient("P006", "Priya",    "Migraine",  "F", "Admitted", 19),
        		new Patient("P007", "Daniel",   "Fracture",  "M", "Released", 33),
        		new Patient("P008", "Zara",     "Allergy",   "F", "Admitted", 27),
        		new Patient("P009", "Victor",   "COVID-19",  "M", "Released", 46),
        		new Patient("P010", "Lily",     "Ulcer",     "F", "Admitted", 72)
        );
        syncPatients();
    }
    
    // ===== Medicines =====
    private void loadSampleMedicines() {
        medicineList.clear();
        medicineList.addAll(
            new Medicine("Paracetamol",   "PharmaA", "2026-01",   5, 100),
            new Medicine("Amoxicillin",   "PharmaB", "2025-12",  10,  50),
            new Medicine("Ventolin",      "PharmaC", "2027-06",  20,  30),
            new Medicine("Ibuprofen",     "PharmaD", "2026-05",   8, 200),
            new Medicine("Insulin",       "PharmaE", "2025-10", 150,  15),  
            new Medicine("Omeprazole",    "PharmaF", "2026-12",  12,  80),
            new Medicine("Cough Syrup",   "PharmaG", "2025-08",   7,  12),  
            new Medicine("Aspirin",       "PharmaH", "2025-11",   3,   5), 
            new Medicine("Vitamin C",     "PharmaI", "2027-03",  10, 300),
            new Medicine("Azithromycin",  "PharmaJ", "2025-12",  18,  25)
        );
        syncMedicines();
    }
    
    // ===== Laboratories =====
    private void loadSampleLabs(){
        labList.clear();
        labList.addAll(
            new Lab("Blood Test",           100),
            new Lab("X-Ray",                200),
            new Lab("MRI",                  500),
            new Lab("Ultrasound",           250),
            new Lab("CT Scan",              600),
            new Lab("Urine Test",            60),
            new Lab("ECG",                  180),
            new Lab("Echocardiogram",       350),
            new Lab("Liver Function Test",  140),
            new Lab("COVID PCR",            220)
        );
        syncLabs();
    }
    
 // ===== Facilities =====
    private void loadSampleFacilities() {
        facilityList.clear();
        facilityList.addAll(
            new Facility("Emergency Ward"),
            new Facility("ICU"),
            new Facility("Pharmacy"),
            new Facility("Maternity Ward"),
            new Facility("Operation Theater"),
            new Facility("General Ward"),
            new Facility("Outpatient Department (OPD)"),
            new Facility("Cafeteria"),
            new Facility("Billing Counter"),
            new Facility("Waiting Area")
        );
        syncFacilities();
    }
    
    // ===== Staff =====
    private void loadSampleStaff() {
        staffList.clear(); // ObservableList for TableView

        staffList.addAll(
            new Nurses("N001", "Nora", "F", 3000),
            new Pharmacists("PH001", "James", "M", 4000),
            new Security("S001", "Farah", "F", 2000),
            new Nurses("N002", "Rahman", "M", 2800),
            new Pharmacists("PH002", "Emily", "F", 3200),
            new Security("S002", "Kumar", "M", 1800),
            new Nurses("N003", "Aqilah", "F", 3500),
            new Pharmacists("PH003", "Ben", "M", 3800),
            new Security("S003", "Sophia", "F", 2200),
            new Nurses("N004", "Hakim", "M", 3100)
        );
        syncStaff();
    }

    /* ---------------- Utilities ---------------- */
    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
