package cs408.incubator

import android.app.PendingIntent.getActivity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.ShadowDrawableWrapper
import android.support.design.widget.Snackbar
import android.support.v4.util.Pair
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.woxthebox.draglistview.DragListView
import kotlinx.android.synthetic.main.activity_main_ideas.*
import android.widget.Toast
import com.woxthebox.draglistview.DragItem
import com.woxthebox.draglistview.DragItemAdapter


class MainIdeasActivity : AppCompatActivity() {
    val REQ_CODE = 1
    private lateinit var mDragList: DragListView
    private lateinit var ideaArray: ArrayList<Pair<Long,String>>

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ideas)
        setSupportActionBar(toolbar)
        val actionbarEvent: ActionBar? = supportActionBar
        actionbarEvent.apply {
            this!!.setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        mDragList = findViewById<DragListView>(R.id.ideaList)

        mDragList.setDragListListener(object : DragListView.DragListListener {
            override fun onItemDragging(itemPosition: Int, x: Float, y: Float) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemDragStarted(position: Int) {
                Toast.makeText(applicationContext, "Start - position: $position", Toast.LENGTH_SHORT).show()
            }

            override fun onItemDragEnded(fromPosition: Int, toPosition: Int) {
                if (fromPosition != toPosition) {
                    Toast.makeText(applicationContext, "End - position: $toPosition", Toast.LENGTH_SHORT).show()
                }
            }
        })

        ideaArray = ArrayList()
        mDragList.setLayoutManager(LinearLayoutManager(applicationContext))
        val listAdapter = IdeaItemAdapter(ideaArray,R.layout.idea_item,true)
        mDragList.setAdapter(listAdapter,true)
        mDragList.setCanDragHorizontally(false)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this,AddIdeaActivity::class.java)
            startActivityForResult(intent,REQ_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQ_CODE){
            if(resultCode == 1){
                val layout = findViewById<LinearLayout>(R.id.linList)
                layout.removeAllViews()
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)
                layout.addView(mDragList,params)
                val newArray = ArrayList<Pair<Long,String>>()
                newArray.add(Pair(111,"New Idea-sbfabif"))
                newArray.addAll(ideaArray)


                mDragList.setLayoutManager(LinearLayoutManager(applicationContext))
                val listAdapter = IdeaItemAdapter(newArray,R.layout.idea_item,true)
                mDragList.setAdapter(listAdapter,true)
                mDragList.setCanDragHorizontally(false)
                ideaArray.add(Pair(111,"New Ideda-sbfabif"))
            }
            else if(resultCode == -1){
                Toast.makeText(applicationContext,"No idea added",Toast.LENGTH_SHORT).show()
            }
        }

    }

}
