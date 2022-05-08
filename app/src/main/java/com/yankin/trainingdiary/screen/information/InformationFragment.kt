package com.yankin.trainingdiary.screen.information

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.trainingdiary.R
import com.example.trainingdiary.databinding.FragmentInformationBinding
import com.example.trainingdiary.support.setVerticalMargin
import com.yankin.trainingdiary.support.SupportFragmentInset
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment :
    SupportFragmentInset<FragmentInformationBinding>(R.layout.fragment_information) {
    override val viewBinding: FragmentInformationBinding by viewBinding()
    private val viewModel: InformationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.toolbarInfo.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.tvLink.text = getString(R.string.email_address)


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
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, getString(R.string.Chose_an_email_client)))
    }
}