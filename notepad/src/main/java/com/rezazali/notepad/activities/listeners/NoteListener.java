package com.rezazali.notepad.activities.listeners;


import com.rezazali.notepad.activities.entities.NoteEntity;

// zewnetrzny listener do elementow recyclerview (konkretnych notatek)
public interface NoteListener {
    void noteClicked(NoteEntity noteEntity, int position);

    void noteLongClicked(NoteEntity noteEntity, int position);
}
