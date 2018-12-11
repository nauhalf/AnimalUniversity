package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gpa.*

class GpaAdapter(private val gpa: List<String>,
                 private val listener: (String) -> Unit) : RecyclerView.Adapter<GpaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gpa, parent, false))
    }

    override fun getItemCount(): Int = gpa.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItems(gpa[p1], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        val textSemester = txt_semester
        fun bindItems(s : String, listener: (String) -> Unit){
            textSemester.text = "Semester $s"
            itemView.setOnClickListener {
                listener(s)
            }
        }
    }
}