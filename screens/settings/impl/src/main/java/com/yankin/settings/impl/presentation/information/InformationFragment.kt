package com.yankin.settings.impl.presentation.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yankin.common.fragment.BaseFragment
import com.yankin.common.fragment.SupportFragmentInset
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.view.setVerticalMargin
import com.yankin.common.viewbinding.viewBinding
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.FragmentExerciseAutofillBinding
import com.yankin.trainingdiary.settings.impl.databinding.FragmentInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    override val binding: FragmentInformationBinding by viewBinding(FragmentInformationBinding::bind)
    private val viewModel: InformationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarInfo.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvLink.text = getString(CommonRString.email_address)

        binding.tvLink.setOnClickListener {
            sendMail()
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        binding.toolbarInfo.setPadding(0, top, 0, 0)
        binding.tvLink.setVerticalMargin(marginBottom = bottom)
    }

    private fun sendMail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Vangertorn@yandex.ru"))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(CommonRString.subject))
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, getString(CommonRString.Chose_an_email_client)))
    }
}
