package com.example.marvelvm.view

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.databinding.FragmentItemBinding
import com.example.marvelvm.model.Person
import com.example.marvelvm.viewmodel.AppViewModel

@RequiresApi(Build.VERSION_CODES.O)
class ItemFragment: Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding: FragmentItemBinding
        get() = _binding ?: throw RuntimeException("FragmentItemBinding == null")


    private lateinit var adapter: RVAdapter

    private val args by navArgs<ItemFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RVAdapter()
        adapter.itemClickListener = {
            showItem(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: AppViewModel by activityViewModels()

        setBackGroundColor()
        setupActionBar()
        setUpButtonListener()
        showItem(args.item)
        binding.rvBottom.adapter = adapter

        viewModel.itemsLive.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

    private fun setBackGroundColor() {
        val mainActivity = activity as AppCompatActivity
        mainActivity.window.setBackgroundDrawable(
            ContextCompat
                .getDrawable(requireContext(), R.color.black)
        )
    }

    private fun setUpButtonListener() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                findNavController().popBackStack()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupActionBar() {

        val mainActivity = activity as AppCompatActivity
        val actionBar = mainActivity.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat
                    .getColor(requireContext(), R.color.black)
            )
        )
        actionBar?.setIcon(null)
    }


    private fun showItem(item: Person){

        val mainActivity = activity as AppCompatActivity
        mainActivity.title = item.name
        val photo = item.thumbnail.path + "." + item.thumbnail.extension
        Glide.with(this).load(photo).into(binding.imagePhoto)
        binding.tvDescription.text = item.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
