package com.example.imagecard.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.imagecard.R

class ChooseImageDialogFragment : DialogFragment() {
    private lateinit var btnChooseFromGallery: Button
    private lateinit var etTypeURL: EditText
    private lateinit var btnSave: Button

    private var chooseFromGalleryListener: (() -> Unit)? = null
    private var downloadFromUrlListener: ((String) -> Unit)? = null


    fun setChooseFromGalleryListener(chooseFromGalleryListener: (() -> Unit)) {
        this.chooseFromGalleryListener = chooseFromGalleryListener
    }

    fun setDownloadFromUrlListener(downloadFromUrlListener: ((String) -> Unit)) {
        this.downloadFromUrlListener = downloadFromUrlListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = object : AlertDialog.Builder(context, theme) {}
        return builder.create()
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        dialog?.setContentView(R.layout.fragment_chose_image_dialog)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        findViews()
        setListeners()
    }

    private fun findViews() {
        btnChooseFromGallery = dialog?.findViewById(R.id.btnChooseFromGallery)!!
        etTypeURL = dialog?.findViewById(R.id.etUrl)!!
        btnSave = dialog?.findViewById(R.id.btnSaveImage)!!
    }

    private fun setListeners() {
        btnChooseFromGallery.setOnClickListener {
            chooseFromGalleryListener?.invoke()
            dismissDialog()

        }

        btnSave.setOnClickListener {
            downloadFromUrlListener?.invoke(etTypeURL.text.toString())
            dismissDialog()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chose_image_dialog, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ChooseImageDialogFragment().apply {

            }
    }
}