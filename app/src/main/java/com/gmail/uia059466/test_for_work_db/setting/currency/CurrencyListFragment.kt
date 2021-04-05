package com.gmail.uia059466.test_for_work_db.setting.currency

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
import com.gmail.uia059466.test_for_work_db.utls.InjectorUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CurrencyListFragment :Fragment(), CurrencyAdapter.CurrencyListener{

// todo   если в списке только один рубль режим редактирования не отображается

    private lateinit var coordinatorLayout: CoordinatorLayout

    private lateinit var listRv: RecyclerView
    val cachedCurrenteCode= mutableListOf<String>()
    private lateinit var viewModel: CurrencyModel

    private val adapter = CurrencyAdapter(this)

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

            val dialog = SelectCurrencyFromAllDialogFragment()
            val exsistCurrency=viewModel.lists.value?.map { it.code }
            if (exsistCurrency!=null){
                dialog.exsistListCurrency.addAll(exsistCurrency)
            }

            dialog.onOk = {
                viewModel.insertInDbAndUpdateList(dialog.selectedNow)
                dialog.dismiss()
            }
            requireActivity().supportFragmentManager.let { dialog.show(it, "editWord") }
        }

        listRv.layoutManager = LinearLayoutManager(activity)
        listRv.adapter = adapter

        (activity as MainActivity).renderTitle("Валюты")
        (activity as MainActivity).hideBottomNavigation()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(null)

        setupOnBackPressed()
        setHasOptionsMenu(true)
        return view
    }

    private fun setupObservers() {
        viewModel.lists.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
                requireActivity().invalidateOptionsMenu()
            }
        })
        viewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            it?.let {text->
                val snackBar = Snackbar.make(coordinatorLayout,it, Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        })
        viewModel.isVisibleEditMode.observe(viewLifecycleOwner, Observer {
            it?.let {
                requireActivity().invalidateOptionsMenu()
            }
        })
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = InjectorUtils.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyModel::class.java]
    }


    private fun navigateToNewList() {
//
//        val action=R.id.action_navigation_account_to_editAccountFragment
////        val bundle = bundleOf("mode" to SelectAdapter.MODE_SELECT)
//        findNavController().navigate(action)


//        (activity as MainActivityImpl).openNewList()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.currency_edit_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
    val menuEdit = menu.findItem(R.id.menu_edit_mode)

    val isVisible=viewModel.isVisibleEditMode.value?:false
      menuEdit.isVisible = isVisible
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

//                viewModel.enableFavoritesSelect()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteItem(currency: UserCurrency) {
        viewModel.deleteIfMay(currency)
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