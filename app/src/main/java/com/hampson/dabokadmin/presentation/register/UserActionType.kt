package com.hampson.dabokadmin.presentation.register

import android.content.Context
import com.hampson.dabokadmin.R

enum class UserActionType {
    REGISTER,
    UPDATE;

    fun getAppBarTitle(context: Context): String {
        return when (this) {
            REGISTER -> context.getString(R.string.register_meal_headline)
            UPDATE -> context.getString(R.string.update_meal_headline)
        }
    }

    fun getActionSuccessMessage(context: Context): String {
        return when (this) {
            REGISTER -> context.getString(R.string.register_meal_success)
            UPDATE -> context.getString(R.string.update_meal_success)
        }
    }

    fun getActionTitle(context: Context): String {
        return when (this) {
            REGISTER -> context.getString(R.string.register_meal_action)
            UPDATE -> context.getString(R.string.update_meal_action)
        }
    }

    fun getCheckActionMessage(context: Context): String {
        return when (this) {
            REGISTER -> context.getString(R.string.check_register_meal)
            UPDATE -> context.getString(R.string.check_update_meal)
        }
    }
}