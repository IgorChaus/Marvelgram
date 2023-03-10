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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.model.Person
import com.example.marvelvm.viewmodel.AppViewModel

@RequiresApi(Build.VERSION_CODES.O)
class ItemFragment: Fragment(), RVAdapter.ItemClickListener {

    companion object {
        fun getInstance() = ItemFragment()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

     //   val viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        val viewModel: AppViewModel by activityViewModels()

        val view = inflater.inflate(R.layout.fragment_item, container, false)

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

        val imagePhoto: ImageView = view.findViewById(R.id.imagePhoto)
        Glide.with(this).load(photo).into(imagePhoto)

        val textView: TextView = view.findViewById(R.id.textView)
        textView.text = description

        val rv: RecyclerView = view.findViewById(R.id.rv2)
        val llm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv.layoutManager = llm
        val adapter = RVAdapter(this)
        rv.adapter = adapter

        viewModel.itemsLiveData.observe(viewLifecycleOwner) {
            adapter.refreshUsers(it)
        }

        return view
    }


    override fun onItemClick(item: Person){

        val mainActivity = activity as AppCompatActivity
        mainActivity.title = item.name

        val imagePhoto: ImageView = requireView().findViewById(R.id.imagePhoto)
        val photo = item.thumbnail.path + "." + item.thumbnail.extension
        Glide.with(this).load(photo).into(imagePhoto)

        val textView: TextView = requireView().findViewById(R.id.textView)
        textView.text = item.description

    }
}
