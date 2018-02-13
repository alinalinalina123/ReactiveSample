package podolyanchik.alina.reactivesample.detail

import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject
import podolyanchik.alina.reactivesample.database.DbManager
import podolyanchik.alina.reactivesample.model.Note


class DetailViewModel(databaseManager: DbManager): DetailViewModelType, DetailViewModelInput, DetailViewModelOutput {

    override val input: DetailViewModelInput = this
    override val output: DetailViewModelOutput = this

    //output

    override val noteAdded: Observable<Note>
    override val noteModified: Observable<Note>
    override val noteRecivied: Observable<Note>

    //input
    private val addButtonPressed = PublishSubject.create<Unit>()
    override fun addButtonPressed() = addButtonPressed.onNext(Unit)

    private val modifyButtonPressed = PublishSubject.create<Int>()
    override fun modifyButtonPressed(id: Int) = modifyButtonPressed.onNext(id)

    private val titleChanged = PublishSubject.create<String>()
    override fun titleChanged(title: String) = titleChanged.onNext(title)

    private val recieveNote = PublishSubject.create<Note>()
    override fun recieveNote(note: Note) = recieveNote.onNext(note)


    private val descriptionChanged = PublishSubject.create<String>()
    override fun descriptionChanged(description: String) = descriptionChanged.onNext(description)

    init {

        noteRecivied = recieveNote.map{ it }

        noteAdded = Observables.combineLatest(titleChanged,descriptionChanged, addButtonPressed).map { (title, description,_) ->
                if(title != "" && description != "") {
                    val note = Note(1, title, description)
                    databaseManager.insert(note)
                   note
                } else Note()
        }

        noteModified = Observables.combineLatest(titleChanged.startWith(""),descriptionChanged.startWith(""), modifyButtonPressed).map { (title, description,id) ->
            if(title != "" && description != "") {
                val note = Note(id, title, description)
                databaseManager.updateTask(note)
                note
            } else Note()
        }
    }
}
