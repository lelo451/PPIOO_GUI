package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.FeedbackScenario;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.player.Jogador;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class TeamNameScenario extends FeedbackScenario {

    @FXML private HBox menuBar;
    @FXML private JFXTextField teamName;
    @FXML private Button btOK;
    @FXML private Label errorLabel;
    private List<Jogador> jogadores;
    private int quant;
    private String jogador;

    public TeamNameScenario(List<Jogador> jogadores, int quant, String jogador) {
        super("fxml/team_name.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.jogador = jogador;
    }

    @Override
    protected void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
    }

    @Override
    protected void onConfigStage(Stage stage) {
        NodeCustomizer.setUpMenuBar(this, menuBar, null, null, null);
        stage.setTitle("Nome do Time");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        teamName.setPromptText("Nome do Time " + (quant + 1) );

        teamName.setOnAction(this::handleButtonAction);
        btOK.setOnAction(this::handleButtonAction);
    }

    private void handleButtonAction(ActionEvent event) {
        try {
            String name = " ";
            if(!teamName.getText().isEmpty())
                name = teamName.getText();
            else
                throw new RuntimeException();
            FeedbackScenario qtdPokemon = new QtdPokeScenario(jogadores, quant, jogador, name);
            Spawner.startFeedbackScenario(qtdPokemon, 0, this, (requestCode, resultCode, data) -> {});
            finish();
        } catch (Exception e) {
            errorLabel.setVisible(true);
        }
    }
}