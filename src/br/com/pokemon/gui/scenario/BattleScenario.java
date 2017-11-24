package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.player.enuns.Acao;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

public class BattleScenario extends Scenario {

    @FXML
    private HBox menu;
    @FXML
    private Button hideButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label lbJogador1;
    @FXML
    private HBox hbPrev1;
    @FXML
    private ImageView poke11;
    @FXML
    private ImageView poke12;
    @FXML
    private ImageView poke13;
    @FXML
    private ImageView poke14;
    @FXML
    private ImageView poke15;
    @FXML
    private ImageView poke16;
    @FXML
    private ImageView imgJogador1;
    @FXML
    private Label lbEspecie1;
    @FXML
    private Label lbLvl1;
    @FXML
    private JFXProgressBar pgPoke1;
    @FXML
    private Label lbHp1;
    @FXML
    private Label lbAtk1;
    @FXML
    private Label lbDef1;
    @FXML
    private Label lbSpd1;
    @FXML
    private Label lbSpe1;
    @FXML
    private Label lbStatus1;
    @FXML
    private Label lbFlinch1;
    @FXML
    private Label lbConfusion1;
    @FXML
    private Label lbJogador2;
    @FXML
    private HBox hbPrev2;
    @FXML
    private ImageView poke21;
    @FXML
    private ImageView poke22;
    @FXML
    private ImageView poke23;
    @FXML
    private ImageView poke24;
    @FXML
    private ImageView poke25;
    @FXML
    private ImageView poke26;
    @FXML
    private ImageView imgJogador2;
    @FXML
    private Label lbEspecie2;
    @FXML
    private Label lbLvl2;
    @FXML
    private JFXProgressBar pgPoke2;
    @FXML
    private Label lbHp2;
    @FXML
    private Label lbAtk2;
    @FXML
    private Label lbDef2;
    @FXML
    private Label lbSpd2;
    @FXML
    private Label lbSpe2;
    @FXML
    private Label lbStatus2;
    @FXML
    private Label lbFlinch2;
    @FXML
    private Label lbConfusion2;
    @FXML
    private TextArea taLog;
    @FXML
    private Label lbJogadorVez;
    @FXML
    private Button btChangePokemon;
    @FXML
    private Button btUseAtaque;
    @FXML
    private Label lbErrorAcao;
    @FXML
    private ImageView imgChange1;
    @FXML
    private ImageView imgChange2;
    @FXML
    private ImageView imgChange3;
    @FXML
    private Button btChange;
    @FXML
    private Label lbErrorChange;
    @FXML
    private JFXComboBox<Label> cbAtaque;
    @FXML
    private Button btAtaque;
    @FXML
    private Label lbErrorAtaque;
    @FXML
    private AnchorPane pTime1;
    @FXML
    private AnchorPane pTime2;
    @FXML
    private AnchorPane apActionJogador;
    @FXML
    private AnchorPane apAcao;
    @FXML
    private AnchorPane apChange;
    @FXML
    private AnchorPane apAtack;

    private List<Jogador> jogadores;

    public BattleScenario(List<Jogador> jogadores) {
        super("fxml/battlev2.fxml");
        this.jogadores = jogadores;
    }

    public void onConfigStage(Stage stage) {
        setUpScenarioStyle(ScenarioStyle.BETTER_UNDECORATED);
        stage.setTitle("Batalha Pokemon v1.0");
        NodeCustomizer.setUpMenuBar(this, menu, exitButton, null, hideButton);
    }

