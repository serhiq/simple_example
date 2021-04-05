package com.gmail.uia059466.test_for_work_db.account.accountdisplay

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.account.AmountFormatter
import com.gmail.uia059466.test_for_work_db.db.transaction.TypeOperation
import com.gmail.uia059466.test_for_work_db.utils.InjectorUtils
import java.math.BigDecimal

class AccountDisplayFragment : Fragment() {

    private lateinit var viewModel: AccountDisplayViewModel

    private lateinit var tvAmount: TextView
    private lateinit var flEdit: FrameLayout
    private lateinit var flDelete: FrameLayout

    private lateinit var llIncome: LinearLayout
    private lateinit var llOutcome: LinearLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
                R.layout.account_display_fragment,
                container,
                false
        )
        tvAmount = view.findViewById(R.id.amount_tv)
        flEdit = view.findViewById(R.id.edit_fl)
        flDelete = view.findViewById(R.id.delete_fl)

        llIncome= view.findViewById(R.id.income_ll)
        llOutcome= view.findViewById(R.id.outcome_ll)
        setupViewModel()
        setupObservers()

        (activity as MainActivity).hideBottomNavigation()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(null)

        setupOnBackPressed()
        setHasOptionsMenu(true)
        return view
    }

    private fun navigateToCreateOutcome(idAccount: Long) {
        val action=R.id.action_accountDisplayFragment_to_addEditTransactionFragment
        val bundle = bundleOf("idAccount" to idAccount,"codeOperation" to TypeOperation.OUTCOME.code)
        findNavController().navigate(action,bundle)
    }

    private fun navigateToCreateIncome(idAccount: Long) {
            val action=R.id.action_accountDisplayFragment_to_addEditTransactionFragment
        val bundle = bundleOf("idAccount" to idAccount,"codeOperation" to TypeOperation.INCOME.code)
            findNavController().navigate(action,bundle)
    }

    private fun setupObservers() {
        viewModel.account.observe(viewLifecycleOwner, Observer {
            it?.let { account ->
                llIncome.setOnClickListener {
                    navigateToCreateIncome(account.id)
                }

                llOutcome.setOnClickListener {
                    navigateToCreateOutcome(account.id)
                }


                (activity as MainActivity).renderTitle(title = account.title)
                tvAmount.text = AmountFormatter.createAmountString(account.amount.divide(BigDecimal(100)))

                flDelete.setOnClickListener {
                    val dialog = DeleteUserAccountDialogFragment()
                    dialog.onOk = {
                        viewModel.deleteUserAccount()
                    }
                    requireActivity().supportFragmentManager.let { dialog.show(it, "delete_user") }
                }

                flEdit.setOnClickListener {
                    val action = R.id.action_accountDisplayFragment_to_addEditAccountFragment
                    val bundle = bundleOf("idAccount" to account.id)
                    findNavController().navigate(action, bundle)
                }
            }
        })


        viewModel.runBack.observe(viewLifecycleOwner, Observer {
            it?.let { isRunBack ->
                if (isRunBack) {
                    findNavController().navigateUp()
                }
            }
        })

    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = InjectorUtils.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AccountDisplayViewModel::class.java]

        val idAccount = arguments?.getLong("idAccount") ?: 0L
        viewModel.start(idAccount)
    }

    override fun onCreateOptionsMenu(menu: Menu,
                                     inflater: MenuInflater) {
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
        viewModel.refresh()
    }
}