package com.example.marvelvm.view

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

    private var binding: FragmentItemBinding? = null
    private lateinit var adapter: RVAdapter
    private lateinit var item: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
        adapter = RVAdapter()
        adapter.itemClickListener = {
            changeItemScreen(it)
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
        savedInstanceState: Bundle?): View? {

        binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: AppViewModel by activityViewModels()

        val mainActivity = activity as AppCompatActivity
        mainActivity.window.setBackgroundDrawable(ContextCompat
            .getDrawable(requireContext(), R.color.black))

        //Выводим стрелочку "Назад"
        val actionBar = mainActivity.supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat
            .getColor(requireContext(), R.color.black)))

        actionBar?.setIcon(null)

        val photo = item.thumbnail.path + "." + item.thumbnail.extension
        mainActivity.title = item.name

        Glide.with(this).load(photo).into(binding!!.imagePhoto)

        binding?.textView?.text = item.description
        binding?.rv2?.adapter = adapter

        viewModel.itemsLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }


    fun changeItemScreen(item: Person){

        val mainActivity = activity as AppCompatActivity
        mainActivity.title = item.name

        val imagePhoto: ImageView = requireView().findViewById(R.id.imagePhoto)
        val photo = item.thumbnail.path + "." + item.thumbnail.extension
        Glide.with(this).load(photo).into(imagePhoto)

        val textView: TextView = requireView().findViewById(R.id.textView)
        textView.text = item.description

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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
