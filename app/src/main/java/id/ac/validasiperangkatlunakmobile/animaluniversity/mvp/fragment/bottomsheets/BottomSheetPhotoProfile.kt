package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.fragment.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet_photo_profile.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class BottomSheetPhotoProfile : BottomSheetDialogFragment() {

    var listener : BottomSheetPhotoProfile.BottomSheetPhotoProfileListener? = null
    lateinit var photo: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_bottom_sheet_photo_profile, container,
                false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        constraint_remove_pic.onClick {
            photo?.let { it1 -> listener?.onRemoveListener(it1) }
        }

        constraint_select_pic.onClick {
            listener?.onSelectListener()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        photo = this.arguments!!.getString("foto")
    }

    companion object {

        fun newInstance(photo: String): BottomSheetPhotoProfile {

            return BottomSheetPhotoProfile().apply {
                arguments = Bundle().apply{
                    putString("foto", photo)
                }
            }
        }
    }

    interface BottomSheetPhotoProfileListener {
        fun onRemoveListener(photo: String)
        fun onSelectListener()
    }
}