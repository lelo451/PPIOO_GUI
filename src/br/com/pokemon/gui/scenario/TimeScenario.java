package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.Ataque;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TimeScenario extends Scenario {

    @FXML private HBox menuBar;
    @FXML private AnchorPane pTeamName;
    @FXML private JFXTextField tfName;
    @FXML private Label lbErrorName;
    @FXML private Label lbName;
    @FXML private AnchorPane pQuantidadePokemon;
    @FXML private JFXComboBox<?> cbQtdPokemon;
    @FXML private Button btQtdPokemon;
    @FXML private Label lbErrorQtdPokemon;
    @FXML private AnchorPane pEspecie;
    @FXML private Label lbEspecie;
    @FXML private TableView<?> tvEspecie;
    @FXML private TableColumn<?, ?> tcEspecie;
    @FXML private Button btEspecie;
    @FXML private Label lbErrorEspecie;
    @FXML private AnchorPane pQuantidadeAtaque;
    @FXML private JFXComboBox<?> cbQtdAtaque;
    @FXML private Button btQtdAtaque;
    @FXML private Label lbErrorQtdAtaque;
    @FXML private AnchorPane pEspecie1;
    @FXML private Label lbAtaque;
    @FXML private TableView<?> tvAtaque;
    @FXML private TableColumn<?, ?> tcAtaque;
    @FXML private Button btAtaque;
    @FXML private Label lbErrorAtaque;
    @FXML private AnchorPane pTeamName1;
    @FXML private JFXTextField tfLevel;
    @FXML private Label lbErrorLevel;
    @FXML private Label lbLevel;
    @FXML private Button btOK;

    private List<Jogador> jogadores;
    private List<Especie> especies = new ArrayList<>();
    private List<Pokemon> pokemons = new ArrayList<>();
    private List<Ataque> ataques = new ArrayList<>();
    private String name;
    private String tipoJogador;
    private int quant;

    public TimeScenario(List<Jogador> jogadores, int quant, String tipoJogador) {
        super("fxml/time.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.tipoJogador = tipoJogador;
    }

    @Override
    protected void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
    }

    public void onConfigStage(Stage stage) {
        setUpScenarioStyle(ScenarioStyle.BETTER_UNDECORATED);
        stage.setTitle("Monte O Seu Time");
        NodeCustomizer.setUpMenuBar(this, menuBar, null, null, null);

        btAtaque.setDisable(true);
        tvAtaque.setDisable(true);
        tvAtaque.setPlaceholder(new Label(""));
        lbErrorEspecie.setVisible(true);
        lbErrorEspecie.setText("Selecione A Quantidade De Pokemons");
    }

    /*
    private void handleFinishCreateTeamAction(ActionEvent event) {
        try {
            name = tfName.getText();
        } catch (Exception e) {
            lbErrorName.setVisible(true);
        }
    }
    */
}