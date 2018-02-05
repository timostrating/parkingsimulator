package com.parkingtycoon.helpers.interfaces;

import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 *
 */
public interface Showable {

    void show(Stage stage);
    default void hide() { }  // This method is not directly required.

}
