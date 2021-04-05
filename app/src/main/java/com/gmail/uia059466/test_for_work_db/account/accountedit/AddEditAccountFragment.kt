package com.gmail.uia059466.test_for_work_db.account.accountedit

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.utils.InjectorUtils
import com.gmail.uia059466.test_for_work_db.utils.showKeyboard
import com.google.android.material.snackbar.Snackbar

class AddEditAccountFragment : Fragment() {

    private lateinit var viewModel: AddEditAccountModel

    private lateinit var content: LinearLayout

    private lateinit var caption: LinearLayout
    private lateinit var tvCaption: TextView

    private lateinit var etTitleAccount: EditText
    private lateinit var flIconAccount: FrameLayout
    private lateinit var imgIconAccount: ImageView

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openKeyboard()
    }

    private fun openKeyboard() {
        etTitleAccount.requestFocus()
        showKeyboard()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
                R.layout.add_edit_account_fragment,
                container,
                false
        )
        content = view.findViewById(R.id.add_edit_account_content)

        caption = view.findViewById(R.id.caption_ll)
        tvCaption = view.findViewById(R.id.text)

        imgIconAccount = view.findViewById(R.id.account_img)
        flIconAccount = view.findViewById(R.id.icon_fl)
        flIconAccount.setOnClickListener {
            showIcons()
        }

        etTitleAccount = view.findViewById(R.id.title_account_et)

        setupViewModel()
        setupObservers()

        (activity as MainActivity).hideBottomNavigation()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_in_app_bar)

        setupOnBackPressed()
        setHasOptionsMenu(true)
        return view
    }

    private fun showIcons() {
        val current: IconAccount = viewModel.account.value?.iconAccount ?: IconAccount.CREDIT_CARD
        val dialog = SelectIconAccountDialogFragment.newInstance(current)
        dialog.onOk = {
            val selectedIcon = dialog.selectedNow
            viewModel.selectIcon(selectedIcon)

            dialog.dismiss()

        }
        requireActivity().supportFragmentManager.let { dialog.show(it, "AvatarBottomDialogFragment") }
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
                    tvCaption.visibility=View.VISIBLE
                } else {
                    caption.visibility = View.GONE
                    val title=getString(R.string.add_edit_account_title)
                    (activity as MainActivity).renderTitle(title)
                }
            }
        })

        viewModel.account.observe(viewLifecycleOwner, Observer {
            it?.let {
                imgIconAccount.setImageResource(it.iconAccount.resId)
            }
        })
        viewModel.titleAccount.observe(viewLifecycleOwner, Observer {
            it?.let {
                etTitleAccount.setText(it)
                etTitleAccount.setSelectAllOnFocus(true)
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
        viewModel = ViewModelProvider(this, viewModelFactory)[AddEditAccountModel::class.java]
        val idAccount = arguments?.getLong("idAccount") ?: 0L
        viewModel.start(idAccount)
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
                viewModel.trySave(requestTitleAccount())
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestTitleAccount(): String {
        return etTitleAccount.text.toString().trim()
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