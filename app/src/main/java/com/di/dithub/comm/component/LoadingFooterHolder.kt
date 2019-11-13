package com.di.dithub.comm.component

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.di.dithub.R

class LoadingFooterHolder : EpoxyModel<View>() {
    override fun getDefaultLayout(): Int = R.layout.view_loading_footer
}