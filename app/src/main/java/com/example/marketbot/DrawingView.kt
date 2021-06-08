package com.example.marketbot

import android.R
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.Log.INFO
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.logging.Level.INFO


class DrawingView(context: Context?, attrs: AttributeSet) : View(context, attrs) {
    //drawing path
    private var drawPath: Path

    //defines what to draw
    private var canvasPaint: Paint

    //defines how to draw
    private var drawPaint: Paint

    //initial color
    private val paintColor = -0xFF0000

    //canvas - holding pen, holds your drawings
    //and transfers them to the view
    private lateinit var drawCanvas: Canvas

    //canvas bitmap
    private lateinit var canvasBitmap: Bitmap

    //brush size
    private var currentBrushSize = 0f  //brush size
    private var lastBrushSize = 0f

    private var arrayX: ArrayList<Float> = ArrayList()
    private var arrayY: ArrayList<Float> = ArrayList()

    /**
     * Инициализируем всю основную информацию о View - размер, цвет, стиль и т.д
     */
    init {
        currentBrushSize = 5F
        lastBrushSize = currentBrushSize

        drawPath = Path()
        drawPaint = Paint()
        drawPaint.color = paintColor
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = currentBrushSize
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND

        canvasPaint = Paint(Paint.DITHER_FLAG)

    }

    /**
     * Нужно для создания интерфейса
     * Дает доступ к Canvas
     *
     * @param canvas
     * Canvas предоставляет методы для рисования
     */
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(canvasBitmap!!, 0.0F, 0.0F, canvasPaint)
        canvas?.drawPath(drawPath, drawPaint)
    }

    /**
     * Метод вызывается каждый раз при изменении холста (Canvas)
     * В ней создается кэшируемая растровая картинка Bitmap и связанный с ней холст
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //create Bitmap of certain w,h
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        //apply bitmap to graphic to start drawing.
        drawCanvas = Canvas(canvasBitmap);

    }

    /**
     * Метод обрабатывает нажатия пользователя на холст, добавляет координаты нажатия
     * в листы и рисует
     *
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        arrayX.add(touchX)
        arrayY.add(touchY)

        //Toast.makeText(context,"x = " + arrayX[0] + " y = " + arrayY[0], Toast.LENGTH_SHORT).show()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPath.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                drawPath.lineTo(touchX, touchY)
                drawCanvas.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
            else -> return false
        }
        invalidate()
        return true
    }

    /**
     * Метод очищает листы от координат и перезагружает view
     *
     */
    fun eraseAll(){
        //Toast.makeText(context, "x = $arrayX[0] y = $arrayY", Toast.LENGTH_LONG).show()
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR)
        arrayX.clear()
        arrayY.clear()
        invalidate();
    }

    /**
     * Метод, который создает экземпляр mServer и отправляет данные
     *
     */
    fun sendToServer(){
        val mServer = mServer()
        mServer.openConnection()
        mServer.sendData(arrayX,arrayY)
    }
}



