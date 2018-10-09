package com.smedialink.nmarsyukov.customtablayout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_page.*

class SampleFragmentPage : Fragment() {

    companion object {

        private const val ARG_NUMBER_PAGE_KEY = "number_page_key"

        fun newInstance(numberPage: Int): SampleFragmentPage =
                SampleFragmentPage()
                        .apply {
                            arguments = Bundle()
                                    .apply {
                                        putInt(ARG_NUMBER_PAGE_KEY, numberPage)
                                    }
                        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {

            val pageNumber = getInt(ARG_NUMBER_PAGE_KEY)

            tvPageNumber.text = getString(R.string.page_number, pageNumber)

        }

    }

}