    public void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
        showInfo(1);
        showInfo(2);
        apChange.setDisable(true);
        apAtack.setDisable(true);
        Jogador jogador1 = jogadores.get(0);
        Jogador jogador2 = jogadores.get(1);
        if(jogador1.isMaquina() && jogador2.isMaquina()) {
            System.out.println("Fazer o computador ficar se atacando");
            apActionJogador.setDisable(true);
            taLog.setText("Game Over!");
            jogador1.setAcao(Acao.ATACAR);
            jogador2.setAcao(Acao.ATACAR);
        } else if(jogador1.isMaquina() && !jogador2.isMaquina()) {
            System.out.println("Setar Ação Do Jogador 1");
            jogador1.setAcao(Acao.ATACAR);
            jogador2.setVez(true);
        } else if(!jogador1.isMaquina() && jogador2.isMaquina()) {
            System.out.println("Setar Ação Do Jogador 2");
            jogador1.setVez(true);
            jogador2.setAcao(Acao.ATACAR);
        } else {
            System.out.println("Batalha entre duplas");
            jogador1.setVez(true);
            jogador2.setVez(false);
        }
        if(jogador1.isVez()) {
            destacarJogadorVez(1);
            lbJogadorVez.setText(jogador1.getNome());
            taLog.setText(jogador1.getNome() + " Selecione Sua Ação!\n");
        } else {
            destacarJogadorVez(2);
            lbJogadorVez.setText(jogador2.getNome());
            taLog.setText(jogador2.getNome() + " Selecione Sua Ação!\n");
        }

    }

    private void showInfo(int player) {
        if (player == 1) {
            Jogador jogador = jogadores.get(0);
            Pokemon poke = jogador.getPokemons().get(0);
            Especie esp = poke.getEspecie();
            lbJogador1.setText(jogador.getNome());
            int id_1 = esp.getId();
            Image image_1 = new Image(padronizaValor(id_1));
            imgJogador1.setImage(image_1);
            lbEspecie1.setText(esp.getNome());
            setarProgressBar(poke, pgPoke1);
            lbLvl1.setText("LVL. " + String.valueOf(poke.getLevel()));
            lbHp1.setText(String.valueOf(poke.getHpAtual()) + "/" + String.valueOf(poke.getHpMax()));
            lbAtk1.setText(String.valueOf(poke.getAtk()));
            lbDef1.setText(String.valueOf(poke.getDef()));
            lbSpd1.setText(String.valueOf(poke.getSpd()));
            lbSpe1.setText(String.valueOf(poke.getSpe()));
            lbStatus1.setText(poke.getStatus().toString());
            lbFlinch1.setText(poke.isFlinch() ? "FLINCH" : "");
            lbConfusion1.setText(poke.isConfusion() ? "Confusion" : "");
            showPokemons(jogador, player);
        } else {
            Jogador jogador = jogadores.get(1);
            Pokemon poke = jogador.getPokemons().get(0);
            Especie esp = poke.getEspecie();
            lbJogador2.setText(jogador.getNome());
            int id_2 = esp.getId();
            Image image_2 = new Image(padronizaValor(id_2));
            imgJogador2.setImage(image_2);
            lbEspecie2.setText(esp.getNome());
            lbLvl2.setText("LVL. " + String.valueOf(poke.getLevel()));
            setarProgressBar(poke, pgPoke2);
            lbHp2.setText(String.valueOf(poke.getHpAtual()) + "/" + String.valueOf(poke.getHpMax()));
            lbAtk2.setText(String.valueOf(poke.getAtk()));
            lbDef2.setText(String.valueOf(poke.getDef()));
            lbSpd2.setText(String.valueOf(poke.getSpd()));
            lbSpe2.setText(String.valueOf(poke.getSpe()));
            lbStatus2.setText(poke.getStatus().toString());
            lbFlinch2.setText(poke.isFlinch() ? "FLINCH" : "");
            lbConfusion2.setText(poke.isConfusion() ? "Confusion" : "");
            showPokemons(jogador, player);
        }
    }

    private void setarProgressBar(Pokemon poke, JFXProgressBar pgPoke) {
        double percentageHP = poke.getHpAtual() / poke.getHpMax();
        if (percentageHP > 0.75) {
            pgPoke.getStyleClass().add("jfx-progress-bar-green");
        } else if (percentageHP > 0.5 && percentageHP <= 0.75) {
            pgPoke.getStyleClass().add("jfx-progress-bar-yellowgreen");
        } else if (percentageHP > 0.35 && percentageHP <= 0.5) {
            pgPoke.getStyleClass().add("jfx-progress-bar-yellow");
        } else if (percentageHP > 0.15 && percentageHP <= 0.35) {
            pgPoke.getStyleClass().add("jfx-progress-bar-orange");
        } else {
            pgPoke.getStyleClass().add("jfx-progress-bar-red");
        }
        pgPoke.setProgress(percentageHP);
    }

    private void showPokemons(Jogador jogador, int player) {
        int size = jogador.getPokemons().size();
        for (int i = 0; i < size; i++) {
            Image image = new Image(padronizaValor(jogador.getPokemons().get(i).getEspecie().getId()));
            switch (i + 1) {
                case 1:
                    if (player == 1)
                        poke11.setImage(image);
                    else
                        poke21.setImage(image);
                    break;
                case 2:
                    if (player == 1)
                        poke12.setImage(image);
                    else
                        poke22.setImage(image);
                    break;
                case 3:
                    if (player == 1)
                        poke13.setImage(image);
                    else
                        poke23.setImage(image);
                    break;
                case 4:
                    if (player == 1)
                        poke14.setImage(image);
                    else
                        poke24.setImage(image);
                    break;
                case 5:
                    if (player == 1)
                        poke15.setImage(image);
                    else
                        poke25.setImage(image);
                    break;
                case 6:
                    if (player == 1)
                        poke16.setImage(image);
                    else
                        poke26.setImage(image);
                    break;
            }
        }
    }

    private String padronizaValor(Integer img) {
        if (img < 10) {
            return "img/pokemons/00" + img.toString() + ".png";
        } else if (img < 100) {
            return "img/pokemons/0" + img.toString() + ".png";
        } else {
            return "img/pokemons/" + img.toString() + ".png";
        }
    }

    private void btChangeAction(ActionEvent event) {
        selecionarAcao();
    }

    private void btAtaqueAction(ActionEvent event) {
        selecionarAcao();
    }

    public void selecionarAcao() {
        apChange.setDisable(true);
        apAtack.setDisable(true);
        Jogador jog1 = jogadores.get(0);
        Jogador jog2 = jogadores.get(1);
        if(jog1.getAcao() == null) {

        }
        Jogador timeAtaque = jogadores.get(vez);
        Jogador timeDefesa = jogadores.get(espera);
        lbJogadorVez.setText(timeAtaque.getNome());

        while (acao_1[0] == 0) {
            apAcao.setDisable(false);
            if (timeAtaque.isMaquina()) {
                acao_1[0] = 2;
            } else {
                if (timeAtaque.getPokemons().size() < 2) {
                    acao_1[0] = 2;
                } else {
                    btAtaque.setOnAction(e -> acao_1[0] = 2);
                    btChange.setOnAction(e -> acao_1[0] = 1);
                }
            }
        }
        while (acao_2[0] == 0) {
            apAcao.setDisable(false);
            destacarJogadorVez(espera);
            lbJogadorVez.setText(timeDefesa.getNome());
            if (timeDefesa.isMaquina()) {
                acao_2[0] = 2;
            } else {
                if (timeDefesa.getPokemons().size() < 2) {
                    acao_2[0] = 2;
                } else {
                    btAtaque.setOnAction(e -> acao_2[0] = 2);
                    btChange.setOnAction(e -> acao_2[0] = 1);
                }
            }
        }
        destacarJogadorVez(vez);
        lbJogadorVez.setText(timeAtaque.getNome());
        apAcao.setDisable(true);
    }

    private void destacarJogadorVez(int vez) {
        switch (vez) {
            case 1:
                pTime1.setDisable(false);
                pTime1.setStyle("-fx-border-color: red; -fx-border-width: 3");
                pTime2.setDisable(true);
                pTime2.setStyle("-fx-border-color: none");
                break;
            case 2:
                pTime1.setDisable(true);
                pTime1.setStyle("-fx-border-color: none");
                pTime2.setDisable(false);
                pTime2.setStyle("-fx-border-color: red; -fx-border-width: 3");
                break;
        }
    }
}
