package com.osamaaftab.musicbrainz.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.osamaaftab.musicbrainz.R
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        view.navigate_button.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashFragment_to_resultFragment)
        }
        return view
    }
}