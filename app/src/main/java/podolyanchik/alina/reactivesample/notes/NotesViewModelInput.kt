package podolyanchik.alina.reactivesample.notes

import podolyanchik.alina.reactivesample.model.Note


interface NotesViewModelInput {

    fun viewLoaded()
    fun deleteButtonPressed(note: Note)
    fun editButtonPressed(note: Note)

}