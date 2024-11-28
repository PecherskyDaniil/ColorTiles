package com.example.colortiles

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// тип для координат
data class Coord(val x: Int, val y: Int)
class MainActivity : AppCompatActivity() {

    lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //tiles = Array(4) { Array(4) { View(this) } }
        tiles = arrayOf(
            arrayOf(findViewById(R.id.t00),findViewById(R.id.t01),findViewById(R.id.t02),findViewById(R.id.t03) ),
            arrayOf(findViewById(R.id.t10),findViewById(R.id.t11),findViewById(R.id.t12),findViewById(R.id.t13)),
            arrayOf(findViewById(R.id.t20),findViewById(R.id.t21),findViewById(R.id.t22),findViewById(R.id.t23)),
            arrayOf(findViewById(R.id.t30),findViewById(R.id.t31),findViewById(R.id.t32),findViewById(R.id.t33))
        )
        initField()

    }

    fun getCoordFromString(s: String): Coord {
        var x:Int=s[1].toInt()-('0').toInt()
        var y:Int=s[0].toInt()-('0').toInt()
        return Coord(x,y) // вернуть полученные координаты
    }
    fun changeColor(view: View) {
        val brightColor = resources.getColor(R.color.bright)
        val darkColor = resources.getColor(R.color.dark)
        val drawable = view.background as ColorDrawable
        if (drawable.color ==brightColor ) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }

    fun onClick(v: View) {
        val coord = getCoordFromString(v.getTag().toString())
        changeColor(v)

        for (i in 0..3) {
            changeColor(tiles[coord.y][i])
            changeColor(tiles[i][coord.x])
        }

        if (checkVictory()){
            val intent = Intent(this, VictoryActivity::class.java)
            startActivity(intent)
        }
    }

    fun checkVictory(): Boolean {
        var olddrawable = tiles[0][0].background as ColorDrawable
        for (i in 0..3) {
            for (j in 0..3) {
                if (i == 0 && j == 0) {
                    olddrawable = tiles[i][j].background as ColorDrawable
                }else {
                    var drawable = tiles[i][j].background as ColorDrawable
                    if (drawable.color != olddrawable.color) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun initField() {
        val brightColor = resources.getColor(R.color.bright)
        val darkColor = resources.getColor(R.color.dark)
        for (i in 0..3) {
            for (j in 0..3) {
                var resh= (0..1).random()
                if (resh>0.5) {
                    tiles[i][j].setBackgroundColor(darkColor)
                }else{
                    tiles[i][j].setBackgroundColor(brightColor)
                }

            }
        }
    }



}