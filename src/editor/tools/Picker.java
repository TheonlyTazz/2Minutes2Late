package editor.tools;

import editor.Tool;

public class Picker extends Tool {

    private Boolean isHolding = false;


    public Picker(String name) {
        super(name);
    }

    public Boolean getHolding() {
        return isHolding;
    }

    public void setHolding(Boolean holding) {
        isHolding = holding;
    }
}
