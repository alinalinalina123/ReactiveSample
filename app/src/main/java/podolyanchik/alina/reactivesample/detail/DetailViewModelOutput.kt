package podolyanchik.alina.reactivesample.detail

import io.reactivex.Observable
import podolyanchik.alina.reactivesample.model.Note


interface DetailViewModelOutput {
    val noteAdded: Observable<Note>
    val noteModified: Observable<Note>
    val noteRecivied: Observable<Note>
}