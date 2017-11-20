package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.FeedbackScenario;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.player.Humano;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.player.Maquina;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.Ataque;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;


public class LvlScenario extends FeedbackScenario {

    @FXML private HBox menuBar;
    @FXML private JFXTextField lvlPokemon;
    @FXML private Button btOK;
    @FXML private Label errorLabel;
    private List<Jogador> jogadores;
    private List<Especie> especies;
    private List<Pokemon> pks;
    private List<Ataque> atks;
    private int quant;
    private String name;
    private int pokeAtual;
    private String jogador;

    public LvlScenario(List<Jogador> jogadores, int quant, String jogador, String name, List<Especie> especies, List<Pokemon> pks, int pokeAtual, List<Ataque> atks) {
        super("fxml/lvl_pokemon.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.name = name;
        this.especies = especies;
        this.pokeAtual = pokeAtual;
        this.jogador = jogador;
        this.pks = pks;
        this.atks = atks;
    }

    @Override
    protected void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
    }

    @Override
    protected void onConfigStage(Stage stage) {
        NodeCustomizer.setUpMenuBar(this, menuBar, null, null, null);
        stage.setTitle("Level do " + especies.get(pokeAtual).getNome());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        lvlPokemon.setPromptText("Level do " + especies.get(pokeAtual).getNome());

        lvlPokemon.setOnAction(this::handleButtonAction);
        btOK.setOnAction(this::handleButtonAction);
    }

    private void handleButtonAction(ActionEvent event) {
        try {
            String texto = lvlPokemon.getText();
            int lvl = Integer.parseInt(texto);
            if(pokeAtual + 1 < especies.size()) {
                pks.add(new Pokemon(lvl, atks, especies.get(pokeAtual)));
                FeedbackScenario qtdAtaque = new QtdAtaqueScenario(jogadores, quant, jogador, name, pokeAtual + 1, especies.size(), especies, pks);
                Spawner.startFeedbackScenario(qtdAtaque, 0, this, (requestCode, resultCode, data) -> {});
                finish();
            } else {
                pks.add(new Pokemon(lvl, atks, especies.get(pokeAtual)));
                if(jogador.equals("C")) {
                    Jogador computer = new Maquina(name, pks);
                    jogadores.add(computer);
                } else {
                    Jogador humano = new Humano(name, pks);
                    jogadores.add(humano);
                }
                quant++;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Time Criado");
                alert.setHeaderText(null);
                alert.setContentText("Time " + name + " Criado Com Sucesso!");
                alert.showAndWait();
                if(quant >= 2) {
                    Scenario battleScenario = new BattleScenario(jogadores);
                    Spawner.startScenario(battleScenario, null);
                    finish();
                } else {
                    Scenario startScenario = new InicialScenario(jogadores, quant);
                    Spawner.startScenario(startScenario, null);
                    finish();
                }
            }
        } catch (Exception e) {
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }
}