package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gpa.*
import java.text.DecimalFormat

class GpaAdapter(private val gpa: List<Gpa>,
                 private val listener: (Gpa) -> Unit) : RecyclerView.Adapter<GpaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gpa, parent, false))
    }

    override fun getItemCount(): Int = gpa.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItems(gpa[p1], listener)
    }

    fun getCurrentGpa() : Double? {
        return gpa.filter { filter -> filter.value != null }.map { it -> it.value!! }.average()
    }

    fun getLatestSemester() : Int? {
        val lastSemester = gpa.maxBy { it -> it.semester!! }
        return lastSemester?.semester
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        private val textSemester = txt_semester
        private val textGpaValue = txt_gpa_value
        private val format = DecimalFormat("#.##")
        fun bindItems(gpa : Gpa, listener: (Gpa) -> Unit){
            val semester = "Semester ${gpa.semester}"
            textSemester.text = semester
            textGpaValue.text = gpa.value?.let { format.format(it) } ?: run { "N/A"}
            itemView.setOnClickListener {
                listener(gpa)
            }
        }
    }
}