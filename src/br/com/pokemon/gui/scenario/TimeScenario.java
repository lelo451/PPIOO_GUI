package br.com.pokemon.gui.scenario;

import br.com.pokemon.db.Base;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.gui.visible.ObservableAtaque;
import br.com.pokemon.gui.visible.ObservableEspecie;
import br.com.pokemon.player.Humano;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.player.Maquina;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TimeScenario extends Scenario {

    @FXML private HBox menuBar;
    @FXML private AnchorPane pTeamName;
    @FXML private JFXTextField tfName;
    @FXML private Label lbErrorName;
    @FXML private Label lbName;
    @FXML private AnchorPane pQuantidadePokemon;
    @FXML private JFXComboBox<Label> cbQtdPokemon;
    @FXML private Button btQtdPokemon;
    @FXML private Label lbErrorQtdPokemon;
    @FXML private AnchorPane pEspecie;
    @FXML private Label lbEspecie;
    @FXML private TableView<ObservableEspecie> tvEspecie;
    @FXML private TableColumn<ObservableEspecie, String> tcEspecie;
    @FXML private Button btEspecie;
    @FXML private Label lbErrorEspecie;
    @FXML private AnchorPane pQuantidadeAtaque;
    @FXML private JFXComboBox<Label> cbQtdAtaque;
    @FXML private Button btQtdAtaque;
    @FXML private Label lbErrorQtdAtaque;
    @FXML private AnchorPane pEspecie1;
    @FXML private Label lbAtaque;
    @FXML private TableView<ObservableAtaque> tvAtaque;
    @FXML private TableColumn<ObservableAtaque, String> tcAtaque;
    @FXML private Button btAtaque;
    @FXML private Label lbErrorAtaque;
    @FXML private AnchorPane pTeamName1;
    @FXML private JFXTextField tfLevel;
    @FXML private Label lbErrorLevel;
    @FXML private Label lbLevel;
    @FXML private Button btOK;
    @FXML private Button btLevel;
    @FXML private Button btName;
    @FXML private ObservableList<ObservableEspecie> especieObservableList;
    @FXML private ObservableList<ObservableAtaque> ataquesObservableList;
    @FXML private ImageView pokePreview1;
    @FXML private ImageView pokePreview2;
    @FXML private ImageView pokePreview3;
    @FXML private ImageView pokePreview4;
    @FXML private ImageView pokePreview5;
    @FXML private ImageView pokePreview6;


    private List<Jogador> jogadores;
    private List<Especie> especies = new ArrayList<>();
    private List<Pokemon> pokemons = new ArrayList<>();
    private List<Ataque> ataques;
    private String name;
    private String tipoJogador;
    private int quant;
    private AudioClip audio;

    public TimeScenario(List<Jogador> jogadores, int quant, String tipoJogador, AudioClip audio) {
        super("fxml/time.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.tipoJogador = tipoJogador;
        this.audio = audio;
    }

    @Override
    protected void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
    }

    public void onConfigStage(Stage stage) {
        setUpScenarioStyle(ScenarioStyle.BETTER_UNDECORATED);
        stage.setTitle("Monte O Seu Time");
        NodeCustomizer.setUpMenuBar(this, menuBar, null, null, null);
        segundoPlano();
        tfName.setPromptText("Nome Do Jogador Do Time " + (quant + 1));
        pokePreview1.setImage(null);
        tvEspecie.setPlaceholder(new Label("Carregando Pokemons..."));
        tvAtaque.setPlaceholder(new Label("Carregando Ataques..."));
        btQtdPokemon.setOnAction(this::handleQtdPokemonAction);
        tfName.setOnKeyPressed(this::handleNameFieldAction);
        btName.setOnAction(this::handleNameAction);
        cbQtdPokemon.setOnKeyPressed(this::handleQtdPokemonCBAction);
        btOK.setOnAction(this::finishChoice);
    }

    private void finishChoice(ActionEvent event) {
        if(tipoJogador.equals("H")) {
            Jogador j = new Humano(name, pokemons);
            jogadores.add(j);
        } else {
            Jogador j = new Maquina(name + " [IA]", pokemons);
            jogadores.add(j);
        }
        quant++;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time Criado");
        alert.setHeaderText(null);
        alert.setContentText("Time " + name + " Criado Com Sucesso!");
        alert.showAndWait();
        if(quant >= 2) {
            audio.stop();
            Media sound = new Media(Paths.get("battle.mp3").toUri().toString());
            AudioClip mediaPlayer = new AudioClip(sound.getSource());
            mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
            mediaPlayer.play();
            Scenario battleScenario = new BattleScenario(jogadores, mediaPlayer);
            Spawner.startScenario(battleScenario, null);
            finish();
        } else {
            Scenario startScenario = new InicialScenario(jogadores, quant, audio);
            Spawner.startScenario(startScenario, null);
            finish();
        }
    }

    private void segundoPlano() {
        cbQtdPokemon.getItems().add(new Label("1"));
        cbQtdPokemon.getItems().add(new Label("2"));
        cbQtdPokemon.getItems().add(new Label("3"));
        cbQtdPokemon.getItems().add(new Label("4"));
        cbQtdPokemon.getItems().add(new Label("5"));
        cbQtdPokemon.getItems().add(new Label("6"));

        cbQtdAtaque.getItems().add(new Label("1"));
        cbQtdAtaque.getItems().add(new Label("2"));
        cbQtdAtaque.getItems().add(new Label("3"));
        cbQtdAtaque.getItems().add(new Label("4"));

        btAtaque.setDisable(true);
        btEspecie.setDisable(true);
        cbQtdAtaque.setDisable(true);
        cbQtdAtaque.setPromptText("Selecione Os Pokemons Primeiro!");
        btQtdAtaque.setDisable(true);
        tvAtaque.setDisable(true);
        tvEspecie.setDisable(true);
        tfLevel.setDisable(true);
        tfLevel.setPromptText("Escolha Os Pokemons E Os Ataques");
        btLevel.setDisable(true);
        btOK.setDisable(true);
        cbQtdPokemon.setDisable(true);
        cbQtdPokemon.setPromptText("Selecione Um Nome Para O Time");
        btQtdPokemon.setDisable(true);

        Base b = new Base();
        new Thread(
                () -> {
                    List<String[]> pks = b.getAllData(true);
                    List<Especie> especieList = new ArrayList<>();
                    for(int i = 1; i <= pks.size(); i++) {
                        especieList.add(new Especie(i));
                    }
                    especieObservableList = FXCollections.observableList(ObservableEspecie.observableEspeciesList(especieList));
                    tcEspecie.setCellValueFactory(cellData -> cellData.getValue().especieProperty());
                    tvEspecie.setItems(especieObservableList);
                }
        ).start();

        new Thread(
                () -> {
                    List<String[]> pks = b.getAllData(false);
                    List<Ataque> ataqueList = new ArrayList<>();
                    for(int i = 1; i <= pks.size(); i++) {
                        List<String> atq = b.getData(i, false);
                        String tipo = atq.get(6);
                        switch (tipo) {
                            case "charge":
                                ataqueList.add(new AtaqueCharge(i));
                                break;
                            case "comum":
                                ataqueList.add(new AtaqueComum(i));
                                break;
                            case "fixo":
                                ataqueList.add(new AtaqueFixo(i));
                                break;
                            case "hp":
                                ataqueList.add(new AtaqueHP(i));
                                break;
                            case "modifier":
                                ataqueList.add(new AtaqueModifier(i));
                                break;
                            case "multihit":
                                ataqueList.add(new AtaqueMultiHit(i));
                                break;
                            case "status":
                                ataqueList.add(new AtaqueStatus(i));
                                break;
                        }
                    }
                    ataquesObservableList = FXCollections.observableList(ObservableAtaque.observableAtaquesList(ataqueList));
                    tcAtaque.setCellValueFactory(cellData -> cellData.getValue().ataqueProperty());
                    tvAtaque.setItems(ataquesObservableList);
                }
        ).start();
    }

    private void handleNameAction(ActionEvent event) {
        nomearTime(event);
    }

    private void handleNameFieldAction(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            nomearTime(event);
        }
    }

    private void nomearTime(Event event) {
        try {
            name = " ";
            if(!tfName.getText().isEmpty()) {
                name = tfName.getText();
                cbQtdPokemon.setPromptText("Selecione a Quantidade de Pokemons Para o Time " + name);
                lbName.setText("Time " + name);
                tfName.setPromptText("Time " + name);
                tfName.setText(null);
                cbQtdPokemon.setDisable(false);
                btQtdPokemon.setDisable(false);
                tfName.setDisable(true);
                btName.setDisable(true);
                lbErrorName.setVisible(false);
            } else
                throw new RuntimeException();
        } catch (Exception e) {
            lbErrorName.setVisible(true);
        }
    }

    private void handleQtdPokemonAction(ActionEvent event) {
        setQtdEsp(event);
    }

    private void handleQtdPokemonCBAction(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            setQtdEsp(event);
        }
    }

    private void setQtdEsp(Event event) {
        try {
            String texto = cbQtdPokemon.getSelectionModel().getSelectedItem().getText();
            int total = Integer.parseInt(texto);
            tvEspecie.setDisable(false);
            tvEspecie.getSelectionModel().clearSelection();
            btEspecie.setDisable(false);
            lbErrorQtdPokemon.setVisible(false);
            btQtdPokemon.setDisable(true);
            cbQtdPokemon.setDisable(true);
            lbErrorQtdPokemon.setVisible(false);
            cbQtdPokemon.getSelectionModel().clearSelection();
            selectPokemons(0, total);
        } catch (Exception e) {
            lbErrorQtdPokemon.setVisible(true);
        }
    }

    private void selectPokemons(int pokeAtual, int totalPokemon) {
        if(pokeAtual + 1 > totalPokemon) {
            try {
                tvEspecie.setDisable(true);
                btEspecie.setDisable(true);
                cbQtdAtaque.setDisable(false);
                btQtdAtaque.setDisable(false);
                especies.add(tvEspecie.getSelectionModel().getSelectedItem().getEspecie());
                finalPhase(0, totalPokemon);
                tvEspecie.getSelectionModel().clearSelection();
                lbErrorEspecie.setVisible(false);
            } catch (Exception e) {
                lbErrorEspecie.setVisible(true);
                tvEspecie.setDisable(false);
                btEspecie.setDisable(false);
            }
        } else {
            lbEspecie.setText("ESPÉCIE POKEMON #" + (pokeAtual + 1));
            btEspecie.setOnAction(e -> {
                try {
                    int id = tvEspecie.getSelectionModel().getSelectedItem().getEspecie().getId();
                    showPreview(id, pokeAtual + 1);
                    especies.add(tvEspecie.getSelectionModel().getSelectedItem().getEspecie());
                    selectPokemons(pokeAtual + 1, totalPokemon);
                    tvEspecie.getSelectionModel().clearSelection();
                    lbErrorEspecie.setVisible(false);
                } catch (Exception ex) {
                    lbErrorEspecie.setVisible(true);
                }
            });
            tvEspecie.setOnKeyPressed(e -> {
                try {
                    int id = tvEspecie.getSelectionModel().getSelectedItem().getEspecie().getId();
                    showPreview(id, pokeAtual + 1);
                    if (e.getCode() == KeyCode.ENTER) {
                        try {
                            especies.add(tvEspecie.getSelectionModel().getSelectedItem().getEspecie());
                            selectPokemons(pokeAtual + 1, totalPokemon);
                            tvEspecie.getSelectionModel().clearSelection();
                            lbErrorEspecie.setVisible(false);
                        } catch (Exception ex) {
                            lbErrorEspecie.setVisible(true);
                        }
                    }
                } catch (Exception ex) {
                    lbErrorEspecie.setVisible(true);
                }
            });
            tvEspecie.setOnMousePressed(e -> {
                int id = tvEspecie.getSelectionModel().getSelectedItem().getEspecie().getId();
                showPreview(id, pokeAtual + 1);
            });
        }
    }

    private void finalPhase(int pokeAtual, int totalPoke) {
        hidePreview();
        cbQtdAtaque.setPromptText("Selecione A Quantidade De Ataque Para " + especies.get(pokeAtual).getNome());
        switch (pokeAtual + 1) {
            case 1:
                pokePreview1.setVisible(true);
                break;
            case 2:
                pokePreview2.setVisible(true);
                break;
            case 3:
                pokePreview3.setVisible(true);
                break;
            case 4:
                pokePreview4.setVisible(true);
                break;
            case 5:
                pokePreview5.setVisible(true);
                break;
            case 6:
                pokePreview6.setVisible(true);
                break;
        }
        ataques = new ArrayList<>();
        btQtdAtaque.setOnAction(e -> {
            cbQtdAtaque.setDisable(true);
            btQtdAtaque.setDisable(true);
            setQtdAtaque(pokeAtual, totalPoke);
        });
        cbQtdAtaque.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                cbQtdAtaque.setDisable(true);
                btQtdAtaque.setDisable(true);
                setQtdAtaque(pokeAtual, totalPoke);
            }
        });
    }

    private void setQtdAtaque(int pokeAtual, int totalPoke) {
        try {
            String texto = cbQtdAtaque.getSelectionModel().getSelectedItem().getText();
            int total = Integer.parseInt(texto);
            tvAtaque.getSelectionModel().clearSelection();
            tvAtaque.setDisable(false);
            btAtaque.setDisable(false);
            addAtaque(0, total, pokeAtual, totalPoke);
            lbErrorQtdAtaque.setVisible(false);
            cbQtdAtaque.getSelectionModel().clearSelection();
        } catch (Exception e) {
            lbErrorQtdAtaque.setVisible(true);
            cbQtdAtaque.setDisable(false);
            btQtdAtaque.setDisable(false);
        }
    }

    private void addAtaque(int ataqueAtual, int totalAtaque, int pokeAtual, int totalPokemon) {
        if(ataqueAtual < totalAtaque)
            lbAtaque.setText("ATAQUE #" + (ataqueAtual + 1) + " / " + especies.get(pokeAtual).getNome() + " #" + (pokeAtual + 1) );
        btAtaque.setOnAction(e -> executaAdd(ataqueAtual, totalAtaque, pokeAtual, totalPokemon));
        tvAtaque.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                executaAdd(ataqueAtual, totalAtaque, pokeAtual, totalPokemon);
            }
        });
    }

    private void executaAdd(int ataqueAtual, int totalAtaque, int pokeAtual, int totalPokemon) {
        btLevel.setOnAction(e -> {
            try {
                int lvl = Integer.parseInt(tfLevel.getText());
                if(lvl < 1 || lvl > 100)
                    throw new RuntimeException();
                addPokemon(pokeAtual, totalPokemon, lvl);
                lbErrorLevel.setVisible(false);
            } catch (Exception ex) {
                lbErrorLevel.setVisible(true);
                tfLevel.setDisable(false);
                btLevel.setDisable(false);
            }
        });
        tfLevel.setOnAction(e -> {
            try {
                int lvl = Integer.parseInt(tfLevel.getText());
                if(lvl < 1 || lvl > 100)
                    throw new RuntimeException();
                addPokemon(pokeAtual, totalPokemon, lvl);
                lbErrorLevel.setVisible(false);
            } catch (Exception ex) {
                lbErrorLevel.setVisible(true);
                tfLevel.setDisable(false);
                btLevel.setDisable(false);
            }
        });
        if(ataqueAtual + 1 >= totalAtaque) {
            try {
                ataques.add(tvAtaque.getSelectionModel().getSelectedItem().getAtaque());
                tvAtaque.setDisable(true);
                btAtaque.setDisable(true);
                tfLevel.setDisable(false);
                btLevel.setDisable(false);
                tfLevel.setPromptText("Level do " + especies.get(pokeAtual).getNome());
                tvAtaque.getSelectionModel().clearSelection();
                lbErrorAtaque.setVisible(false);
            } catch (Exception e) {
                lbErrorAtaque.setVisible(true);
            }
        } else {
            try {
                ataques.add(tvAtaque.getSelectionModel().getSelectedItem().getAtaque());
                addAtaque(ataqueAtual + 1, totalAtaque, pokeAtual, totalPokemon);
                tvAtaque.getSelectionModel().clearSelection();
                lbErrorAtaque.setVisible(false);
            } catch (Exception e) {
                lbErrorAtaque.setVisible(true);
            }
        }
    }

    private void addPokemon(int pokeAtual, int totalPokemon, int lvl) {
        if(pokeAtual + 1 >= totalPokemon) {
            tfLevel.setDisable(true);
            btLevel.setDisable(true);
            btOK.setDisable(false);
            pokemons.add(new Pokemon(lvl, ataques, especies.get(pokeAtual)));
        } else {
            pokemons.add(new Pokemon(lvl, ataques, especies.get(pokeAtual)));
            cbQtdAtaque.setDisable(false);
            btQtdAtaque.setDisable(false);
            tfLevel.setDisable(true);
            tfLevel.setText(null);
            btLevel.setDisable(true);
            finalPhase(pokeAtual + 1, totalPokemon);
        }
    }

    private void hidePreview() {
        pokePreview1.setVisible(false);
        pokePreview2.setVisible(false);
        pokePreview3.setVisible(false);
        pokePreview4.setVisible(false);
        pokePreview5.setVisible(false);
        pokePreview6.setVisible(false);
    }

    private void showPreview(int id, int pokemon) {
        String path;
        if(id < 10)
            path = "img/pokemons/00" + id + ".png";
        else if(id < 100)
            path = "img/pokemons/0" + id + ".png";
        else
            path = "img/pokemons/" + id + ".png";
        Image image = new Image(path);
        switch (pokemon) {
            case 1:
                pokePreview1.setImage(image);
                break;
            case 2:
                pokePreview2.setImage(image);
                break;
            case 3:
                pokePreview3.setImage(image);
                break;
            case 4:
                pokePreview4.setImage(image);
                break;
            case 5:
                pokePreview5.setImage(image);
                break;
            case 6:
                pokePreview6.setImage(image);
                break;
        }
    }
}