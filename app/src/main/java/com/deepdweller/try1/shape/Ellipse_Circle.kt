package com.deepdweller.try1.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Build
import androidx.annotation.RequiresApi

class Ellipse_Circle(
    point: PointF,
    width: Float,
    height: Float) : Rectangle(point, width, height) {

    override fun instance(points: MutableList<PointF>): Figure {
        val start = points.first()
        val h = points.last().y - start.y
        val w = points.last().x - start.x
        return Ellipse_Circle(start, w, h)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawOval(position.x, position.y, position.x + width, position.y + height, paint)
    }
}