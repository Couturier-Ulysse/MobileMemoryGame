package com.example.app1011

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.app1011.databinding.FragmentMainBinding
import com.example.app1011.databinding.FragmentOneShotFragmentBinding
import com.example.app1011.databinding.FragmentSecondBinding

class one_shot_fragment : Fragment() {
    private lateinit var binding: FragmentOneShotFragmentBinding
    private var countDown = 3
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneShotFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCountdown()
    }
    private fun startCountdown() {
        val handler = Handler(Looper.getMainLooper())
        val countdownRunnable = object : Runnable {
            override fun run() {
                if (countDown > 0) {
                    binding.textViewCountdown.text = countDown.toString()
                    countDown--
                    handler.postDelayed(this, 1000)
                } else {
                    findNavController().navigate(R.id.action_one_shot_fragment_to_SecondFragment)
                }
            }
        }
        handler.post(countdownRunnable)
    }
}