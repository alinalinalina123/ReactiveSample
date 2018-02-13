package podolyanchik.alina.reactivesample.notes

import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject
import podolyanchik.alina.reactivesample.database.DbManager
import podolyanchik.alina.reactivesample.model.Note


class NotesViewModel( databaseManager: DbManager) : NotesViewModelType, NotesViewModelOutput, NotesViewModelInput {

    override val input: NotesViewModelInput = this
    override val output: NotesViewModelOutput = this

    //outputs

    override val notes: Observable<List<Note>>
    override val deleteNote: Observable<Note>
    override val editNote: Observable<Note>

    //inputs

    private val deleteButtonPressed = PublishSubject.create<Note>()
    override fun deleteButtonPressed(note: Note) = deleteButtonPressed.onNext(note)

    private val editButtonPressed = PublishSubject.create<Note>()
    override fun editButtonPressed(note: Note) = editButtonPressed.onNext(note)

    private val viewLoaded = PublishSubject.create<Unit>()
    override fun viewLoaded() = viewLoaded.onNext(Unit)

    init {

        notes = viewLoaded.map {
            databaseManager.selectAll()
        }

        deleteNote = deleteButtonPressed.map { it }

        editNote = editButtonPressed.map { it }
    }
}