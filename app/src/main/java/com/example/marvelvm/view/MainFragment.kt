package com.example.marvelvm.view

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kode_viewmodel.source.DataRepository
import com.example.kode_viewmodel.source.RetrofitInstance
import com.example.marvelvm.MainActivity
import com.example.marvelvm.R
import com.example.marvelvm.model.Person
import com.example.marvelvm.viewmodel.AppViewModel

class MainFragment : Fragment(), RVAdapter.ItemClickListener {


    companion object {
        fun getIstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val viewModel = ViewModelProvider(requireActivity(), MainActivity.factory).get(AppViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val mainActivity = activity as AppCompatActivity

        val actionBar = mainActivity.supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat
                    .getColor(mainActivity, R.color.black))
        )
        mainActivity.setTitle("")
        actionBar?.setIcon(R.drawable.marvel)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(false)

        val rv: RecyclerView = view.findViewById(R.id.rv1)
        val llm = SpecialLayout(mainActivity)

        //      val llm = GridLayoutManager(this, 3)
        rv.layoutManager = llm
        val adapter = RVAdapter(this)
        rv.adapter = adapter

        viewModel.itemsLiveData.observe(viewLifecycleOwner) {
            adapter.refreshUsers(it)
        }

        val editText: EditText = view.findViewById(R.id.editText)
        editText.addTextChangedListener {
                s -> viewModel.searchPerson(s.toString())
        }
        return view

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(item: Person){
        val bundle = Bundle()
        val itemFragment = ItemFragment.getInstance()
        bundle.putString("name", item.name)
        bundle.putString("description", item.description)
        bundle.putString("photo", item.thumbnail.path + "." + item.thumbnail.extension)
        itemFragment.setArguments(bundle)

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, itemFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

}