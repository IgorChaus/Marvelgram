package com.example.marvelvm.view

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marvelvm.MarvelApp
import com.example.marvelvm.R
import com.example.marvelvm.databinding.FragmentMainBinding
import com.example.marvelvm.model.Person
import com.example.marvelvm.viewmodel.AppViewModel
import com.example.marvelvm.viewmodel.AppViewModelFactory
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class MainFragment : Fragment(){
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    @Inject
    lateinit var factory: AppViewModelFactory

    val component by lazy{
        (requireActivity().application as MarvelApp).component
    }

    private val viewModel: AppViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[AppViewModel::class.java]
    }

    private lateinit var adapter: RVAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RVAdapter()
        adapter.itemClickListener = {
            launchItemScreen(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBar()

        with(binding){
            rvMain.adapter = adapter
            etSearch.addTextChangedListener {
                s -> viewModel.searchPerson(s.toString())
            }
        }

        viewModel.itemsLive.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupActionBar() {
        val mainActivity = activity as AppCompatActivity
        val actionBar = mainActivity.supportActionBar
        mainActivity.title = ""
        actionBar?.let{
            it.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(mainActivity, R.color.black))
            )
            it.setIcon(R.drawable.marvel)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun launchItemScreen(item: Person) {

        findNavController().navigate(MainFragmentDirections.actionMainFragmentToItemFragment(item))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}