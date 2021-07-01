package com.example.imagecard.ui.feature

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.imagecard.R
import com.example.imagecard.constants.MIME_TYPE_FILTER
import com.example.imagecard.data.entity.CardEntity
import com.example.imagecard.dialog.ChooseImageDialogFragment
import com.example.imagecard.extention.hideKeyboardFragment
import com.example.imagecard.ui.adapter.CardAdapter
import com.example.imagecard.ui.viewmodel.CardViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class CardFragment : Fragment() {
    private lateinit var cardRecyclerView: RecyclerView
    private val cardAdapter = CardAdapter()
    private val vModel: CardViewModel by viewModel()
    private var cardCount = 0
    private var cardEntity: CardEntity? = null

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            if (permission) {
                getContent.launch(MIME_TYPE_FILTER)
            } else {
            }
        }

    private val getContent: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->

            cardEntity?.let { cardEntity ->
                cardEntity.imagePath = imageUri?.let {
                    it.toString()
                }?:""

                vModel.updateCards(cardEntity)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews(view)
        setListeners()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        observeData()

    }

    private fun initViews(view: View) {
        cardRecyclerView = view.findViewById(R.id.card_recycler_view)
    }

    private fun updateRecyclerView(list: MutableList<CardEntity>) {
        cardAdapter.items = list
        cardAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        cardRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        cardRecyclerView.adapter = cardAdapter
    }

    private fun setListeners() {
        cardAdapter.setTitleListener {
            it?.let {
                vModel.updateCards(it)
                hideKeyboardFragment()
            }
        }

        cardAdapter.setImageListener {
            cardEntity = it
            it?.let {
                val chooseImageDialogFragment = ChooseImageDialogFragment.newInstance()

                chooseImageDialogFragment.setChooseFromGalleryListener {
                    if (!checkStoragePermission()) {
                        requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    } else {
                        getContent.launch(MIME_TYPE_FILTER)
                    }
                }

                chooseImageDialogFragment.setDownloadFromUrlListener { path ->
                    cardEntity?.imagePath = path
                    cardEntity?.let { it -> vModel.updateCards(it) }

                }

                childFragmentManager.beginTransaction()
                    .add(chooseImageDialogFragment, "")
                    .commitAllowingStateLoss()


            }
        }
    }

    private fun observeData() {
        vModel.getCards().observe(viewLifecycleOwner) {
            it?.let { cardList ->
                cardCount = cardList.size
                updateRecyclerView(cardList.toMutableList())
            }
        }
        vModel.insertCards()
    }


    private fun checkStoragePermission(): Boolean {
        return (context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
                == PackageManager.PERMISSION_GRANTED)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_add, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.add_card -> {
                vModel.addCard(++cardCount)
                true
            }
            else -> false
        }

    companion object {
        @JvmStatic
        fun newInstance() =
            CardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}