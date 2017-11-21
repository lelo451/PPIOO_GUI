package br.com.pokemon.gui.scenario;

import br.com.pokemon.db.Base;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.visible.ObservableAtaque;
import br.com.pokemon.gui.visible.ObservableEspecie;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    @FXML private ImageView pokePreview;

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
        segundoPlano();
        btQtdPokemon.setOnAction(this::handleQtdPokemonAction);
        tfName.setOnKeyPressed(this::handleNameFieldAction);
        btName.setOnAction(this::handleNameAction);
        cbQtdPokemon.setOnKeyPressed(this::handleQtdPokemonCBAction);
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
            btEspecie.setDisable(false);
            selectPokemons(0, total);
            lbErrorQtdPokemon.setVisible(false);
            btQtdPokemon.setDisable(true);
            cbQtdPokemon.setDisable(true);
        } catch (Exception e) {
            lbErrorQtdPokemon.setVisible(true);
        }
    }

    private void selectPokemons(int pokeAtual, int totalPokemon) {
        tvEspecie.getSelectionModel().clearSelection();
        if(pokeAtual + 1 > totalPokemon) {
            cbQtdAtaque.setDisable(false);
            btQtdAtaque.setDisable(false);
            tvEspecie.setDisable(true);
            btEspecie.setDisable(true);
            //finalPhase(0, 4, 0, totalPokemon);
        } else {
            lbEspecie.setText("ESPÉCIE POKEMON #" + (pokeAtual + 1));
            btEspecie.setOnAction(e -> {
                try {
                    int id = tvEspecie.getSelectionModel().getSelectedItem().getEspecie().getId();
                    showPreview(id);
                    especies.add(tvEspecie.getSelectionModel().getSelectedItem().getEspecie());
                    selectPokemons(pokeAtual + 1, totalPokemon);
                } catch (Exception ex) {
                    lbErrorEspecie.setVisible(true);
                }
            });
            tvEspecie.setOnKeyPressed(e -> {
                int id = tvEspecie.getSelectionModel().getSelectedItem().getEspecie().getId();
                showPreview(id);
                if(e.getCode() == KeyCode.ENTER) {
                    try {
                        especies.add(tvEspecie.getSelectionModel().getSelectedItem().getEspecie());
                        selectPokemons(pokeAtual + 1, totalPokemon);
                    } catch (Exception ex) {
                        lbErrorEspecie.setVisible(true);
                    }
                }
            });
            tvEspecie.setOnMousePressed(e -> {
                int id = tvEspecie.getSelectionModel().getSelectedItem().getEspecie().getId();
                showPreview(id);
            });
        }
    }

    /*
    TODO: fazer essa função de finalizar de adicionar o pokemon e mostrar a imagem do pokemon que está sendo feito a adição de ataques
    private void finalPhase(int ataqueAtual, int maxAtaque, int pokeAtual, int totalPoke) {
        if(ataqueAtual + 1 > maxAtaque) {
            if(pokeAtual + 1 > totalPoke) {

            }
        }
        cbQtdAtaque.setPromptText("Selecione A Quantidade De Ataque Para " + especies.get(pokeAtual).getNome());
        showPreview(especies.get(pokeAtual).getId());

    }
     */

    private void showPreview(int id) {
        String path;
        if(id < 10)
            path = "img/pokemons/00" + id + ".png";
        else if(id < 100)
            path = "img/pokemons/0" + id + ".png";
        else
            path = "img/pokemons/" + id + ".png";
        Image image = new Image(path);
        pokePreview.setImage(image);
    }
}