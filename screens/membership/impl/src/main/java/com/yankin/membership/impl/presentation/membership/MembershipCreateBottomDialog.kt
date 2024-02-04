package com.yankin.membership.impl.presentation.membership

import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.custom_view.CalendarView
import com.yankin.common.debounce.debounceClick
import com.yankin.common.dialog.BaseBottomSheetDialogFragment
import com.yankin.common.hideKeyboard
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedViewModelFactory
import com.yankin.membership.impl.di.viewmodel_factories.MembershipCreateViewModelFactory
import com.yankin.membership.impl.presentation.membership.models.MembershipCreateEvent
import com.yankin.trainingdiary.membership_screens.impl.R
import com.yankin.trainingdiary.membership_screens.impl.databinding.BottomSheetCreateMembershipBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
internal class MembershipCreateBottomDialog : BaseBottomSheetDialogFragment<BottomSheetCreateMembershipBinding>() {

    @Inject
    lateinit var viewModelFactory: MembershipCreateViewModelFactory

    private val viewModel: MembershipCreateViewModel by viewModels {
        AssistedViewModelFactory(factory = viewModelFactory, owner = this)
    }

    override fun parentLayoutId(): Int = R.id.createMembershipDialog

    override fun attrColorBackground(): Int = CommonRAttr.contentBackground

    override val binding: BottomSheetCreateMembershipBinding by viewBinding(BottomSheetCreateMembershipBinding::inflate)

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etReoccurrence.doOnTextChanged { text, start, before, count ->
            if (start != 0 || before != 0 || count != 0) {
                viewModel.onTrainingCountChange(text ?: "")
            }
        }

        binding.ctvPerpetual.debounceClick {
            it as CheckedTextView
            it.toggle()
            viewModel.onIndefiniteClick()
        }
        binding.ctvUnlimited.debounceClick {
            it as CheckedTextView
            it.toggle()
            viewModel.onUnlimitedClick()
        }

        binding.btnSaveSeasonTicket.setOnClickListener {
            hideKeyboard()
            viewModel.onSaveMembershipClick()
        }

        binding.ibAddQuantity.setOnClickListener {
            viewModel.onTrainingCountUpClick()
        }
        binding.ibRemoveQuantity.setOnClickListener {
            viewModel.onTrainingCountDownClick()
        }
    }

    override fun onInitView() {
        binding.calendar.onDateChangedCallback = object : CalendarView.DateChangeListener {
            override fun onDateChanged(date: Date) {
                viewModel.onDateChange(date)
            }
        }
    }

    override fun onObserveData() {
        viewModel.getMembershipCreateUiStream().observeWithLifecycle(this) { uiState ->
            if (uiState.trainingCount != binding.etReoccurrence.text?.toString()) {
                binding.etReoccurrence.setText(uiState.trainingCount)
            }
            binding.calendar.selectedDate = uiState.selectedDate
            try {
                binding.calendar.active = !uiState.isIndefiniteCheck
            }catch (e:Exception){
                println("TAG1 $e")
            }
            binding.ctvPerpetual.isChecked = uiState.isIndefiniteCheck
            binding.ctvUnlimited.isChecked = uiState.isUnlimitedCheck
            binding.ivCloseNumber.isVisible = uiState.isUnlimitedCheck
        }
        viewModel.getMembershipCreateEventStream().observeWithLifecycle(this) { event ->
            when(event){
                MembershipCreateEvent.Default -> {}
                MembershipCreateEvent.Dismiss -> {
                    dismiss()
                    viewModel.onEventHandle()
                }
                MembershipCreateEvent.EndDateError -> {
                    Toast.makeText(
                        this.context,
                        getString(CommonRString.Term_season_ticket),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.onEventHandle()
                }
                MembershipCreateEvent.NotEnoughTrainingCount -> {
                    Toast.makeText(
                        this.context,
                        getString(CommonRString.number_of_training),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.onEventHandle()
                }
            }
        }
    }
}
