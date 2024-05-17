package ma.projet.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.projet.manager.EtudiantM;
import ma.projet.model.Etudiant;

public class ControllerEtudiant {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private ComboBox<String> filiereField;
    @FXML
    private RadioButton radioM;
    @FXML
    private RadioButton radioF;
    @FXML
    private TableView<Etudiant> etudiantTable;
    @FXML
    private TableColumn<Etudiant, Integer> idColumn;
    @FXML
    private TableColumn<Etudiant, String> nomColumn;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn;
    @FXML
    private TableColumn<Etudiant, String> sexeColumn;
    @FXML
    private TableColumn<Etudiant, String> filiereColumn;

    private EtudiantM etudiantM;
    private ToggleGroup sexeGroup;

    @FXML
    private void initialize() {
        etudiantM = new EtudiantM();
        filiereField.setItems(FXCollections.observableArrayList("DSI", "RSI", "SEM", "MDW"));

        sexeGroup = new ToggleGroup();
        radioM.setToggleGroup(sexeGroup);
        radioF.setToggleGroup(sexeGroup);

        // Set up the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        filiereColumn.setCellValueFactory(new PropertyValueFactory<>("filiere"));

        // Load existing data into the table view
        loadDataIntoTableView();
    }

    @FXML
    private void handleAddEtudiant() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String filiere = filiereField.getValue();
        String sexe = getSelectedSexe();

        if (nom.isEmpty() || prenom.isEmpty() || filiere == null || sexe == null) {
            // Show error message or handle invalid input
            return;
        }

        // Create a new Etudiant object and add it to the database
        Etudiant newEtudiant = new Etudiant(nom, prenom, sexe, filiere);
        etudiantM.create(newEtudiant);

        // Refresh table view after adding the new entry
        loadDataIntoTableView();

        // Clear input fields
        clearInputFields();
    }

    private String getSelectedSexe() {
        RadioButton selectedRadioButton = (RadioButton) sexeGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText();
        }
        return null;
    }

    private void loadDataIntoTableView() {
        try {
            ObservableList<Etudiant> etudiantsList = FXCollections.observableArrayList(etudiantM.findAll());
            etudiantTable.setItems(etudiantsList);
            etudiantTable.refresh(); // Refresh TableView to display data
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }
    }

    private void clearInputFields() {
        nomField.clear();
        prenomField.clear();
        filiereField.getSelectionModel().clearSelection();
        sexeGroup.selectToggle(null);
    }

    @FXML
    private void deleteEtudiant() {
        Etudiant selectedEtudiant = etudiantTable.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            etudiantM.delete(selectedEtudiant);
            loadDataIntoTableView();
        }
    }

    @FXML
    private void modifierEtudiant() {
        Etudiant selectedEtudiant = etudiantTable.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String filiere = filiereField.getValue();
            String sexe = getSelectedSexe();

            if (nom.isEmpty() || prenom.isEmpty() || filiere == null || sexe == null) {
                // Show error message or handle invalid input
                return;
            }

            // Update the selectedEtudiant object
            selectedEtudiant.setNom(nom);
            selectedEtudiant.setPrenom(prenom);
            selectedEtudiant.setFiliere(filiere);
            selectedEtudiant.setSexe(sexe);

            // Update the database
            etudiantM.update(selectedEtudiant);

            // Refresh table view after modifying the entry
            loadDataIntoTableView();

            // Clear input fields
            clearInputFields();
        }
    }
}
