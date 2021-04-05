package com.gmail.uia059466.test_for_work_db.setting.currencyrate

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrency
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser
import com.gmail.uia059466.test_for_work_db.setting.currency.CurrencyAdapter
import com.gmail.uia059466.test_for_work_db.setting.currency.CurrencyModel
import com.gmail.uia059466.test_for_work_db.setting.currency.SelectCurrencyFromAllDialogFragment
import com.gmail.uia059466.test_for_work_db.utls.InjectorUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CurrencyRateListFragment : Fragment(), CurrencyRateAdapter.CurrencyListener{

    private lateinit var coordinatorLayout: CoordinatorLayout

    private lateinit var listRv: RecyclerView
    private lateinit var viewModel: CurrencyRateModel

    private val adapter = CurrencyRateAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.currency_list_fragment,
            container,
            false
        )
        coordinatorLayout = view.findViewById<CoordinatorLayout>(R.id.currency_list_content)

        listRv = view.findViewById(R.id.list)

        setupViewModel()
        setupObservers()

       val addFab=view.findViewById<FloatingActionButton>(R.id.add_fab)
        addFab.setOnClickListener {
            navigateToNewRate()
        }

        listRv.layoutManager = LinearLayoutManager(activity)
        listRv.adapter = adapter

        (activity as MainActivity).renderTitle("Курс валют")
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).hideBottomNavigation()
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(null)

        setupOnBackPressed()
        setHasOptionsMenu(true)
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshList()
    }
    private fun setupObservers() {
        viewModel.lists.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            it?.let {text->
                val snackBar = Snackbar.make(coordinatorLayout,it, Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        })
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = InjectorUtils.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyRateModel::class.java]
    }


    private fun navigateToNewRate() {
        val action=R.id.action_currencyRateListFragment_to_addEditRateFragment
        findNavController().navigate(action)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.currency_edit_menu, menu)
    }

//    todo
    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuRateAs = menu.findItem(R.id.menu_edit_mode)
//        menuRateAs.isVisible = if ()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                goBack()
                return true
            }
            R.id.menu_edit_mode -> {
                adapter.isEditMode=!adapter.isEditMode
                adapter.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteItem(rate: RatesUser) {
        viewModel.deleteIfMay(rate)
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        goBack()
                    }
                })
    }


    private fun goBack() {
        findNavController().navigateUp()
    }

}