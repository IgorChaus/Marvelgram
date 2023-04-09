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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.marvelvm.R
import com.example.marvelvm.databinding.FragmentMainBinding
import com.example.marvelvm.model.Person
import com.example.marvelvm.viewmodel.AppViewModel

class MainFragment : Fragment(), RVAdapter.ItemClickListener {
    private var binding: FragmentMainBinding? = null

    companion object {
        fun getIstance() = MainFragment()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val viewModel: AppViewModel by activityViewModels()
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val mainActivity = activity as AppCompatActivity

        val actionBar = mainActivity.supportActionBar
        actionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(mainActivity, R.color.black))
        )
        mainActivity.setTitle("")
        actionBar?.setIcon(R.drawable.marvel)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(false)

        val llm = SpecialLayout(mainActivity)

        //      val llm = GridLayoutManager(this, 3)
        binding?.rv1?.layoutManager = llm

        val adapter = RVAdapter(this)
        binding?.rv1?.adapter = adapter

        viewModel.itemsLiveData.observe(viewLifecycleOwner) {
            adapter.refreshUsers(it)
        }

        binding?.editText?.addTextChangedListener {
                s -> viewModel.searchPerson(s.toString())
        }
        return binding?.root

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}