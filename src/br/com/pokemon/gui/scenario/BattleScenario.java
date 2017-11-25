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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
        taLog.setEditable(false);
        showInfo(1);
        showInfo(2);
        apChange.setDisable(true);
        apAtack.setDisable(true);
        verificaJogadores();
    }

    private void verificaJogadores() {
        Jogador jogador1 = jogadores.get(0);
        Jogador jogador2 = jogadores.get(1);
        if(jogador1.isMaquina() && jogador2.isMaquina()) {
            apActionJogador.setDisable(true);
            taLog.appendText(jogador1.getNome() + " Ação Escolhida!\n");
            jogador1.setAcao(Acao.ATACAR);
            destacarJogadorVez(2);
            taLog.appendText(jogador2.getNome() + " Ação Escolhida!\n");
            jogador2.setAcao(Acao.ATACAR);
        } else {
            if (jogador1.isMaquina() && !jogador2.isMaquina()) {
                taLog.appendText(jogador1.getNome() + " Ação Escolhida!\n");
                jogador1.setAcao(Acao.ATACAR);
                destacarJogadorVez(2);
                lbJogadorVez.setText(jogador2.getNome());
                taLog.appendText(jogador2.getNome() + " Selecione A Sua Ação\n");
            } else if (!jogador1.isMaquina() && jogador2.isMaquina()) {
                jogador2.setAcao(Acao.ATACAR);
                destacarJogadorVez(1);
                lbJogadorVez.setText(jogador1.getNome());
                taLog.appendText(jogador1.getNome() + " Selecione A Sua Ação\n");
            } else {
                destacarJogadorVez(1);
                lbJogadorVez.setText(jogador1.getNome());
                taLog.appendText(jogador1.getNome() + " Selecione A Sua Ação\n");
            }
            adicionarAcao();
        }
    }

    private void adicionarAcao() {
        btUseAtaque.setOnAction(this::btAtaqueAction);
        btChangePokemon.setOnAction(this::btChangeAction);
    }

    private void removerAcao() {
        btUseAtaque.setOnAction(null);
        btChangePokemon.setOnAction(null);
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
        selecionarAcao(Acao.TROCAR_POKEMON);
    }

    private void btAtaqueAction(ActionEvent event) {
        selecionarAcao(Acao.ATACAR);
    }

    public void selecionarAcao(Acao acao) {
        removerAcao();
        apChange.setDisable(true);
        apAtack.setDisable(true);
        Jogador jog1 = jogadores.get(0);
        Jogador jog2 = jogadores.get(1);
        if(jog1.getAcao() == null) {
            jog1.setAcao(acao);
            taLog.appendText(jog1.getNome() + " Ação Escolhida!\n");
            if(jog2.getAcao() == null) {
                adicionarAcao();
                destacarJogadorVez(2);
                taLog.appendText(jog2.getNome() + " Seleciona A Sua Ação!\n");
            } else {
                taLog.appendText(jog2.getNome() + " Ação Escolhida!\n");
            }
        }else if(jog2.getAcao() == null) {
            jog2.setAcao(acao);
            taLog.appendText(jog2.getNome() + " Ação Escolhida!\n");
        }
        if(jog1.getAcao() != null && jog2.getAcao() != null) {
            apActionJogador.setDisable(true);
            executarAcoes();
        }
    }

    private void executarAcoes() {
        Acao acao1 = jogadores.get(0).getAcao();
        Acao acao2 = jogadores.get(1).getAcao();
        taLog.appendText("Jogador 1: " + acao1 + " Jogador 2: " + acao2);
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