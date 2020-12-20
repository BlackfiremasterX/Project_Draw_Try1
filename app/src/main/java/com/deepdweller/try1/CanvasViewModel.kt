package com.deepdweller.try1

import android.graphics.PointF
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepdweller.try1.cmd.Cmd
import com.deepdweller.try1.shape.Figure
import com.deepdweller.try1.shape.Ellipse_Circle
import com.deepdweller.try1.shape.Rectangle
import com.deepdweller.try1.shape.Triangle

class CanvasViewModel : ViewModel() {

    var selectedFigure: Int = 0

    var figures = MutableLiveData<MutableList<Figure>?>()
    var points = mutableListOf<PointF>()
    var commands = mutableListOf<Cmd>()

    fun addPressPoint(pointF: PointF) {
        points.add(pointF)
    }

    fun createFigure(){
        getFigure()?.let {
            if (it.canCreated(points)) {

                commands.add(object : Cmd {
                    override fun execute() {
                        figures.postValue(figures.value?.apply { remove(last()) })
                        commands.remove(this)
                    }
                })

                figures.postValue(figures.value?.apply { add(it.instance(points)) }
                    ?: mutableListOf(it.instance(points)))
                points.clear()
            }
        }
    }

    private fun getFigure(): Figure? =  when (selectedFigure){
        0 -> Rectangle(PointF(), 0f, 0f)
        1 -> Ellipse_Circle(PointF(), 0f, 0f)
        2 -> Triangle()
        else -> null
    }
}


