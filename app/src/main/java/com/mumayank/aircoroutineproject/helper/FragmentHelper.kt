package com.mumayank.aircoroutineproject.helper

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class FragmentHelper {
    companion object {

        fun replaceFragmentInActivity(
            activity: AppCompatActivity?,
            newFragment: Fragment,
            shouldAddCurrentFragmentToBackStack: Boolean,
            fragmentHolderLayoutId: Int
        ) {
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            replaceFragment(
                fragmentTransaction,
                newFragment,
                shouldAddCurrentFragmentToBackStack,
                fragmentHolderLayoutId,
                false
            )
        }

        fun replaceFragmentInFragment(
            fragment: Fragment?,
            newFragment: Fragment,
            shouldAddCurrentFragmentToBackStack: Boolean,
            fragmentHolderLayoutId: Int
        ) {
            val fragmentTransaction = fragment?.childFragmentManager?.beginTransaction()
            replaceFragment(
                fragmentTransaction,
                newFragment,
                shouldAddCurrentFragmentToBackStack,
                fragmentHolderLayoutId,
                false
            )
        }

        private fun replaceFragment(
            fragmentTransaction: FragmentTransaction?,
            newFragment: Fragment?,
            shouldAddCurrentFragmentToBackStack: Boolean,
            fragmentHolderLayoutId: Int,
            shouldAnimateTransition: Boolean
        ) {
            if (fragmentTransaction == null || newFragment == null) {
                return
            }

            try {
                if (shouldAnimateTransition) {
                    fragmentTransaction.setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out
                    )
                }
                fragmentTransaction.replace(fragmentHolderLayoutId, newFragment)
                if (shouldAddCurrentFragmentToBackStack) {
                    fragmentTransaction.addToBackStack(null)
                }
                fragmentTransaction.commit()
            } catch (e: Exception) {
                Log.e("fragmenthelper", e.message ?: "")
            }
        }

    }
}