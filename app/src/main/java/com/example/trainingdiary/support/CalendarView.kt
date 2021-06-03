package com.example.trainingdiary.support
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.format.DateUtils

import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingdiary.R

import java.text.SimpleDateFormat
import java.util.*

class CalendarStyleSettings(
    val dayTextColor: Int,
    val todayTextColor: Int,
    val selectedTextColor: Int,
    val selectedDayBackground: Drawable?,
    val toDayBackground: Drawable?,
    val dayTextSizeInPx: Float,
    val weekTextSizeInPx: Float
)


class CalendarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val rvDays: RecyclerView by lazy { findViewById(R.id.rvDays) }
    private val tvMonth: TextView by lazy { findViewById(R.id.tvMonths) }

    private val ivLeft: ImageView by lazy { findViewById(R.id.ivLeft) }
    private val ivRight: ImageView by lazy { findViewById(R.id.ivRight) }


    var onDateChangedCallback: DateChangeListener? = null

    private val calendar = Calendar.getInstance().apply {
        time = Date()
    }

    val selectedDate: Date?
        get() {
            return (rvDays.adapter as DaysAdapter).selectedDate
        }
    private lateinit var calendarStyleSettings: CalendarStyleSettings


    init {
        View.inflate(context, R.layout.calendar_view, this)
        context.theme?.let { theme ->
            val attr = theme.obtainStyledAttributes(attributeSet, R.styleable.CalendarView, 0, 0)
            val dayTextColor = attr.getColor(R.styleable.CalendarView_day_text_color, Color.BLACK)
            val monthTextColor =
                attr.getColor(R.styleable.CalendarView_month_text_color, Color.BLACK)
            val todayTextColor =
                attr.getColor(R.styleable.CalendarView_today_text_color, Color.BLACK)
            val selectedTextColor =
                attr.getColor(R.styleable.CalendarView_selected_day_text_color, Color.BLACK)
            val ivLeftColor = attr.getColor(R.styleable.CalendarView_ivLeft_color, Color.WHITE)
            val ivRightColor = attr.getColor(R.styleable.CalendarView_ivRight_color, Color.WHITE)
            val selectedDayBackground =
                attr.getDrawable(R.styleable.CalendarView_selected_day_background)
            val toDayBackground =
                attr.getDrawable(R.styleable.CalendarView_today_background)
            val dayTextSizeInPx =
                attr.getDimensionPixelSize(R.styleable.CalendarView_day_text_size, 50).toFloat()
            val monthTextSizeInPx =
                attr.getDimensionPixelSize(R.styleable.CalendarView_month_text_size, 40).toFloat()
            val weekTextSizeInPx =
                attr.getDimensionPixelSize(R.styleable.CalendarView_week_text_size, 40).toFloat()

            tvMonth.setTextColor(monthTextColor)
            tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_PX, monthTextSizeInPx)

            ivLeft.setColorFilter(ivLeftColor)
            ivRight.setColorFilter(ivRightColor)

            calendarStyleSettings =
                CalendarStyleSettings(
                    dayTextColor = dayTextColor,
                    todayTextColor = todayTextColor,
                    selectedTextColor = selectedTextColor,
                    selectedDayBackground = selectedDayBackground,
                    toDayBackground = toDayBackground,
                    dayTextSizeInPx = dayTextSizeInPx,
                    weekTextSizeInPx = weekTextSizeInPx
                )
            attr.recycle()
        }
        setMonth()

        ivLeft.setOnClickListener {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
            setMonth(calendar.time)
        }
        ivRight.setOnClickListener {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
            setMonth(calendar.time)
        }
    }


    private fun setMonthDay(date: Date = Date()) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val list = arrayListOf<Date>()

        for (i in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            list.add(calendar.time)
        }
        rvDays.adapter = DaysAdapter(calendarStyleSettings, list) {
            calendar.time = it
            onDateChangedCallback?.onDateChanged(it)
        }

    }


    private fun setMonth(date: Date = Date()) {
        tvMonth.text = monthFormatter.format(date)
        setMonthDay(date)
    }

    class DaysAdapter(
        private val settings: CalendarStyleSettings,
        private val items: List<Date>,
        private val onDateChangedCallback: (Date) -> Unit,

        ) : RecyclerView.Adapter<DayViewHolder>() {

        var selectedDate: Date? = null
            private set

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
            return DayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.calendar_day_list_item, parent, false), ::onItemClick

            )
        }

        private fun onItemClick(pos: Int) {
            val prevPos = items.indexOf(selectedDate)
            selectedDate = items[pos]
            onDateChangedCallback(items[pos])
            notifyItemChanged(prevPos)
            notifyItemChanged(pos)

        }

        override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
            holder.bind(items[position], selectedDate == items[position], settings)
        }

        override fun getItemCount(): Int = items.size

    }

    class DayViewHolder(itemView: View, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val tvDay = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvWeekDay = itemView.findViewById<TextView>(R.id.tvWeekday)

        init {
            itemView.setOnClickListener {
                onClick(adapterPosition)

            }
        }

        fun bind(date: Date, selected: Boolean, calendarStyleSettings: CalendarStyleSettings) {
            tvDay.text = monthDayFormatter.format(date)
            tvWeekDay.text = weekDayFormatter.format(date)

            val backgroundRes = when {
                selected -> calendarStyleSettings.selectedDayBackground
                DateUtils.isToday(date.time) -> calendarStyleSettings.toDayBackground
                else -> null
            }
            itemView.background = backgroundRes

            val textColor = when {
                selected -> calendarStyleSettings.selectedTextColor
                DateUtils.isToday(date.time) -> calendarStyleSettings.todayTextColor
                else -> calendarStyleSettings.dayTextColor
            }

            tvWeekDay.setTextColor(textColor)
            tvDay.setTextColor(textColor)
            tvWeekDay.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                calendarStyleSettings.weekTextSizeInPx
            )
            tvDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, calendarStyleSettings.dayTextSizeInPx)


        }

        companion object {
            val monthDayFormatter = SimpleDateFormat("dd", Locale.getDefault())
            val weekDayFormatter = SimpleDateFormat("EE", Locale.getDefault())

        }

    }

    interface DateChangeListener {
        fun onDateChanged(date: Date)
    }

    companion object {
        val monthFormatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    }
}
