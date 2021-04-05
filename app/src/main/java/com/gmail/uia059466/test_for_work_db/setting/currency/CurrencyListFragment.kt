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
import com.gmail.uia059466.test_for_work_db.utils.InjectorUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CurrencyListFragment :Fragment(), CurrencyAdapter.CurrencyListener{

    private lateinit var coordinatorLayout: CoordinatorLayout

    private lateinit var listRv: RecyclerView
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

        val title=getString(R.string.currencies_list_title)
        (activity as MainActivity).renderTitle(title)
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
                val snackBar = Snackbar.make(coordinatorLayout,text, Snackbar.LENGTH_LONG)
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