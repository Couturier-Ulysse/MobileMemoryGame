package com.example.app1011

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.app1011.databinding.FragmentMainBinding
import android.media.MediaPlayer

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var player: MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNavFromMainToSec.setOnClickListener{
            Log.i("MyApp","ButtonClicked")
            findNavController().navigate(R.id.action_MainFragment_to_one_shot_fragment)
            Log.i("MyApp","Navigation done !")
        }
        binding.btnMainToThird.setOnClickListener{
            findNavController().navigate(R.id.action_MainFragment_to_thirdFragment3)
        }
        binding.btnMainToAction.setOnClickListener{
            findNavController().navigate(R.id.action_MainFragment_to_actionActivity)
        }
    }
}