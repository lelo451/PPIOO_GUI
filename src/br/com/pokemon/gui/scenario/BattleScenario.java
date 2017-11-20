package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class BattleScenario extends Scenario {

    @FXML private Pane rootPane;
    @FXML private Pane messages;
    @FXML private HBox menu;
    @FXML private Button hideButton;
    @FXML private Button exitButton;
    @FXML private Label label_jog1;
    @FXML private Label label_jog2;
    @FXML private ImageView img_1;
    @FXML private ImageView img_2;

    @FXML private Label especie_1;
    @FXML private Label lvl_1;
    @FXML private Label hp_1;
    @FXML private Label atk_1;
    @FXML private Label def_1;
    @FXML private Label spd_1;
    @FXML private Label spe_1;
    @FXML private Label status_1;
    @FXML private Label flinch_1;
    @FXML private Label confusion_1;

    @FXML private Label especie_2;
    @FXML private Label lvl_2;
    @FXML private Label hp_2;
    @FXML private Label atk_2;
    @FXML private Label def_2;
    @FXML private Label spd_2;
    @FXML private Label spe_2;
    @FXML private Label status_2;
    @FXML private Label flinch_2;
    @FXML private Label confusion_2;

    private List<Jogador> jogadores;

    public BattleScenario(List<Jogador> jogadores) {
        super("fxml/battle.fxml");
        this.jogadores = jogadores;
    }

    private void showInfo(int player) {
        if(player == 1) {
            Jogador jogador = jogadores.get(0);
            Pokemon poke = jogador.getPokemons().get(0);
            Especie esp = poke.getEspecie();
            label_jog1.setText(jogador.getNome());
            int id_1 = esp.getId();
            Image image_1 = new Image(padronizaValor(id_1));
            img_1.setImage(image_1);
            especie_1.setText(esp.getNome());
            lvl_1.setText(String.valueOf(poke.getLevel()));
            hp_1.setText(String.valueOf(poke.getHpAtual()) + "/" + String.valueOf(poke.getHpMax()));
            atk_1.setText(String.valueOf(poke.getAtk()));
            def_1.setText(String.valueOf(poke.getDef()));
            spd_1.setText(String.valueOf(poke.getSpd()));
            spe_1.setText(String.valueOf(poke.getSpe()));
            status_1.setText(poke.getStatus().toString());
            flinch_1.setText(poke.isFlinch() ? "FLINCH" : "");
            confusion_1.setText(poke.isConfusion() ? "Confusion" : "");
        } else {
            Jogador jogador = jogadores.get(1);
            Pokemon poke = jogador.getPokemons().get(0);
            Especie esp = poke.getEspecie();
            label_jog2.setText(jogador.getNome());
            int id_2 = esp.getId();
            Image image_2 = new Image(padronizaValor(id_2));
            img_2.setImage(image_2);
            especie_2.setText(esp.getNome());
            lvl_2.setText(String.valueOf(poke.getLevel()));
            hp_2.setText(String.valueOf(poke.getHpAtual()) + "/" + String.valueOf(poke.getHpMax()));
            atk_2.setText(String.valueOf(poke.getAtk()));
            def_2.setText(String.valueOf(poke.getDef()));
            spd_2.setText(String.valueOf(poke.getSpd()));
            spe_2.setText(String.valueOf(poke.getSpe()));
            status_2.setText(poke.getStatus().toString());
            flinch_2.setText(poke.isFlinch() ? "FLINCH" : "");
            confusion_2.setText(poke.isConfusion() ? "Confusion" : "");
        }
    }

    private String padronizaValor(Integer img) {
        if(img < 10) {
            return "img/pokemons/00" + img.toString() + ".png";
        } else if(img < 100) {
            return "img/pokemons/0" + img.toString() + ".png";
        } else {
            return "img/pokemons/" + img.toString() + ".png";
        }
    }

    public void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
        showInfo(1);
        showInfo(2);
        Spawner.startFragment(new AcaoScenario(), this, messages);
    }

    public void onConfigStage(Stage stage) {
        setUpScenarioStyle(ScenarioStyle.BETTER_UNDECORATED);
        stage.setTitle("Batalha Pokemon v1.0");
        NodeCustomizer.setUpMenuBar(this, menu, exitButton, null, hideButton);
    }
}
