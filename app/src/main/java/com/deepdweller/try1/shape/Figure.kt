package com.deepdweller.try1.shape

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.Build
import androidx.annotation.RequiresApi
import com.deepdweller.try1.DrawableItem

abstract class Figure() : DrawableItem {
    var position: PointF = PointF()
    constructor(position: PointF):this(){
        this.position = position
    }

    abstract val minimumPointToCreate: Int

    fun canCreated(point: MutableList<PointF>) =
        point.size >= minimumPointToCreate

    override var myPaint: Paint? = null

    override fun draw(canvas: Canvas, paint: Paint) {
        if (myPaint == null)
            myPaint = Paint(paint)
    }

    abstract fun instance(points: MutableList<PointF>): Figure

}
