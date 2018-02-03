package com.parkingtycoon.helpers.interfaces;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * This is a special Interface that allows us to write clickListeners as one liners using lambda's
 */
public interface ClickListener extends EventListener {
    default public boolean handle (Event event) {  // We use default to get functional code into a interface
        if (!(event instanceof ChangeListener.ChangeEvent)) return false;
        changed((ChangeListener.ChangeEvent)event, event.getTarget());
        return false;
    }

    /**
     * @param actor The event target, which is the actor that emitted the change event.
     */
    public void changed(ChangeListener.ChangeEvent event, Actor actor);
}
