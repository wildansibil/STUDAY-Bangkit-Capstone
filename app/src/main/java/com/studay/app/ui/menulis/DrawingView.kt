package com.studay.app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.abs

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var drawPath: Path = Path()
    private var drawPaint: Paint = Paint()
    private lateinit var canvasBitmap: Bitmap
    private var canvasPaint: Paint = Paint(Paint.DITHER_FLAG)
    private var brushSize: Float = 8f
    private var lastTouchX: Float = 0f
    private var lastTouchY: Float = 0f
    private val touchTolerance: Float = ViewConfiguration.get(context).scaledTouchSlop.toFloat()

    init {
        setupDrawing()
    }

    // Set up the paint and drawing properties
    private fun setupDrawing() {
        drawPaint.color = Color.BLACK
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = brushSize
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
    }

    // Handle size change, especially when device orientation changes
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    }

    // Draw the current path and the background bitmap
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)
        canvas.drawPath(drawPath, drawPaint)
    }

    // Handle touch events to draw paths on the canvas
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath.moveTo(touchX, touchY)
                lastTouchX = touchX
                lastTouchY = touchY
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(touchX - lastTouchX)
                val dy = abs(touchY - lastTouchY)

                if (dx >= touchTolerance || dy >= touchTolerance) {
                    drawPath.quadTo(lastTouchX, lastTouchY, (touchX + lastTouchX) / 2, (touchY + lastTouchY) / 2)
                    lastTouchX = touchX
                    lastTouchY = touchY
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                drawPath.lineTo(lastTouchX, lastTouchY)
                val canvas = Canvas(canvasBitmap)
                canvas.drawPath(drawPath, drawPaint)
                drawPath.reset()  // Reset the path for the next touch
                invalidate()
            }
        }

        return true
    }

    // Convert the drawing to a ByteBuffer to pass into a model
    fun convertToByteBuffer(): ByteBuffer {
        val scaledBitmap = Bitmap.createScaledBitmap(canvasBitmap, 28, 28, true)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 28 * 28)
        byteBuffer.order(ByteOrder.nativeOrder())
        val pixels = IntArray(28 * 28)
        scaledBitmap.getPixels(pixels, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height)

        for (pixel in pixels) {
            // Convert pixel to grayscale (normalized)
            val normalizedPixel = ((pixel shr 16 and 0xFF) * 0.299f +
                    (pixel shr 8 and 0xFF) * 0.587f +
                    (pixel and 0xFF) * 0.114f) / 255f
            byteBuffer.putFloat(normalizedPixel)
        }

        return byteBuffer
    }
}