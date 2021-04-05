package com.gmail.uia059466.test_for_work_db.transaction

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
import com.gmail.uia059466.test_for_work_db.utls.InjectorUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class TransactionListFragment : Fragment(), TransactionAdapter.Listener {

    private lateinit var coordinatorLayout: CoordinatorLayout

    private lateinit var listRv: RecyclerView
    private lateinit var viewModel: TransactionViewModel

    private val adapter = TransactionAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.transaction_list_fragment,
            container,
            false
        )
        coordinatorLayout = view.findViewById<CoordinatorLayout>(R.id.transaction_list_content)

        listRv = view.findViewById(R.id.list)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.add_fab)
        fabAdd.setOnClickListener {
            navigateToCreateTransaction()
        }

        setupViewModel()
        setupObservers()

        listRv.layoutManager = LinearLayoutManager(activity)
        listRv.adapter = adapter

        (activity as MainActivity).renderTitle("Транзакции")
        (activity as MainActivity).showBottomNavigation()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = InjectorUtils.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[TransactionViewModel::class.java]
    }

    private fun navigateToCreateTransaction() {
        val action=R.id.action_navigation_transaction_to_addEditTransactionFragment
        findNavController().navigate(action)
    }

    override fun onListClicked(id: Long) {
    //        val action = R.id.action_accountsListFragment_to_accountDisplayFragment
//        val bundle = bundleOf("idAccount" to id)
//        findNavController().navigate(action, bundle)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                goBack()
                return true
            }
       }
        return super.onOptionsItemSelected(item)
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

    override fun onResume() {
        super.onResume()
        viewModel.refreshList()
    }
}