package com.yankin.settings.impl.presentation.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yankin.common.fragment.SupportFragmentInset
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.view.setVerticalMargin
import com.yankin.trainingdiary.settings.impl.R
import com.yankin.trainingdiary.settings.impl.databinding.FragmentInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment : SupportFragmentInset<FragmentInformationBinding>(R.layout.fragment_information) {
    override lateinit var viewBinding: FragmentInformationBinding
    private val viewModel: InformationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentInformationBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.fragment_information, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.toolbarInfo.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.tvLink.text = getString(CommonRString.email_address)

        viewBinding.tvLink.setOnClickListener {
            sendMail()
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarInfo.setPadding(0, top, 0, 0)
        viewBinding.tvLink.setVerticalMargin(marginBottom = bottom)
    }

    private fun sendMail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Vangertorn@yandex.ru"))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(CommonRString.subject))
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, getString(CommonRString.Chose_an_email_client)))
    }
}
