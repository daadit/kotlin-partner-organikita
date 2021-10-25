package com.example.partner_organikita.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.partner_organikita.fragment.DikirimFragment
import com.example.partner_organikita.fragment.DiprosesFragment

class AdapterTabLayoutHome(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                DiprosesFragment()
            }
            1->{
                DikirimFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}