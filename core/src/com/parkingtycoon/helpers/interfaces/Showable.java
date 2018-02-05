package com.parkingtycoon.helpers.interfaces;

import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * This Class is responsible for providing a basic interface for items who would like to be showable in the UI.
 *
 * A scenario where you would use this is a button that opens a window.
 * The window implements Showable and by doing this it also makes the decision if it allows multiple window
 * open at the same time.
 *
 * @author Timo Strating
 */
public interface Showable {

    /**
     * Show this Item
     *
     * @param stage the stage where the showed item should be added to
     */
    void show(Stage stage);

    /**
     * Hide this item. You can only hide things if you can also show them. There is no hide without a show.
     */
    default void hide() { }  // This method is not directly required.

}
