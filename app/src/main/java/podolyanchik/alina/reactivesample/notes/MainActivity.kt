package podolyanchik.alina.reactivesample.notes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_bar.*
import org.jetbrains.anko.toast
import podolyanchik.alina.reactivesample.R
import podolyanchik.alina.reactivesample.database.DbManager
import podolyanchik.alina.reactivesample.detail.DetailActivity

class MainActivity : AppCompatActivity() {
    lateinit var mRecyclerView: RecyclerView
    private var mAdapter: RecyclerViewAdapter? = null
    private var mSQLite: DbManager? = null
    lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSQLite = DbManager(this)

        mRecyclerView = recyclerView
        val layoutManager = LinearLayoutManager(this@MainActivity)
        mRecyclerView.layoutManager = layoutManager
        mAdapter = RecyclerViewAdapter(ArrayList(0), this)
        mRecyclerView.adapter = this.mAdapter
        viewModel = NotesViewModel(mSQLite!!)

    }

    override fun onResume() {
        super.onResume()

        viewModel.output.notes
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                 mAdapter?.updateAllList(it)
                 mRecyclerView.adapter = this.mAdapter
                }

        viewModel.output.editNote
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {

                    viewModel.input.viewLoaded()
                }

        viewModel.input.viewLoaded()

        viewModel.output.deleteNote
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    mSQLite?.delete(it)
                    viewModel.input.viewLoaded()
                }

        delete.setOnClickListener {
           val note =  mAdapter?.getSelected()
            if(note != null) {
                viewModel.input.deleteButtonPressed(note)
                mAdapter?.setSelectedNull()
            } else { this.toast("You should select note")}
        }

        edit.setOnClickListener {
            val note =  mAdapter?.getSelected()
            if(note != null) {
                mAdapter?.setSelectedNull()
                startActivity(Intent(this,DetailActivity::class.java).apply {
                    this.putExtra("note_update",note)
                })
            } else { this.toast("You should select note")}
        }
        add.setOnClickListener {
            startActivity(Intent(this,DetailActivity::class.java))
        }

    }
}
