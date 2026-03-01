package com.deepmeridian.deepmeridian


class GearClockView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    private val darkGray = Color.parseColor("#222222")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = min(cx, cy) * 0.9f

        drawRoman(canvas, cx, cy, radius)
        drawPrecisionGear(canvas, cx, cy, radius * 0.18f, 40)
        drawHands(canvas, cx, cy, radius)
    }

    private fun drawRoman(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val numerals = arrayOf(
            "XII","I","II","III","IV","V",
            "VI","VII","VIII","IX","X","XI"
        )

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = darkGray
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create("serif", Typeface.BOLD)
            textSize = radius * 0.12f
        }

        for (i in 0 until 12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val x = cx + (radius * 0.88f * cos(angle)).toFloat()
            val y = cy + (radius * 0.88f * sin(angle)).toFloat()
            val offset = (paint.descent() + paint.ascent()) / 2
            canvas.drawText(numerals[i], x, y - offset, paint)
        }
    }

    private fun drawPrecisionGear(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radius: Float,
        teeth: Int
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = darkGray
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }

        for (i in 0 until teeth) {
            val angle = 2 * Math.PI * i / teeth
            val x1 = cx + (radius * cos(angle)).toFloat()
            val y1 = cy + (radius * sin(angle)).toFloat()
            val x2 = cx + ((radius + 12) * cos(angle)).toFloat()
            val y2 = cy + ((radius + 12) * sin(angle)).toFloat()
            canvas.drawLine(x1, y1, x2, y2, paint)
        }
    }

    private fun drawHands(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR)
        val minute = now.get(Calendar.MINUTE)

        val minuteAngle = minute * 6f
        val hourAngle = hour * 30f + minute * 0.5f

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = darkGray
            strokeWidth = 6f
        }

        drawHand(canvas, cx, cy, radius * 0.7f, minuteAngle, paint)
        drawHand(canvas, cx, cy, radius * 0.45f, hourAngle, paint)
    }

    private fun drawHand(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        length: Float,
        angle: Float,
        paint: Paint
    ) {
        canvas.save()
        canvas.rotate(angle, cx, cy)
        canvas.drawLine(cx, cy, cx, cy - length, paint)
        canvas.restore()
    }
}