package br.com.pokemon.gui.FXScenario;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Fragment extends Controller {

    Pane rootPane;

    public Fragment(String fxmlPath) {
        super(fxmlPath);
    }

    // ============ LIFE CYCLE METHODS ================= //

    void create() {

        onCreate();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        fxmlLoader.setController(this);

        try {

            Pane newLoadedPane = fxmlLoader.load();

            onCreateView();

            rootPane.getChildren().add(newLoadedPane);

            ready();

        } catch (IOException e) {
            System.err.println("Failed to create " + getClass().getSimpleName() + " fragment.");
            e.printStackTrace();
        }

    }

    final void ready() {
        onReady();
    }

    final void destroy() {
        onDestroy();
    }

    // ============= CUSTOMIZATION CYCLE METHODS ============= //

    protected void onCreate() {
    }

    protected void onCreateView() {
    }

    protected void onReady() {
    }

    protected void onDestroy() {
    }

}
