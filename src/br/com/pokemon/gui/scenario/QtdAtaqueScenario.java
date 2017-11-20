package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.FeedbackScenario;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.Ataque;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;


public class QtdAtaqueScenario extends FeedbackScenario {

    @FXML private HBox menuBar;
    @FXML private JFXComboBox<Label> qtdPokemon;
    @FXML private Button btOK;
    @FXML private Label errorLabel;
    private List<Jogador> jogadores;
    private List<Especie> especies;
    private List<Pokemon> pks;
    private List<Ataque> atks = new ArrayList<>();
    private int quant;
    private String name;
    private int min;
    private int max;
    private String jogador;

    public QtdAtaqueScenario(List<Jogador> jogadores, int quant, String jogador, String name, int min, int max, List<Especie> especies, List<Pokemon> pks) {
        super("fxml/quantidade_pokemon.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.name = name;
        this.min = min;
        this.max = max;
        this.especies = especies;
        this.jogador = jogador;
        this.pks = pks;
    }

    @Override
    protected void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
    }

    @Override
    protected void onConfigStage(Stage stage) {
        NodeCustomizer.setUpMenuBar(this, menuBar, null, null, null);
        stage.setTitle("Quantidade de Ataques");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        qtdPokemon.getItems().add(new Label("1"));
        qtdPokemon.getItems().add(new Label("2"));
        qtdPokemon.getItems().add(new Label("3"));
        qtdPokemon.getItems().add(new Label("4"));

        qtdPokemon.setPromptText("Selecione a Quantidade de Ataque Para o " + especies.get(min).getNome());

        qtdPokemon.setOnKeyPressed(this::handleKeyAction);
        btOK.setOnAction(this::handleButtonAction);
    }

    private void handleButtonAction(ActionEvent event) {
        executeAction(event);
    }

    private void handleKeyAction(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            executeAction(event);
        }
    }

    private void executeAction(Event event) {
        try {
            String texto = qtdPokemon.getSelectionModel().getSelectedItem().getText();
            int total = Integer.parseInt(texto);
            FeedbackScenario ataqueScenario = new AtaqueScenario(jogadores, quant, jogador, name, 0, total, especies, pks, min, atks);
            Spawner.startFeedbackScenario(ataqueScenario, 0, this, (requestCode, resultCode, data) -> {});
            finish();
        } catch (Exception e) {
            errorLabel.setVisible(true);
        }
    }
}