package podolyanchik.alina.reactivesample.detail

import podolyanchik.alina.reactivesample.model.Note


interface DetailViewModelInput {
    fun addButtonPressed()
    fun modifyButtonPressed(id: Int)
    fun titleChanged(title: String)
    fun descriptionChanged(description: String)
    fun recieveNote(note: Note)
}