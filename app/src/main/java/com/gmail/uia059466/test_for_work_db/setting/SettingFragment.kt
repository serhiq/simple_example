package com.gmail.uia059466.test_for_work_db.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.R

class SettingFragment : Fragment() {


    override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
                R.layout.setting_main_fragment_content,
                container,
                false
        )
        val rvCurrency = view.findViewById<RelativeLayout>(R.id.currencies_rv)
        rvCurrency.setOnClickListener {
            val action=R.id.currencyListFragment
            findNavController().navigate(action)
        }
        val rvRates = view.findViewById<RelativeLayout>(R.id.exchange_rates_rl)
        rvRates.setOnClickListener {
            val action=R.id.action_settingFragment_to_currencyRateListFragment
            findNavController().navigate(action)
        }

        (activity as MainActivity).renderTitle("Настройки")
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(null)
        (activity as MainActivity).showBottomNavigation()
        setupOnBackPressed()
        return view
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
              override fun handleOnBackPressed() {
                goBack()
              }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
          android.R.id.home -> {
                  goBack()

            return true
          }
        }
        return true
    }

    private fun goBack() {
        findNavController().navigateUp()
    }
}