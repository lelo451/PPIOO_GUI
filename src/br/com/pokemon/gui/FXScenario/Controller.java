package br.com.pokemon.gui.FXScenario;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller {

    String fxmlPath;

    Controller parentController;

    Map<String, Object> extraInformation = new HashMap<>();

    public Controller(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    abstract void create();
    abstract void destroy();

    public Controller getParentController() {
        return parentController;
    }

    public void putExtra(String id, Object object) {
        extraInformation.put(id, object);
    }

    public Object getExtra(String id) {
        return extraInformation.get(id);
    }
}
