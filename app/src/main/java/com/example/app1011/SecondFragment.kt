package com.example.app1011

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.app1011.databinding.FragmentSecondBinding

import kotlin.random.Random
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object TimerSingleton {
    var timerValue: Int = 0
}

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private var timerValue = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttons = arrayOf(
            arrayOf(binding.btn11, binding.btn12, binding.btn13, binding.btn14),
            arrayOf(binding.btn21, binding.btn22, binding.btn23, binding.btn24),
            arrayOf(binding.btn31, binding.btn32, binding.btn33, binding.btn34),
            arrayOf(binding.btn41, binding.btn42, binding.btn43, binding.btn44)
        )
        val initialMatrix = Array(4) { Array(4) { 0 } }
        var victory = false
        // Matrix to count which  card has been discovered
        val pointsMatrix = Array(4) { Array(4) { 0 } }
        val shuffledMatrix = createShuffledMatrix()
        for (row in shuffledMatrix) {
            val rowString = row.joinToString(" ")
            Log.d("Matrix", rowString)
        }
        setupButtonClickListeners(buttons,initialMatrix,shuffledMatrix,pointsMatrix)
        binding.btnNavFromSecToMain.setOnClickListener{
            Log.i("MyApp","ButtonClicked")
            findNavController().navigate(R.id.action_secondFragment_to_mainFragment)
        }
        startTimer()
    }
    private fun setupButtonClickListeners(buttons: Array<Array<ImageView>>,matrix: Array<Array<Int>>,shuffledMatrix: Array<Array<Int>>,pointsMatrix: Array<Array<Int>>) {
        var isDelayActive = false
        for (row in buttons.indices) {
            for (column in buttons[row].indices) {
                buttons[row][column].setOnClickListener {
                    val rowNumber = row+1
                    val columnNumber = column+1
                    var retourne = 0
                    var rowRetourne = 0
                    var columnRetourne = 0
                    var firstValue = 0
                    var secondValue = 0
                    var totalpoints = 0
                    for (row in matrix.indices) {
                        for (column in matrix[row].indices) {
                            if(matrix[row][column]==1) {
                                retourne = 1
                                rowRetourne = row
                                columnRetourne = column

                            }
                        }
                    }

                    for (row in matrix.indices) {
                        for (column in matrix[row].indices) {
                            if(pointsMatrix[row][column]==1) {
                                totalpoints+=1
                            }
                        }
                    }

                    if (pointsMatrix[row][column]==0) {
                        if (retourne == 1 && !isDelayActive) {
                            if (matrix[row][column] == 0) {
                                firstValue = shuffledMatrix[rowRetourne][columnRetourne]
                                secondValue = shuffledMatrix[row][column]

                                if (firstValue == secondValue) {
                                    Log.d(
                                        "Resultttt",
                                        "Point gained ! :" + firstValue + secondValue
                                    )
                                    pointsMatrix[row][column]=1
                                    pointsMatrix[rowRetourne][columnRetourne]=1
                                    matrix[row][column] = 0
                                    matrix[rowRetourne][columnRetourne] = 0
                                    totalpoints+=2
                                    for (row in pointsMatrix) {
                                        val rowString = row.joinToString(" ")
                                        Log.d("Points Matrix", rowString)
                                    }
                                    buttons[row][column].setImageResource(R.drawable.blanc)
                                    buttons[rowRetourne][columnRetourne].setImageResource(R.drawable.blanc)
                                }
                                else {
                                    val imageName = "chien${shuffledMatrix[row][column]}"
                                    val imageResourceId = resources.getIdentifier(imageName,
                                        "drawable",
                                        requireContext().packageName)
                                    buttons[row][column].setImageResource(imageResourceId)

                                    matrix[row][column] = 0
                                    matrix[rowRetourne][columnRetourne] = 0
                                    isDelayActive = true
                                    Handler().postDelayed({
                                        buttons[row][column].setImageResource(R.drawable.card)
                                        buttons[rowRetourne][columnRetourne].setImageResource(R.drawable.card)
                                        isDelayActive = false
                                    }, 800)
                                }
                            }
                        }

                        if (retourne == 0) {
                            val imageName = "chien${shuffledMatrix[row][column]}"
                            val imageResourceId = resources.getIdentifier(
                                imageName,
                                "drawable",
                                requireContext().packageName
                            )
                            buttons[row][column].setImageResource(imageResourceId)
                            matrix[row][column] = 1
                        }
                    }

                    for (row in matrix) {
                        val rowString = row.joinToString(" ")
                        Log.d("Matrix", rowString)
                    }
                    if (totalpoints==16){
                        var text = "Race mode - score : " + timerValue.toString() + " seconds"
                        DataRepository.flowers.add(text)
                        findNavController().navigate(R.id.action_SecondFragment_to_fourthFragment)
                        TimerSingleton.timerValue = timerValue
                    }
                }
            }
        }
    }

    suspend fun waitTwoSeconds() {
        delay(2000)
    }

    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                binding.texttimer.text = "$timerValue"
                timerValue++
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun createShuffledMatrix(): Array<Array<Int>> {
        val values = (1..8).flatMap { listOf(it, it) }
        val shuffledValues = values.shuffled(Random)
        val matrix = Array(4) { Array(4) { 0 } }
        var index = 0
        for (row in matrix.indices) {
            for (column in matrix[row].indices) {
                matrix[row][column] = shuffledValues[index]
                index++
            }
        }
        return matrix
    }
}
