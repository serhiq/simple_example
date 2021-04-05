package com.gmail.uia059466.test_for_work_db.setting.rateedit

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
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser
import com.gmail.uia059466.test_for_work_db.utils.InjectorUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class AddEditRateFragment : Fragment() {

    private lateinit var viewModel: AddEditRateModel

    private lateinit var dateRv: RelativeLayout
    private lateinit var dateTv: TextView

    private lateinit var selectCurrencyRv: RelativeLayout
    private lateinit var selectCurrencyTv: TextView

    private lateinit var rateEt: EditText
    private lateinit var rateTv: TextView

    private lateinit var downloadRateTv: ImageButton
    private lateinit var content: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.addeditratefragment,
            container,
            false
        )
        content = view.findViewById(R.id.addedit_content)
        dateRv = view.findViewById(R.id.date_rl)
        dateTv = view.findViewById(R.id.date_tv)

        selectCurrencyRv = view.findViewById(R.id.select_currency_rl)
        selectCurrencyTv = view.findViewById(R.id.current_currency_tv)

        rateEt = view.findViewById(R.id.rate)
        rateTv = view.findViewById(R.id.rate_description_tv)

        downloadRateTv = view.findViewById(R.id.rate_download_img)

        dateRv.setOnClickListener {
            displayTimePicker()
        }

        selectCurrencyRv.setOnClickListener {
            displayCurrencyPickerDialog()
        }
        setRate(0.0)

        setupViewModel()
        setupObservers()

        val title = getString(R.string.rates_add_title)
        (activity as MainActivity).renderTitle(title)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_in_app_bar)

        setupOnBackPressed()
        setHasOptionsMenu(true)
        return view
    }

    private fun displayCurrencyPickerDialog() {

        val dialog = SelectUserCurrencyDialogFragment()

        val exsistCurrency = viewModel.lists.value

        val nowRate = viewModel.rateLiveDate.value?.currency?.currencyCode


        if (exsistCurrency != null && nowRate != null) {
            dialog.exsistListCurrency.addAll(exsistCurrency)
            dialog.selectedNow = nowRate
        }

        dialog.onOk = {
            viewModel.selectCurrency(dialog.selectedNow)
            dialog.dismiss()
        }
        requireActivity().supportFragmentManager.let { dialog.show(it, "editWord") }
    }

    private fun displayTimePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Выбирите дату")
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            viewModel.selectDate(selection)
        }
        picker.show(parentFragmentManager, "date_picker_tag")

    }

    private fun setupObservers() {

        viewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            it?.let { text ->
                val snackBar = Snackbar.make(content, it, Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        })

        viewModel.rateLiveDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val simpleFormat = SimpleDateFormat("dd MMMM")
                dateTv.text = simpleFormat.format(it.data)

                selectCurrencyTv.text = it.currency.currencyCode

                rateEt.setText(it.rate.toString())
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
        viewModel = ViewModelProvider(this, viewModelFactory)[AddEditRateModel::class.java]
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        menu.clear()
        inflater.inflate(R.menu.addedit_rate_menu, menu)

    }


    private fun getRate(): BigDecimal? {
        return try {
            var rateText: String = rateEt.text.toString().trim()
            rateText = rateText.replace(',', '.')
            return BigDecimal(rateText)
        } catch (ex: NumberFormatException) {
            null
        }
    }

    private val nf = DecimalFormat("0.00000")

    private fun setRate(r: Double) {
        rateEt.removeTextChangedListener(rateWatcher)
        rateEt.setText(nf.format(Math.abs(r)))
        rateEt.addTextChangedListener(rateWatcher)
    }

    private val rateWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            updateRateInfo()
        }

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }
    }

    fun updateRateInfo() {
        val currentRate = getRate()
        val currencyTo = viewModel.rateLiveDate.value?.currency
        val isCorrectRate = currentRate != null || currentRate != BigDecimal.ZERO

        if (!isCorrectRate) {
            rateTv.text = ""
        } else if (currentRate != null && currentRate != BigDecimal.ZERO && currencyTo != null) {
            rateTv.text = RatesUser.createRateDescription(currentRate, currencyTo)
        } else {
            rateTv.text = ""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                goBack()

                return true
            }

            R.id.menu_save -> {
                viewModel.trySave(getRate())
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
}