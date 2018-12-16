package id.ac.validasiperangkatlunakmobile.animaluniversity.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AlertDialog
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern


fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

fun decodeSampledBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, this)

        // Calculate inSampleSize
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false

        BitmapFactory.decodeResource(res, resId, this)
    }
}

fun Context.alertLoading(): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setCancelable(false)
    dialogBuilder.setView(R.layout.progress_bar_loading)
    return dialogBuilder.create()
}

fun String.isValidEmail() : Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword() : Boolean {
    return Pattern.compile("^.{6,}$").matcher(this).matches()
}

fun String.getUriFromPath(context: Context): Uri {
    val photoId: Long
    val photoUri = MediaStore.Images.Media.getContentUri("external")
    val projection = arrayOf(MediaStore.Images.ImageColumns._ID)
    val cursor = context.contentResolver.query(photoUri, projection, MediaStore.Images.ImageColumns.DATA + " LIKE ?", arrayOf(this), null)
    cursor!!.moveToFirst()

    val columnIndex = cursor.getColumnIndex(projection[0])
    photoId = cursor.getLong(columnIndex)

    cursor.close()
    return Uri.parse(photoUri.toString() + "/" + photoId)
}

fun Uri.getFileName(context: Context): String {
    var result: String? = null
    if (this.scheme == "content") {
        val cursor = context.contentResolver.query(this, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor!!.close()
        }
    }
    if (result == null) {
        result = this.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}