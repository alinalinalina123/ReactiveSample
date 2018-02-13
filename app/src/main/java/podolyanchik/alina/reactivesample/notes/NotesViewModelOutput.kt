package podolyanchik.alina.reactivesample.notes

import io.reactivex.Observable
import podolyanchik.alina.reactivesample.model.Note


interface NotesViewModelOutput {
    val notes: Observable<List<Note>>
    val deleteNote: Observable<Note>
    val editNote: Observable<Note>
}