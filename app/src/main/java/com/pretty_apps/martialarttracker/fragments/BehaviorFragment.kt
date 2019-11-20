package com.pretty_apps.martialarttracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pretty_apps.martialarttracker.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BehaviorFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BehaviorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BehaviorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_behavior, container, false)
    }
}
