package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.player.Jogador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class InicialScenario extends Scenario {

    @FXML private Pane rootPane;
    @FXML private HBox menu;
    @FXML private Button hideButton;
    @FXML private Button exitButton;
    @FXML private Button human;
    @FXML private Button IA;
    @FXML private Label startTime;
    @FXML private Label errorLabel;
    @FXML private ImageView logo;
    private List<Jogador> jogadores;
    private int quant;

    public InicialScenario(List<Jogador> jogadores, int quant) {
        super("fxml/main_menu.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
    }

    public void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");

        Image image1 = new Image("img/scenarios/logo1.png");
        Image image2 = new Image("img/scenarios/logo2.png");

        if(new Random().nextInt(100) < 51) {
            logo.setImage(image1);
        } else {
            logo.setImage(image2);
        }

        human.setOnAction(this::HandleHumanAction);

        IA.setOnAction(this::HandleComputerAction);

    }

    public void onConfigStage(Stage stage) {
        setUpScenarioStyle(ScenarioStyle.BETTER_UNDECORATED);
        stage.setTitle("Batalha Pokemon v1.0");
        startTime.setText("Escolha Quem Vai Controlar O Time " + (quant + 1));
        NodeCustomizer.setUpMenuBar(this, menu, exitButton, null, hideButton);
    }

    private void HandleHumanAction(ActionEvent event) {
        Scenario teamName = new TimeScenario(jogadores, quant, "H");
        Spawner.startScenario(teamName, null);
        finish();
    }

    private void HandleComputerAction(ActionEvent event) {
        Scenario teamName = new TimeScenario(jogadores, quant, "C");
        Spawner.startScenario(teamName, null);
        finish();
    }
}