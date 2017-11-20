package br.com.pokemon.gui.scenario;

import br.com.pokemon.gui.FXScenario.Fragment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AcaoScenario extends Fragment {

    @FXML private Pane rootPane;
    @FXML private HBox menu;
    @FXML private Label lb_jogador_vez;
    @FXML private Button change_pokemon;
    @FXML private Button use_ataque;

    public AcaoScenario() {
        super("fxml/acao.fxml");
    }

    @Override
    protected void onCreateView() {
        rootPane.setVisible(true);
    }
}
