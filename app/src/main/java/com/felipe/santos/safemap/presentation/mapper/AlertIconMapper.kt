package com.felipe.santos.safemap.presentation.mapper

import androidx.annotation.DrawableRes
import com.felipe.santos.safemap.R
import com.felipe.santos.safemap.domain.model.AlertType

object AlertIconMapper {

    @DrawableRes
    fun getIconResId(type: AlertType): Int {
        return when (type) {
            AlertType.VERBAL -> R.drawable.ic_verbal
            AlertType.PHYSICAL -> R.drawable.ic_physical
            AlertType.STALKING -> R.drawable.ic_stalking
            AlertType.FOLLOWING -> R.drawable.ic_following
            AlertType.EXPOSURE -> R.drawable.ic_exposure
        }
    }
}