package com.example.marvelvm.view

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
    private lateinit var item: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
        adapter = RVAdapter()
        adapter.itemClickListener = {
            showItem(it)
        }
    }

    private fun parsArgs(){
        requireArguments().getParcelable<Person>(KEY_ITEM)?.let {
            item = it
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
        setupActionBar()
        showItem(item)
        binding.rvBottom.adapter = adapter

        viewModel.itemsLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

    private fun setupActionBar() {
        val mainActivity = activity as AppCompatActivity
        mainActivity.window.setBackgroundDrawable(
            ContextCompat
                .getDrawable(requireContext(), R.color.black)
        )

        //Выводим стрелочку "Назад"
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


    fun showItem(item: Person){

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

    companion object {
        fun getInstance(item: Person): Fragment{
            return ItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ITEM,item)
                }
            }
        }

        private const val KEY_ITEM = "item"
    }
}
