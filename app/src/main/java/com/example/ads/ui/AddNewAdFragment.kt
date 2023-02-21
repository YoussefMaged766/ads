package com.example.ads.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.ads.databinding.FragmentAddNewAdBinding
import com.example.ads.model.Ads
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewAdFragment : Fragment() {

    lateinit var binding: FragmentAddNewAdBinding
    private var imgUri: Uri? = null
    private val viewModel by viewModels<AddNewViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewAdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getImagePicked()
        binding.btnAdd.setOnClickListener {
            viewModel.upload(getAdDetails(),imgUri!!)
        }



    }

    fun getImagePicked() {

        val loadFile2 = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                imgUri = it
                Glide.with(requireContext()).asBitmap().load(it).into(binding.imageAd)
            }
        }

        binding.viewUploadImage.setOnClickListener {
            loadFile2.launch("image/*")
        }
    }

    fun getAdDetails():Ads{
        val ad = Ads(description = binding.editTextDescription.text.toString(),
        phone = binding.editTextPhone.text.toString(),
        price = binding.editTextPrice.text.toString(),
        address = binding.editTextAddress.text.toString(),
        imgURL = null,
        type = getSelectedType())
        return ad
    }

    fun getSelectedType():String{
        val radioButtonID: Int = binding.radioGroup.checkedRadioButtonId
        val radioButton: View = binding.radioGroup.findViewById(radioButtonID)
        val idx: Int = binding.radioGroup.indexOfChild(radioButton)
        val r: RadioButton = binding.radioGroup.getChildAt(idx) as RadioButton
        val selectedtext: String = r.text.toString()
        return selectedtext
    }
}


