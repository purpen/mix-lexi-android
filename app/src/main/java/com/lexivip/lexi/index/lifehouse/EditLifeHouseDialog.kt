package com.lexivip.lexi.index.lifehouse

import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.basemodule.tools.DimenUtil
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.Util
import com.lexivip.lexi.R
import com.smart.dialog.utils.CornerUtils
import com.smart.dialog.widget.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_edit_life_house.view.*


class EditLifeHouseDialog(context: FragmentActivity?, presenter: LifeHousePresenter, title: CharSequence, description: CharSequence) : BaseDialog<EditLifeHouseDialog>(context) {
    private var titleLifeHouse: CharSequence = title
    private var desc: CharSequence = description
    private val present: LifeHousePresenter = presenter
    private lateinit var view: View
    override fun onCreateView(): View {
        widthScale(0.85f)
        view = View.inflate(context, R.layout.dialog_edit_life_house, null)

        view.background = CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), DimenUtil.getDimensionPixelSize(R.dimen.dp4).toFloat())

        return view
    }

    override fun setUiBeforShow() {
        view.editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                view.textViewTitleLen.text = "${s.length}/16"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        view.editTextDesc.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                view.textViewDescLen.text = "${s.length}/40"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        if (!TextUtils.isEmpty(titleLifeHouse)) {
            view.editTextTitle.setText(titleLifeHouse, TextView.BufferType.EDITABLE)
            view.editTextTitle.setSelection(titleLifeHouse.length)
        }


        if (!TextUtils.isEmpty(desc)) {
            view.editTextDesc.setText(desc, TextView.BufferType.EDITABLE)
            view.editTextDesc.setSelection(desc.length)
        }


        view.button.setOnClickListener {
            val title = view.editTextTitle.text.toString()
            val description = view.editTextDesc.text.toString()
            if (TextUtils.isEmpty(title)) {
                ToastUtil.showInfo(Util.getString(R.string.text_null_life_house_title))
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(description)) {
                ToastUtil.showInfo(Util.getString(R.string.text_null_life_house_description))
                return@setOnClickListener
            }
            present.editLifeHouse(title, description)
            dismiss()
        }

        view.imageViewClose.setOnClickListener { dismiss() }
    }
}