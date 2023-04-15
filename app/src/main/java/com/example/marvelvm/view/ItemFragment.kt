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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.databinding.FragmentItemBinding
import com.example.marvelvm.model.Person
import com.example.marvelvm.viewmodel.AppViewModel

@RequiresApi(Build.VERSION_CODES.O)
class ItemFragment: Fragment() {
    private var binding: FragmentItemBinding? = null
    private lateinit var adapter: RVAdapter

    companion object {
        fun getInstance() = ItemFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RVAdapter()
        adapter.itemClickListener = {
            changeItemScreen(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val viewModel: AppViewModel by activityViewModels()

        binding = FragmentItemBinding.inflate(inflater, container, false)

        val name = this.arguments?.getString("name","")
        val description = this.arguments?.getString("description","")
        val photo = this.arguments?.getString("photo")

        val mainActivity = activity as AppCompatActivity
        mainActivity.window.setBackgroundDrawable(ContextCompat
            .getDrawable(requireContext(), R.color.black))

        //Выводим стрелочку "Назад"
        val actionBar = mainActivity.supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat
                .getColor(requireContext(), R.color.black)))

        actionBar?.setIcon(null)
        mainActivity.title = name

        Glide.with(this).load(photo).into(binding!!.imagePhoto)

        binding?.textView?.text = description

       /* val llm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,
            false)
        binding?.rv2?.layoutManager = llm*/

        binding?.rv2?.adapter = adapter

        viewModel.itemsLiveData.observe(viewLifecycleOwner) {
            adapter.refreshUsers(it)
        }

        return binding?.root
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

}
