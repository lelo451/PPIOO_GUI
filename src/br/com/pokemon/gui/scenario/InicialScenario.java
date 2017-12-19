package br.com.pokemon.gui.scenario;

import br.com.pokemon.db.Base;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.player.Humano;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.player.Maquina;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
    @FXML private JFXComboBox<Label> cbRandom1;
    @FXML private JFXComboBox<Label> cbRandom2;
    @FXML private Button btRandom;
    @FXML private JFXTextField tfParameter;
    @FXML private Button btParameter;
    @FXML private Label lbError;

    private List<Jogador> jogadores;
    private int quant;
    private AudioClip audio;

    public InicialScenario(List<Jogador> jogadores, int quant, AudioClip audio) {
        super("fxml/main_menu.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.audio = audio;
    }

    public void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");

        Image image1 = new Image("img/scenarios/logo1.png");
        Image image2 = new Image("img/scenarios/logo2.png");

        if (new Random().nextInt(100) < 51) {
            logo.setImage(image1);
        } else {
            logo.setImage(image2);
        }

        if (!jogadores.isEmpty()) {
            cbRandom1.setVisible(false);
            cbRandom2.setVisible(false);
            btRandom.setVisible(false);
        } else {
            cbRandom1.setPromptText("Jogador 1");
            cbRandom1.getItems().add(new Label("Computador"));
            cbRandom1.getItems().add(new Label("Humano"));
            cbRandom2.setPromptText("Jogador 2");
            cbRandom2.getItems().add(new Label("Computador"));
            cbRandom2.getItems().add(new Label("Humano"));
        }

        human.setOnAction(this::HandleHumanAction);
        IA.setOnAction(this::HandleComputerAction);
        btRandom.setOnAction(this::randomize);
        btParameter.setOnAction(this::handleBtParameter);
        tfParameter.setOnKeyPressed(this::handleTfParameter);
    }

    public void onConfigStage(Stage stage) {
        setUpScenarioStyle(ScenarioStyle.BETTER_UNDECORATED);
        stage.setTitle("Batalha Pokemon v1.0");
        startTime.setText("Escolha Quem Vai Controlar O Time " + (quant + 1));
        NodeCustomizer.setUpMenuBar(this, menu, exitButton, null, hideButton);
    }

    private void HandleHumanAction(ActionEvent event) {
        Scenario teamName = new TimeScenario(jogadores, quant, "H", audio);
        Spawner.startScenario(teamName, null);
        finish();
    }

    private void HandleComputerAction(ActionEvent event) {
        Scenario teamName = new TimeScenario(jogadores, quant, "C", audio);
        Spawner.startScenario(teamName, null);
        finish();
    }

    private void randomize(ActionEvent event) {
        String jog1 = " ", jog2 = " ";
        try {
            jog1 = cbRandom1.getSelectionModel().getSelectedItem().getText();
            jog2 = cbRandom2.getSelectionModel().getSelectedItem().getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Escolha Os Jogadores!");
            alert.setHeaderText(null);
            alert.setContentText("Escolha O Tipo Dos Dois Jogadores!");
            alert.showAndWait();
            btRandom.setOnAction(this::randomize);
        }
        Base b = new Base();
        for (int k = 0; k < 2; k++) {
            int poke = ThreadLocalRandom.current().nextInt(1, 6);
            List<Pokemon> pks = new ArrayList<>();
            List<Ataque> atk = new ArrayList<>();
            List<Especie> eps = new ArrayList<>();
            for (int i = 0; i < poke; i++) {
                int id = ThreadLocalRandom.current().nextInt(1, 151);
                eps.add(new Especie(id));
            }
            for (Especie ep : eps) {
                int ataques = ThreadLocalRandom.current().nextInt(1, 4);
                atk = new ArrayList<>();
                for (int j = 1; j <= ataques; j++) {
                    int ataque = ThreadLocalRandom.current().nextInt(1, 165);
                    addAtaque(ataque, atk);
                }
                int lvl = ThreadLocalRandom.current().nextInt(1, 100);
                pks.add(new Pokemon(lvl, atk, ep));
            }
            switch (k) {
                case 0:
                    switch (jog1) {
                        case "Computador":
                            Jogador jogador1 = new Maquina("IA-1", pks);
                            jogadores.add(jogador1);
                            break;
                        case "Humano":
                            Jogador jogador2 = new Humano("Player-1", pks);
                            jogadores.add(jogador2);
                            break;
                    }
                    break;
                case 1:
                    switch (jog2) {
                        case "Computador":
                            Jogador jogador1 = new Maquina("IA-2", pks);
                            jogadores.add(jogador1);
                            audio.stop();
                            Media sound = new Media(Paths.get("battle.mp3").toUri().toString());
                            AudioClip mediaPlayer = new AudioClip(sound.getSource());
                            mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
                            mediaPlayer.play();
                            Scenario battleScenario = new BattleScenario(jogadores, mediaPlayer);
                            Spawner.startScenario(battleScenario, null);
                            finish();
                            break;
                        case "Humano":
                            Jogador jogador2 = new Humano("Player-2", pks);
                            jogadores.add(jogador2);
                            audio.stop();
                            Media sound2 = new Media(Paths.get("battle.mp3").toUri().toString());
                            AudioClip mediaPlayer2 = new AudioClip(sound2.getSource());
                            mediaPlayer2.setCycleCount(AudioClip.INDEFINITE);
                            mediaPlayer2.play();
                            Scenario battleScenario2 = new BattleScenario(jogadores, mediaPlayer2);
                            Spawner.startScenario(battleScenario2, null);
                            finish();
                            break;
                    }
                    break;
            }
        }
    }

    private void handleBtParameter(ActionEvent event) {
        createTime();
    }

    private void handleTfParameter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            createTime();
        }
    }

    private void createTime() {
        jogadores = new ArrayList<>();
        List<Pokemon> pks = new ArrayList<>();
        List<Ataque> atk = new ArrayList<>();
        String parameter = tfParameter.getText();
        if(parameter.isEmpty()) {
            showError();
        } else {
            String[] all = parameter.split(" ");
            if(all.length < 16) {
                showError();
            } else {
                int controlador1 = Integer.parseInt(all[0]);
                int quantPoke1 = Integer.parseInt(all[1]), quant = 0;
                int atual = 0;
                createJogadores(quant, quantPoke1, controlador1, all, atk, pks, atual);
                atual = 2 + (pks.size() * 6);
                int controlador2 = Integer.parseInt(all[atual]);
                int quantPoke2 = Integer.parseInt(all[1 + atual]), quant2 = 0;
                atk = new ArrayList<>();
                pks = new ArrayList<>();
                createJogadores(quant2, quantPoke2, controlador2, all, atk, pks, atual);
                audio.stop();
                Media sound = new Media(Paths.get("battle.mp3").toUri().toString());
                AudioClip mediaPlayer = new AudioClip(sound.getSource());
                mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
                mediaPlayer.play();
                Scenario battleScenario = new BattleScenario(jogadores, mediaPlayer);
                Spawner.startScenario(battleScenario, null);
                finish();
            }
        }
    }

    private void createJogadores(int quant, int quantPoke, int controlador, String[] all, List<Ataque> atk, List<Pokemon> pks, int atual) {
        while (quant < quantPoke) {
            int idPoke1 = Integer.parseInt(all[((2 + atual) + (6 * quant))]);
            Especie e = new Especie(idPoke1);
            int lvl = Integer.parseInt(all[((3 + atual) + (6 * quant))]);
            int tmp = (4 + atual) + (6 * quant);
            atk = new ArrayList<>();
            for (int i = tmp; i < (tmp + 4); i++) {
                int id = Integer.parseInt(all[i]);
                if (id != 0) {
                    addAtaque(id, atk);
                }
            }
            pks.add(new Pokemon(lvl, atk, e));
            quant++;
        }
        if(controlador == 0) {
            jogadores.add(new Maquina("IA-" + (quant + 1), pks));
        } else {
            jogadores.add(new Humano("Player-" + (quant + 1), pks));
        }
    }

    private void addAtaque(int id, List<Ataque> atk) {
        Base b = new Base();
        List<String> atq = b.getData(id, false);
        switch (atq.get(6)) {
            case "charge":
                atk.add(new AtaqueCharge(id));
                break;
            case "comum":
                atk.add(new AtaqueComum(id));
                break;
            case "fixo":
                atk.add(new AtaqueFixo(id));
                break;
            case "hp":
                atk.add(new AtaqueHP(id));
                break;
            case "modifier":
                atk.add(new AtaqueModifier(id));
                break;
            case "multihit":
                atk.add(new AtaqueMultiHit(id));
                break;
            case "status":
                atk.add(new AtaqueStatus(id));
                break;
        }
    }

    private void showError() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            lbError.setVisible(true);
            lbError.setText("Número de Parametros Inválidos");
        }
    }
}