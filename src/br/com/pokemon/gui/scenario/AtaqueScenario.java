package br.com.pokemon.gui.scenario;

import br.com.pokemon.db.Base;
import br.com.pokemon.gui.FXScenario.FeedbackScenario;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Scenario;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.gui.visible.ObservableAtaque;
import br.com.pokemon.gui.visible.ObservableEspecie;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import br.com.pokemon.poke.atack.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class AtaqueScenario extends FeedbackScenario {

    @FXML private HBox menuBar;
    @FXML private TableView<ObservableAtaque> tvAll;
    @FXML private TableColumn<ObservableAtaque, String> tcOptions;
    @FXML private Button addOption;
    @FXML private Label errorLabel;
    @FXML private ObservableList<ObservableAtaque> ataquesObservableList;
    @FXML private Label texto;
    private List<Jogador> jogadores;
    private List<Especie> especies;
    private List<Pokemon> pks;
    private List<Ataque> atks;
    private int quant;
    String name;
    private int min; //ataque atual a ser adicionado
    private int max; //numero maximo de ataques escolhidos pelo usuario para ser adicionado ao pokemon
    private int pokeAtual;
    private String jogador;

    public AtaqueScenario(List<Jogador> jogadores, int quant, String jogador, String name, int min, int max, List<Especie> especies, List<Pokemon> pks, int pokeAtual, List<Ataque> atks) {
        super("fxml/pagination.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.name = name;
        this.min = min;
        this.max = max;
        this.especies = especies;
        this.pokeAtual = pokeAtual;
        this.pks = pks;
        this.jogador = jogador;
        this.atks = atks;
    }

    @Override
    protected void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
    }

    @Override
    protected void onConfigStage(Stage stage) {
        NodeCustomizer.setUpMenuBar(this, menuBar, null, null, null);
        stage.setTitle("Ataque #" + (min + 1) + "/ " + especies.get(pokeAtual).getNome() + " #" + (pokeAtual + 1));
        texto.setText("Ataque #" + (min + 1) + "/ " + especies.get(pokeAtual).getNome() + " #" + (pokeAtual + 1));
        tcOptions.setText("Ataques");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        Base b = new Base();
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
        tcOptions.setCellValueFactory(cellData -> cellData.getValue().ataqueProperty());
        tvAll.setItems(ataquesObservableList);

        errorLabel.setText("Selecione Um Ataque");

        tvAll.setOnKeyPressed(this::handleKeyAction);
        addOption.setOnAction(this::handleButtonAction);
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
            Ataque a = tvAll.getSelectionModel().getSelectedItem().getAtaque();
            if(tvAll.getSelectionModel().getSelectedItem() == null)
                throw new RuntimeException();
            if(pokeAtual < especies.size()) {
                if(min + 1 >= max) {
                    atks.add(a);
                    FeedbackScenario lvlScenario = new LvlScenario(jogadores, quant, jogador, name, especies, pks, pokeAtual, atks);
                    Spawner.startFeedbackScenario(lvlScenario, 0, this, (requestCode, resultCode, data) -> {});
                    finish();
                } else {
                    atks.add(a);
                    FeedbackScenario atkScenario = new AtaqueScenario(jogadores, quant, jogador, name, min + 1, max, especies, pks, pokeAtual, atks);
                    Spawner.startFeedbackScenario(atkScenario, 0, this, (requestCode, resultCode, data) -> {});
                    finish();
                }
            }
        } catch (Exception e) {
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }

}
