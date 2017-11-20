package br.com.pokemon.gui.scenario;

import br.com.pokemon.db.Base;
import br.com.pokemon.gui.FXScenario.FeedbackScenario;
import br.com.pokemon.gui.FXScenario.NodeCustomizer;
import br.com.pokemon.gui.FXScenario.Spawner;
import br.com.pokemon.gui.visible.ObservableEspecie;
import br.com.pokemon.player.Jogador;
import br.com.pokemon.poke.Especie;
import br.com.pokemon.poke.Pokemon;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class EspecieScenario extends FeedbackScenario {

    @FXML private HBox menuBar;
    @FXML private TableView<ObservableEspecie> tvAll;
    @FXML private TableColumn<ObservableEspecie, String> tcOptions;
    @FXML private Button addOption;
    @FXML private Label errorLabel;
    @FXML private ObservableList<ObservableEspecie> especieObservableList;
    @FXML private Label texto;
    private List<Jogador> jogadores;
    private List<Especie> especies;
    private List<Pokemon> pks = new ArrayList<>();
    private int quant;
    String name;
    private int min;
    private int max;
    private String jogador;

    public EspecieScenario(List<Jogador> jogadores, int quant, String jogador, String name, int min, int max, List<Especie> especies) {
        super("fxml/pagination.fxml");
        this.jogadores = jogadores;
        this.quant = quant;
        this.name = name;
        this.min = min;
        this.max = max;
        this.especies = especies;
        this.jogador = jogador;
    }

    @Override
    protected void onConfigScene(Scene scene) {
        scene.getStylesheets().add("css/Style.css");
    }

    @Override
    protected void onConfigStage(Stage stage) {
        NodeCustomizer.setUpMenuBar(this, menuBar, null, null, null);
        stage.setTitle("Especie Pokemon #" + min);
        texto.setText("Especie Pokemon #" + min);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        Base b = new Base();
        List<String[]> pks = b.getAllData(true);
        List<Especie> especieList = new ArrayList<>();
        for(int i = 1; i <= pks.size(); i++) {
            especieList.add(new Especie(i));
        }

        especieObservableList = FXCollections.observableList(ObservableEspecie.observableEspeciesList(especieList));
        tcOptions.setCellValueFactory(cellData -> cellData.getValue().especieProperty());
        tvAll.setItems(especieObservableList);

        errorLabel.setText("Selecione Um Pokemon");

        addOption.setOnAction(this::handleButtonAction);
    }

    private void handleButtonAction(ActionEvent event) {
        try {
            int especie_id = tvAll.getSelectionModel().getSelectedItem().getEspecie().getId();
            if(tvAll.getSelectionModel().getSelectedItem() == null)
                throw new RuntimeException();
            if(min >= max) {
                especies.add(new Especie(especie_id));
                FeedbackScenario qtdAtaqueScenario = new QtdAtaqueScenario(jogadores, quant, jogador, name, 0, max, especies, pks);
                Spawner.startFeedbackScenario(qtdAtaqueScenario, 0, this, (requestCode, resultCode, data) -> {});
                finish();
            } else {
                especies.add(new Especie(especie_id));
                FeedbackScenario especieScenario = new EspecieScenario(jogadores, quant, jogador, name, min + 1, max, especies);
                Spawner.startFeedbackScenario(especieScenario, 0, this, (requestCode, resultCode, data) -> {});
                finish();
            }
        } catch (Exception e) {
            errorLabel.setVisible(true);
        }
    }
}
