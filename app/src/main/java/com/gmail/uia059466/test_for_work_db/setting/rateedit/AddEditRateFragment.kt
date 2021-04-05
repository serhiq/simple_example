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
import com.gmail.uia059466.test_for_work_db.utls.InjectorUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class AddEditRateFragment : Fragment(){



    private lateinit var viewModel: AddEditRateModel

    private lateinit var dateRv: RelativeLayout
    private lateinit var dateTv: TextView

    private lateinit var selectCurrencyRv: RelativeLayout
    private lateinit var selectCurrencyTv: TextView

    private lateinit var rateEt: EditText
    private lateinit var rateTv: TextView

    private lateinit var downloadRateTv: ImageButton
    private lateinit var content:LinearLayout

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
        content=view.findViewById(R.id.addedit_content)
        dateRv=view.findViewById(R.id.date_rl)
        dateTv=view.findViewById(R.id.date_tv)

        selectCurrencyRv=view.findViewById(R.id.select_currency_rl)
        selectCurrencyTv=view.findViewById(R.id.current_currency_tv)

        rateEt=view.findViewById(R.id.rate)
        rateTv=view.findViewById(R.id.rate_description_tv)

        downloadRateTv=view.findViewById(R.id.rate_download_img)

        dateRv.setOnClickListener {
            displayTimePicker()
        }

        selectCurrencyRv.setOnClickListener {
            displayCurrencyPickerDialog()
        }
        setRate(0.0)

        setupViewModel()
        setupObservers()

        (activity as MainActivity).renderTitle("Обменный курс")
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_in_app_bar)

        setupOnBackPressed()
        setHasOptionsMenu(true)
        return view
    }
