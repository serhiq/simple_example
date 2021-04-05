package com.gmail.uia059466.test_for_work_db.transaction.addedittransaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.transaction.SelectCurrencyWithRubDialogFragment
import com.gmail.uia059466.test_for_work_db.db.transaction.TransactionEditTextState
import com.gmail.uia059466.test_for_work_db.db.transaction.TypeOperation
import com.gmail.uia059466.test_for_work_db.utils.InjectorUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.text.SimpleDateFormat

class AddEditTransactionFragment : Fragment() {

    private lateinit var viewModel: AddEditTransactionViewModel

    private lateinit var content: LinearLayout

    private lateinit var caption: LinearLayout

    private lateinit var vDividerCurrency: View
    private lateinit var rvAmountCurrency: RelativeLayout
    private lateinit var tvAmountCurrencyTitle: TextView
    private lateinit var tvAmountCurrency: TextView
    private lateinit var etRate: EditText
    private lateinit var llRateContatiner: LinearLayout
    private lateinit var tvRateInfo: TextView
    private lateinit var tvCurrentDate: TextView
    private lateinit var rvDate: RelativeLayout
    private lateinit var rvAccount: RelativeLayout
    private lateinit var tvAccount: TextView
    private lateinit var imgAccount: ImageView

    private lateinit var rvCurrency: RelativeLayout
    private lateinit var etComment: EditText

    private lateinit var etRubPrimaryAmount: EditText
    private lateinit var etRubSecondaryAmount: EditText

