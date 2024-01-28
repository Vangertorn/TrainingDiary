package com.yankin.common.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yankin.common.debounce.updateGlobalDebounce
import com.yankin.common.drawable.setTintAttr
import com.yankin.common.drawable.setTintColor
import com.yankin.common.fragment.onBackPressed
import com.yankin.common.resource_import.CommonRDrawable
import com.yankin.common.resource_import.MaterialRId
import com.yankin.common.view.removeFromParent
import com.yankin.common.viewbinding.viewBinding
import com.yankin.common.window.copySystemNavigationBarColorFromWindow
import com.yankin.screens.common.databinding.BaseBottomSheetDialogLayoutBinding

abstract class BaseBottomSheetDialogFragment<V : ViewBinding> :
    SupportDialogFragmentInset<BaseBottomSheetDialogLayoutBinding>() {

    @IdRes
    abstract fun parentLayoutId(): Int

    @AttrRes
    abstract fun attrColorBackground(): Int

    @ColorRes
    protected open fun backgroundColorResId(): Int = 0

    protected open fun initViews() {}

    protected open fun title(): String = ""

    protected open fun subTitle(): String = ""

    protected open fun inject() {}

    private val parentBinding by viewBinding(BaseBottomSheetDialogLayoutBinding::inflate)

    protected abstract val binding: V

    var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>? = null
        get() {
            if (field == null) {
                val bottomSheet =
                    dialog?.findViewById<FrameLayout>(MaterialRId.design_bottom_sheet)
                        ?: return null
                field = BottomSheetBehavior.from(bottomSheet)
            }
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.root.removeFromParent()
        parentBinding.container.addView(binding.root)
        onBackPressed { updateGlobalDebounce() }
        return parentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("onCreateDialog", "Current screen: ${this::class.java.name}")
        setTitle()
        setSubTitle()
        setBackground()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setBackground() {
        dialog?.let { safeDialog ->
            safeDialog.findViewById<View>(parentLayoutId())?.background =
                AppCompatResources.getDrawable(requireContext(), CommonRDrawable.background_bottom_sheet_corner)
                    ?.apply {
                        if (backgroundColorResId() != 0) {
                            setTintColor(requireContext(), backgroundColorResId())
                            parentBinding.root.background.setTintColor(
                                requireContext(),
                                backgroundColorResId()
                            )
                        } else {
                            setTintAttr(requireContext(), attrColorBackground())
                            parentBinding.root.background.setTintAttr(
                                requireContext(),
                                attrColorBackground()
                            )
                        }
                    }
        }
    }

    private fun setTitle() {
        if (title().isNotEmpty()) {
            parentBinding.tvTitle.text = title()
            parentBinding.tvTitle.visibility = View.VISIBLE
        }
    }

    private fun setSubTitle() {
        if (subTitle().isNotEmpty()) {
            parentBinding.tvSubTitle.text = subTitle()
            parentBinding.tvSubTitle.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        initViews()
        initSystemNavigationBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSheetBehavior = null
    }

    fun expand() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initSystemNavigationBar() {
        activity?.window?.let { parentWindow ->
            requireDialog().window?.copySystemNavigationBarColorFromWindow(parentWindow)
        }
    }
}
