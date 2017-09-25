package com.seapip.thomas.woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.seapip.thomas.woordenapplicatie.Words;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.*;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class Controller implements Initializable {

    private static final String DEFAULT_TEXT = "Een, twee, drie, vier\n" +
            "Hoedje van, hoedje van\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "Heb je dan geen hoedje meer\n" +
            "Maak er één van bordpapier\n" +
            "Eén, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van, hoedje van\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier\n" +
            "\n" +
            "En als het hoedje dan niet past\n" +
            "Zetten we 't in de glazenkas\n" +
            "Een, twee, drie, vier\n" +
            "Hoedje van papier";

    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }

    @FXML
    private void aantalAction(ActionEvent event) {
        taOutput.setText("Total words: " + String.valueOf(Words.count(taInput.getText()))
                + "\n" + "Unique words: " + String.valueOf(Words.countUnique(taInput.getText())));
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
        taOutput.setText(String.join(" ", Words.sortReverse(taInput.getText())));
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        Map<String, Integer> frequency = Words.frequencyAlternative(taInput.getText());
        String text = "";
        for (String word : frequency.keySet()) {
            text += word + ": " + frequency.get(word).toString() + "\n";
        }
        taOutput.setText(text);
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        Map<String, Collection<Integer>> concordance = Words.concordance(taInput.getText());
        String text = "";
        for (String word : concordance.keySet()) {
            List<Integer> rowNumbers = new ArrayList<>(concordance.get(word));
            Collections.sort(rowNumbers);
            List<String> rows = new ArrayList<>();
            for (int row : rowNumbers) {
                rows.add(String.valueOf(row));
            }
            text += word + ": " + String.join(", ", rows) + "\n";
        }
        taOutput.setText(text);
    }
}
