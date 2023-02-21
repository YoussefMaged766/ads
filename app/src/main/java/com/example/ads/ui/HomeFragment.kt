package com.example.ads.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ads.R
import com.example.ads.adapter.AdAdapter
import com.example.ads.databinding.FragmentHomeBinding
import com.example.ads.model.Ads
import com.example.ads.utils.Status
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
  lateinit   var adapter : AdAdapter
lateinit var layoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null){
            setHasOptionsMenu(true)

        }
        viewModel.getAllAds()
        viewModel.loginLiveData.observe(viewLifecycleOwner){
            it.let {
                when(it.status){
                    Status.SUCCESS->{
                        adapter = AdAdapter(it.data!!)
                        binding.recycleAds.adapter =adapter
                        binding.recycleAds.layoutManager = LinearLayoutManager(requireContext())

                    }
                    Status.LOADING->{}
                    Status.ERROR->{
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


//        viewModel.loginLiveData.observe(viewLifecycleOwner, Observer {
//            adapter = AdAdapter(it)
//            Log.e( "onViewCreated: ", it.toString())
//
//        })
        binding.recycleAds.apply {
            this.adapter=adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

            viewModel.recyclerLiveDataError.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.optional_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addNew -> {
                findNavController().navigate(R.id.action_nav_home_to_addNewAdFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}