package podolyanchik.alina.reactivesample.detail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import podolyanchik.alina.reactivesample.R
import podolyanchik.alina.reactivesample.database.DbManager
import podolyanchik.alina.reactivesample.libs.afterTextChanged
import podolyanchik.alina.reactivesample.model.Note
import podolyanchik.alina.reactivesample.notes.MainActivity

class DetailActivity : AppCompatActivity() {

    private var mSQLite: DbManager? = null
    lateinit var viewModel: DetailViewModel
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mSQLite = DbManager(this)
        viewModel = DetailViewModel(mSQLite!!)
        note = this.intent.getSerializableExtra("note_update") as? Note


    }

    override fun onResume() {
        super.onResume()

        viewModel.output.noteRecivied
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    editTitle.setText(it.title)
                    editDescription.setText(it.description)
                    addNoteButton.apply {
                        text = "Modify note"
                    }
                }

        viewModel.output.noteAdded
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    if(it.title != null) {
                        startActivity(Intent(this, MainActivity::class.java))
                        this.finish()
                    } else {
                        this.toast("Check all fields!")
                    }
                }

        viewModel.output.noteModified
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    if(it.title != null) {
                        startActivity(Intent(this, MainActivity::class.java))
                        this.finish()
                    } else {
                        this.toast("Check all fields!")
                    }
                }

        if(note != null) {
            viewModel.input.recieveNote(note!!)
        }

        editTitle.afterTextChanged {
            viewModel.titleChanged(it)
        }

        editDescription.afterTextChanged {
            viewModel.descriptionChanged(it)
        }

        addNoteButton.setOnClickListener{
            if(note == null) viewModel.addButtonPressed()
            else viewModel.modifyButtonPressed(note?.id ?: 0)
        }


    }
}
