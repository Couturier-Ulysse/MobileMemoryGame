package com.example.app1011

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.app1011.databinding.FragmentMainBinding
import com.example.app1011.databinding.FragmentSecondBinding
import com.example.app1011.databinding.FragmentThirdBinding

import android.util.Log
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

object DataRepository {
    var flowers: ArrayList<String> = ArrayList()
}
object endLives {
    var lives: Int = 0
}
object User {
    var lives: Int = 0
}

class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThirdBinding
    private lateinit var imageView: ImageView
    //lives initiated at 5
    var TotalLives = 5
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdBinding.inflate(inflater, container, false)
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

        binding.btnNavFromThirdToMain.setOnClickListener{
            findNavController().navigate(R.id.action_thirdFragment3_to_MainFragment)
        }
    }

    private fun setupButtonClickListeners(buttons: Array<Array<ImageView>>,matrix: Array<Array<Int>>,shuffledMatrix: Array<Array<Int>>,pointsMatrix: Array<Array<Int>>) {
        var isDelayActive = false
        for (row in buttons.indices) {
            for (column in buttons[row].indices) {
                buttons[row][column].setOnClickListener {
                    // Appel de la fonction pour obtenir la ligne et la colonne
                    val rowNumber = row+1
                    val columnNumber = column+1
                    var retourne = 0
                    var rowRetourne = 0
                    var columnRetourne = 0
                    var firstValue = 0
                    var secondValue = 0
                    var totalpoints = 0
                    // Exemple d'utilisation des valeurs obtenues

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
                                    TotalLives--
                                    if (TotalLives==0) {
                                        findNavController().navigate(R.id.action_thirdFragment3_to_fifthFragment)
                                    }
                                    val pointImage = "points${TotalLives}"
                                    val imagePointId = resources.getIdentifier(pointImage,
                                        "drawable",
                                        requireContext().packageName)
                                    binding.Buttonvies.setImageResource(imagePointId)

                                    val imageName = "chien${shuffledMatrix[row][column]}"
                                    val imageResourceId = resources.getIdentifier(imageName,
                                        "drawable",
                                        requireContext().packageName)
                                    buttons[row][column].setImageResource(imageResourceId)

                                    matrix[row][column] = 0
                                    matrix[rowRetourne][columnRetourne] = 0
                                    isDelayActive = true
                                    Handler().postDelayed({
                                        // Remet les images à la carte après la pause
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
                        var text = "LIVES mode - score : " + TotalLives.toString() + " lives"
                        Log.d("vies", TotalLives.toString())
                        DataRepository.flowers.add(text)
                        findNavController().navigate(R.id.action_thirdFragment3_to_sixFragment)
                        endLives.lives = TotalLives
                    }
                }
            }
        }
    }
    suspend fun waitTwoSeconds() {
        delay(2000)
    }

    private fun createShuffledMatrix(): Array<Array<Int>> {
        // Créer une liste avec des valeurs de 1 à 8 répétées deux fois
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