// todo как сделать
    private fun displayCurrencyPickerDialog() {

       val dialog = SelectUserCurrencyDialogFragment()

        val exsistCurrency=viewModel.lists.value

        val nowRate=viewModel.rateLiveDate.value?.currency?.currencyCode


        if (exsistCurrency!=null&&nowRate!=null){
            dialog.exsistListCurrency.addAll(exsistCurrency)
            dialog.selectedNow=nowRate
//            dialog.selectedNow=nowRate.idCurrency
        }

        dialog.onOk = {
            viewModel.selectCurrency(dialog.selectedNow)
            dialog.dismiss()
        }
        requireActivity().supportFragmentManager.let { dialog.show(it, "editWord") }
    }

    private fun displayTimePicker() {
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText("Please Choose your date")
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener { selection ->
                viewModel.selectDate(selection)
//                binding.hintButton.titleText = simpleFormat.format(Date(selection))

//                Date and Time Pattern                Result
//                -----------------------------        ---------------------------------
//                "yyyy.MM.dd G 'at' HH:mm:ss z"       2001.07.04 AD at 12:08:56 PDT
//                "EEE, MMM d, ''yy"                   Wed, Jul 4, '01
//                "h:mm a"                             12:08 PM
//                "hh 'o''clock' a, zzzz"              12 o'clock PM, Pacific Daylight Time
//                "K:mm a, z"                          0:08 PM, PDT
//                "yyyyy.MMMMM.dd GGG hh:mm aaa"       02001.July.04 AD 12:08 PM
//                "EEE, d MMM yyyy HH:mm:ss Z"         Wed, 4 Jul 2001 12:08:56 -0700
//                "yyMMddHHmmssZ"                      010704120856-0700
//                "yyyy-MM-dd'T'HH:mm:ss.SSSZ"         2001-07-04T12:08:56.235-0700
//                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"       2001-07-04T12:08:56.235-07:00
//                "YYYY-'W'ww-u"                       2001-W27-3
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


//                binding.hintButton.titleText =

//                todo
//                adapter.setData(it)
//                adapter.notifyDataSetChanged()
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


    private fun navigateToNewList() {
//
//        val action=R.id.action_navigation_account_to_editAccountFragment
////        val bundle = bundleOf("mode" to SelectAdapter.MODE_SELECT)
//        findNavController().navigate(action)


//        (activity as MainActivityImpl).openNewList()
    }



    override fun onCreateOptionsMenu(menu: Menu,
                                     inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.addedit_rate_menu, menu)

    }


    fun getRate(): BigDecimal? {
        return try {
            var rateText: String = rateEt.text.toString().trim() // text(rateEt.text?:" ")
            if (rateText != null) {
                rateText = rateText.replace(',', '.')
                return BigDecimal(rateText)
            }
            BigDecimal.ZERO
        } catch (ex: NumberFormatException) {
            null
        }
    }

    private val nf = DecimalFormat("0.00000")


    fun setRate(r: Double) {
        rateEt.removeTextChangedListener(rateWatcher)
        rateEt.setText(nf.format(Math.abs(r)))
        rateEt.addTextChangedListener(rateWatcher)
    }

    private val rateWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            updateRateInfo()
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

//
//    private fun calculateRate() {
//        val amountFrom: Long = amountInputFrom.getAmount()
//        val amountTo: Long = amountInputTo.getAmount()
//        val r = 1.0f * amountTo / amountFrom
//        if (!java.lang.Float.isNaN(r)) {
//            rateNode.setRate(r)
//        }
//        rateNode.updateRateInfo()
//    }

    fun updateRateInfo() {
        val currentRate = getRate()
        val currencyTo = viewModel.rateLiveDate.value?.currency
        val isCorrectRate=currentRate!=null||currentRate!= BigDecimal.ZERO

        if(!isCorrectRate){
            rateTv.text = ""
        }else if (currentRate!=null&&currentRate!= BigDecimal.ZERO&&currencyTo!=null) {
            rateTv.text = RatesUser.createRateDescription(currentRate, currencyTo)
        }else{
            rateTv.text = ""
        }
    }


//    todo это в расчет валюты
//    private fun updateToAmountFromRate() {
//        val r: Double = rateNode.getRate()
//        val amountFrom: Long = amountInputFrom.getAmount()
//        val amountTo = Math.floor(r * amountFrom).toLong()
//        amountInputTo.setOnAmountChangedListener(null)
//        amountInputTo.setAmount(amountTo)
//        rateNode.updateRateInfo()
//        amountInputTo.setOnAmountChangedListener(onAmountToChangedListener)
//    }

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




//    private fun calculateRate() {
//        val amountFrom: Long = amountInputFrom.getAmount()
//        val amountTo: Long = amountInputTo.getAmount()
//        val r = 1.0f * amountTo / amountFrom
//        if (!java.lang.Float.isNaN(r)) {
//            rateNode.setRate(r)
//        }
//        rateNode.updateRateInfo()
//    }

//    private val onAmountFromChangedListener: AmountInput.OnAmountChangedListener = object :
//            OnAmountChangedListener() {
//        fun onAmountChanged(oldAmount: Long,
//                            newAmount: Long) {
//            val r: Double = rateNode.getRate()
//            if (r > 0) {
//                val amountFrom: Long = amountInputFrom.getAmount()
//                val amountTo = Math.round(r * amountFrom)
//                amountInputTo.setOnAmountChangedListener(null)
//                amountInputTo.setAmount(amountTo)
//                amountInputTo.setOnAmountChangedListener(onAmountToChangedListener)
//            } else {
//                val amountFrom: Long = amountInputFrom.getAmount()
//                val amountTo: Long = amountInputTo.getAmount()
//                if (amountFrom > 0) {
//                    rateNode.setRate(1.0f * amountTo / amountFrom)
//                }
//            }
//            if (amountInputFrom.isIncomeExpenseEnabled()) {
//                if (amountInputFrom.isExpense()) {
//                    amountInputTo.setExpense()
//                } else {
//                    amountInputTo.setIncome()
//                }
//            }
//            rateNode.updateRateInfo()
//            if (amountFromChangeListener != null) {
//                amountFromChangeListener.onAmountChanged(oldAmount, newAmount)
//            }
//        }
//    }


}