    private lateinit var etCurrPrimaryAmount: EditText
    private lateinit var etCurrSecondaryAmount: EditText

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
                R.layout.transaction_add_edit_fragment,
                container,
                false
        )
        content = view.findViewById(R.id.add_edit_transaction_content)
        caption = view.findViewById(R.id.caption_ll)

        etRubPrimaryAmount = view.findViewById(R.id.primary_main_edit_text)
        etRubSecondaryAmount = view.findViewById(R.id.main_secondary_currency)

        etCurrPrimaryAmount = view.findViewById(R.id.primary_secondary_edit_text)
        etCurrSecondaryAmount = view.findViewById(R.id.secondary_secondary_currency)

        vDividerCurrency = view.findViewById(R.id.divider_currency)
        tvAmountCurrencyTitle = view.findViewById(R.id.amount_secondary_title_tv)
        tvAmountCurrency = view.findViewById(R.id.current_currency_tv)

        rvAmountCurrency = view.findViewById(R.id.container_amount_secondary_rv)
        llRateContatiner = view.findViewById(R.id.rate_ll)

        etRate = view.findViewById(R.id.rate)

        tvRateInfo = view.findViewById(R.id.data)
        tvCurrentDate = view.findViewById(R.id.current_date_tv)

        rvDate = view.findViewById(R.id.date_rl)

        rvAccount = view.findViewById(R.id.account_rl)

        tvAccount = view.findViewById(R.id.account_current_tv)

        imgAccount = view.findViewById(R.id.img_account)

        rvCurrency = view.findViewById(R.id.currency_rl)

        etComment = view.findViewById(R.id.comment_et)

        setupViewModel()
        setupObservers()

        (activity as MainActivity).hideBottomNavigation()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_in_app_bar)

        setupOnBackPressed()
        setHasOptionsMenu(true)
        return view
    }


    private fun setupObservers() {

        viewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            it?.let { text ->
                val snackBar = Snackbar.make(content, it, Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        })

        viewModel.isVisibleEditMode.observe(viewLifecycleOwner, Observer {
            it?.let { isEditMode ->
                if (isEditMode) {
                    (activity as MainActivity).renderTitle("")
                    caption.visibility = View.VISIBLE

                } else {
                    caption.visibility = View.GONE
                    (activity as MainActivity).renderTitle("Новая транзакция")
                }
            }
        })

        viewModel.stateEditTextState.observe(viewLifecycleOwner, Observer {
            it?.let { state ->
                if (state.isUseCurrency) {
                    showCurrencyContainer()
                    etCurrPrimaryAmount.setText(state.amountCurrency)
                    etCurrSecondaryAmount.setText(state.amountOst)
                    etRubPrimaryAmount.setText(state.amountRub)
                    etRubSecondaryAmount.setText(state.amountKop)
                    etComment.setText(state.comment)

                    setupTextWatcherOnEditText()

                } else {
                    etRubPrimaryAmount.setText(state.amountRub)
                    etRubSecondaryAmount.setText(state.amountKop)
                    etComment.setText(state.comment)

                    deleteTextWatcherOnEditText()
                    clearAndHideCurrencyContainer()
                }
            }
        })

        viewModel.stateRate.observe(viewLifecycleOwner, Observer {
            it?.let { rate ->
                etRate.setText(rate)
            }
        })

        viewModel.transactionD.observe(viewLifecycleOwner, Observer {
            it?.let { trans ->
                tvAccount.text = trans.account.title
                imgAccount.setImageResource(trans.account.iconAccount.resId)
                rvAccount.setOnClickListener {
                    displaySelectAccountDialog(trans.account)
                }

                if (trans.fromCurrency == null || trans.fromCurrency == "RUB") {
                    tvAmountCurrency.text = "RUB"
                    clearAndHideCurrencyContainer()
                } else {
                    showCurrencyContainer()
                    tvAmountCurrency.text = trans.fromCurrency
                    tvAmountCurrencyTitle.text = "Сумма, ${trans.fromCurrency}"
                }

                rvCurrency.setOnClickListener {
                    displayCurrencyPickerDialog(trans.fromCurrency)
                }


                rvDate.setOnClickListener {
                    displayDatePicker()
                }

                val simpleFormat = SimpleDateFormat("dd MMMM")
                tvCurrentDate.text = simpleFormat.format(trans.date)
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

    private fun displaySelectAccountDialog(account: UserAccount) {

        val dialog = SelectAccountDialogFragment()
        dialog.exsistLisAccount.addAll(viewModel.listAccount)
        dialog.selectedNow = account
        dialog.onOk = {
            viewModel.selectAccount(dialog.selectedNow)
            dialog.dismiss()
        }

        requireActivity().supportFragmentManager.let { dialog.show(it, "editWord") }
    }

    private val rubSumChanger: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            updateCalcNewSumInRub()
        }

        override fun beforeTextChanged(s: CharSequence,
                                       start: Int,
                                       count: Int,
                                       after: Int) {
        }

        override fun onTextChanged(s: CharSequence,
                                   start: Int,
                                   before: Int,
                                   count: Int) {
        }
    }

    private fun updateCalcNewSumInRub() {
        val result = RateCalculator.calculateInRub(etRate.text.toString(),
                                                   NumberStr(etCurrPrimaryAmount.text.toString(), etCurrSecondaryAmount.text.toString()))
        etRubPrimaryAmount.setText(result.integerNumberPart)
        etRubSecondaryAmount.setText(result.fractionNumberPart)
    }

    private val currencySumChanger: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            updateSumCurrency()
        }

        override fun beforeTextChanged(s: CharSequence,
                                       start: Int,
                                       count: Int,
                                       after: Int) {
        }

        override fun onTextChanged(s: CharSequence,
                                   start: Int,
                                   before: Int,
                                   count: Int) {
        }
    }

    private fun updateSumCurrency() {
        val result = RateCalculator.calculateInCurrencyAmount(etRate.text.toString(),
                                                              NumberStr(etRubPrimaryAmount.text.toString(), etRubSecondaryAmount.text.toString()))
        etCurrPrimaryAmount.setText(result.integerNumberPart)
        etCurrSecondaryAmount.setText(result.fractionNumberPart)
    }

    private fun setupTextWatcherOnEditText() {
        etRate.addTextChangedListener(rubSumChanger)
        etCurrPrimaryAmount.addTextChangedListener(rubSumChanger)
        etCurrSecondaryAmount.addTextChangedListener(rubSumChanger)
//        etRubPrimaryAmount.addTextChangedListener(currencySumChanger)
    }

    private fun deleteTextWatcherOnEditText() {
        etRate.removeTextChangedListener(rubSumChanger)
        etCurrPrimaryAmount.removeTextChangedListener(rubSumChanger)
        etCurrSecondaryAmount.removeTextChangedListener(rubSumChanger)
//        etRubPrimaryAmount.removeTextChangedListener(currencySumChanger
//        )
    }

    private fun displayDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Выбирите дату операции")
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            viewModel.selectDate(selection)
        }
        picker.show(parentFragmentManager, "date_picker_tag")
    }

    private fun clearAndHideCurrencyContainer() {
        llRateContatiner.visibility = View.GONE
        rvAmountCurrency.visibility = View.GONE
    }

    private fun showCurrencyContainer() {
        llRateContatiner.visibility = View.VISIBLE
        rvAmountCurrency.visibility = View.VISIBLE
    }

    private fun displayCurrencyPickerDialog(fromCurrency: String?) {

        val dialog = SelectCurrencyWithRubDialogFragment()

        val exsistCurrency = viewModel.listCurrency.map { it.code }

        val nowRate = fromCurrency ?: "RUB"
        dialog.exsistListCurrency.addAll(exsistCurrency)
        dialog.selectedNow = nowRate

        dialog.onOk = {
            viewModel.selectCurrency(dialog.selectedNow)
            viewModel.updateEditTextStateT(requestCurrentEditTextState(), dialog.selectedNow
                    ?: "RUB")
            dialog.dismiss()
        }
        requireActivity().supportFragmentManager.let { dialog.show(it, "editWord") }
    }

    private fun requestCurrentEditTextState(): TransactionEditTextState {

        val currentCurrency = viewModel.transactionD.value?.fromCurrency
        return if (currentCurrency != null && currentCurrency != "RUB") {
            TransactionEditTextState(
                    amountRub = etRubPrimaryAmount.text.toString(),
                    amountKop = requestNumberWithAdd(etRubSecondaryAmount),
                    amountCurrency = etCurrPrimaryAmount.text.toString(),
                    amountOst = requestNumberWithAdd(etCurrPrimaryAmount),
                    comment = etComment.text.toString(),
                    isUseCurrency = true)

        } else {
            TransactionEditTextState(
                    amountRub = etRubPrimaryAmount.text.toString(),
                    amountKop = requestNumberWithAdd(etRubSecondaryAmount),
                    amountCurrency = BigDecimal.ZERO.toPlainString(),
                    amountOst = BigDecimal.ZERO.toPlainString(),
                    comment = etComment.text.toString(),
                    isUseCurrency = false)
        }
    }

    private fun requestNumberWithAdd(et: EditText): String {
        val text = et.text.trim().toString()
        if (text.length == 1) {
            return "${text}0"
        } else if (text.length == 2) {
            return text
        }
        return "0"
    }


    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = InjectorUtils.provideViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddEditTransactionViewModel::class.java]
        val idTransaction = arguments?.getLong("idTransaction") ?: 0L
        val codeOperation = arguments?.getString("codeOperation") ?: TypeOperation.OUTCOME.code
        val accountId = arguments?.getLong("idAccount") ?: 0L
        viewModel.start(idTransaction, codeOperation, accountId)
    }

    override fun onCreateOptionsMenu(menu: Menu,
                                     inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.addedit_rate_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                goBack()
                return true
            }

            R.id.menu_save -> {
                viewModel.trySave(requestCurrentEditTextState())
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
}