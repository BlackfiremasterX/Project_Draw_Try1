package com.deepdweller.try1

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deepdweller.try1.shape.Ellipse_Circle
import com.deepdweller.try1.shape.Rectangle
import com.deepdweller.try1.shape.Triangle
import top.defaults.colorpicker.ColorPickerPopup

class CanvasFragment : Fragment(R.layout.fragment_main) {

    lateinit var canvasViewModel: CanvasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        canvasViewModel = ViewModelProvider(requireActivity()).get(CanvasViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val canvasView = root.findViewById<CanvasView>(R.id.canvasView)
        canvasView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                canvasViewModel.addPressPoint(PointF(event.x, event.y))
                canvasViewModel.createFigure()
            }
            true
        }
        canvasViewModel.figures.observe(viewLifecycleOwner){
            canvasView.drawableITems = it?.toMutableList() ?: mutableListOf()
            canvasView.invalidate()
        }

        val figureSpinner = root.findViewById<Spinner>(R.id.shape_spinner)
        figureSpinner.setSelection(canvasViewModel.selectedFigure)
        figureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                canvasViewModel.selectedFigure = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val colorPicker = root.findViewById<ImageButton>(R.id.colorPicker)
        colorPicker.setOnClickListener {
            ColorPickerPopup.Builder(requireContext())
                    .initialColor(Color.WHITE) // Set initial color
                    .enableBrightness(true) // Enable brightness slider or not
                    .enableAlpha(true) // Enable alpha slider or not
                    .okTitle("Выбрать цвет")
                    .cancelTitle("Отмена")
                    .showIndicator(true)
                    .showValue(true)
                    .build()
                    .show(it, object :  ColorPickerPopup.ColorPickerObserver() {
                        override fun onColorPicked(color: Int) {
                            it.setBackgroundColor(color)
                            canvasView.paint.color = color
                        }
                    });
        }

        val undoButton = root.findViewById<Button>(R.id.undoButton)
        undoButton.setOnClickListener {
            canvasViewModel.commands.let {if ( it.isNotEmpty()) it.last().execute()}
        }

        return root
    }

}