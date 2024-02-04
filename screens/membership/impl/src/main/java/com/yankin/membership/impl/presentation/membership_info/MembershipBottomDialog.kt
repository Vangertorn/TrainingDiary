package com.yankin.membership.impl.presentation.membership_info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.yankin.common.coroutines.observeWithLifecycle
import com.yankin.common.dialog.BaseBottomSheetDialogFragment
import com.yankin.common.resource_import.CommonRAttr
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.viewmodel.AssistedParamsViewModelFactory
import com.yankin.membership.api.navigation.MembershipCommunicator
import com.yankin.membership.impl.di.viewmodel_factories.MembershipViewModelFactory
import com.yankin.membership.impl.navigation.MembershipParcelableParams
import com.yankin.navigation.BundleParcelable
import com.yankin.trainingdiary.membership_screens.impl.R
import com.yankin.trainingdiary.membership_screens.impl.databinding.BottomSheetMembershipBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MembershipBottomDialog : BaseBottomSheetDialogFragment<BottomSheetMembershipBinding>() {

    @Inject
    lateinit var viewModelFactory: MembershipViewModelFactory

    private val viewModel: MembershipViewModel by viewModels {
        AssistedParamsViewModelFactory(factory = viewModelFactory, params = params, owner = this)
    }

    private var params by BundleParcelable<MembershipParcelableParams>(MembershipCommunicator.NAV_KEY)

    override fun parentLayoutId(): Int = R.id.membershipDialog

    override fun attrColorBackground(): Int = CommonRAttr.contentBackground

    override val binding: BottomSheetMembershipBinding by viewBinding(BottomSheetMembershipBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnReset.setOnClickListener {
            viewModel.onDeleteMembershipClick()
            dismiss()
        }
    }

    override fun onObserveData() {
        viewModel.getMembershipUiStream().observeWithLifecycle(this) { uiState ->
            binding.tvTrainingsAmount.text = uiState.trainingLeft
            binding.tvDateValid.text = uiState.endDate
            binding.tvDaysAmount.text = uiState.daysLeft
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {}
}
