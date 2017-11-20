package br.com.pokemon.gui.FXScenario;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Scenario extends Controller {

    boolean hasUndecoratedStyle = false;

    Stage stage = null;

    public Scenario(String fxmlPath) {
        super(fxmlPath);
    }



    // ============ LIFE CYCLE METHODS ================= //

    void create() {

        onCreate();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        fxmlLoader.setController(this);

        Scene scene;

        try {

            // Config Scene
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            configScene(scene);

            // Config Stage
            if (stage == null) stage = new Stage();
            stage.setScene(scene);
            configStage(stage);

            ready();

        } catch (IOException e) {
            System.err.println("Failed to create " + getClass().getSimpleName() + " scenario.");
            e.printStackTrace();
        }

    }

    final void configScene(Scene scene) {
        onConfigScene(scene);
    }

    final void configStage(Stage stage) {
        onConfigStage(stage);
    }

    final void ready() {
        onReady();
    }

    final void destroy() {
        onDestroy();
        stage.close();
    }

    // ============= CUSTOMIZATION CYCLE METHODS ============= //

    protected void onCreate() {}

    protected void onConfigScene(Scene scene) {}

    protected void onConfigStage(Stage stage) {}

    protected void onReady() {}

    protected void onDestroy() {}

    // ================ OTHER METHODS ======================//

    public void finish() {
        destroy();
    }

    // ============ SETTERS AND GETTERS ================= //

    public Stage getStage() {
        return stage;
    }

    public void setUpScenarioStyle(ScenarioStyle scenarioStyle) {

        switch (scenarioStyle) {
            case BETTER_UNDECORATED:
                hasUndecoratedStyle = true;
                NodeCustomizer.setUpUndecoratedScenario(this);
                break;
        }
    }

    public enum ScenarioStyle {
        BETTER_UNDECORATED
    }

}