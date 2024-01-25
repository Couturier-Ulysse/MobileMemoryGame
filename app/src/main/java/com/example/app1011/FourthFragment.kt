package com.example.app1011

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.app1011.databinding.FragmentFourthBinding
import com.example.app1011.databinding.FragmentSecondBinding

class FourthFragment : Fragment() {
    private lateinit var binding: FragmentFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timerValue = TimerSingleton.timerValue
        binding.score.text = timerValue.toString()
        binding.btnNavFromFourthToMain.setOnClickListener{
            Log.i("MyApp","ButtonClicked")
            findNavController().navigate(R.id.action_fourthFragment_to_MainFragment)
        }
    }
}