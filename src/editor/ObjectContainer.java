package editor;

import java.util.ArrayList;

public class ObjectContainer {
    private ArrayList<Object> objects;
    public ObjectContainer(ArrayList<Object> objects) {
        this.objects = objects;
    }
    private void renderObjects(){
        for(Object o : objects){
            System.out.println(o);
        }
    }

    private void draw(){

    }
    private void update(){

    }
}
