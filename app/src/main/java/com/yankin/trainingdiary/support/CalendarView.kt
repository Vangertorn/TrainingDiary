package com.yankin.trainingdiary.support

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
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
    val weekTextSizeInPx: Float,
    val enabledBackground: Drawable?
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
    private val tvInvisible: TextView by lazy { findViewById(R.id.tvInvisible) }


    var onDateChangedCallback: DateChangeListener? = null

    var active: Boolean = true
        set(value) {
            field = value
            if (!value) {
                tvInvisible.visibility = View.VISIBLE
            } else {
                tvInvisible.visibility = View.GONE
            }
            if (value) {
                ivLeft.setOnClickListener {
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
                    setMonth(calendar.time)
                }
                ivRight.setOnClickListener {
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
                    setMonth(calendar.time)
                }
            } else {
                ivLeft.isClickable = false
                ivRight.isClickable = false

            }
            setMonthDay()
        }


    private val calendar = Calendar.getInstance().apply {
        time = Date()
    }

    var selectedDate: Date?
        get() {
            return (rvDays.adapter as? DaysAdapter)?.selectedDate
        }
        set(value) {
            (rvDays.adapter as? DaysAdapter)?.selectedDate = value
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

            val enabledBackground = attr.getDrawable(R.styleable.CalendarView_enabled_background)

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
                    weekTextSizeInPx = weekTextSizeInPx,
                    enabledBackground = enabledBackground
                )
            attr.recycle()
        }
        ivLeft.setOnClickListener {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
            setMonth(calendar.time)
        }
        ivRight.setOnClickListener {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
            setMonth(calendar.time)
        }
        setMonth()
    }


    private fun setMonthDay(date: Date = Date()) {
        var currentPosition = 0
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val list = arrayListOf<Date>()

        for (i in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            list.add(calendar.time)
            if (DateUtils.isToday(calendar.time.time)) {
                currentPosition = i - 1
            }
        }
        if (active) {
            rvDays.adapter =
                DaysAdapter(calendarStyleSettings, list, selectedDate, currentPosition) {
                    calendar.time = it
                    onDateChangedCallback?.onDateChanged(it)
                }
            rvDays.background = null
        } else {
            rvDays.adapter = InactiveDaysAdapter(list)
            rvDays.background = calendarStyleSettings.enabledBackground
        }
    }


    private fun setMonth(date: Date = Date()) {
        tvMonth.text = monthFormatter.format(date)
        setMonthDay(date)
    }

    class InactiveDaysAdapter(private val items: List<Date>) :
        RecyclerView.Adapter<InactiveDayViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InactiveDayViewHolder {
            return InactiveDayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.calendar_day_list_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: InactiveDayViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

    }

    class DaysAdapter(
        private val settings: CalendarStyleSettings,
        private val items: List<Date>,
        selected: Date? = null,
        private val currentPosition: Int,
        private val onDateChangedCallback: (Date) -> Unit

    ) : RecyclerView.Adapter<DayViewHolder>() {
        private var recyclerView: RecyclerView? = null

        var selectedDate: Date? = null
            set(value) {
                val calendar = Calendar.getInstance()
                calendar.time = value!!
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                field = calendar.time
                val activeIndex = items.indexOf(field)
                if (activeIndex >= 0) {
                    notifyItemChanged(activeIndex)
                    (recyclerView?.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        activeIndex,
                        recyclerView!!.resources.displayMetrics.widthPixels / 2
                    )
                }
            }

        init {
            selected?.let {
                selectedDate = it
            }
        }


        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)
            this.recyclerView = recyclerView
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                currentPosition,
                recyclerView.resources.displayMetrics.widthPixels / 2
            )
        }

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
            super.onDetachedFromRecyclerView(recyclerView)
            this.recyclerView = null
        }


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

    class InactiveDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDay = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvWeekDay = itemView.findViewById<TextView>(R.id.tvWeekday)
        fun bind(date: Date) {
            tvDay.text = monthDayFormatter.format(date)
            tvWeekDay.text = weekDayFormatter.format(date)
        }
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


    }

    interface DateChangeListener {
        fun onDateChanged(date: Date)
    }

    companion object {
        @SuppressLint("ConstantLocale")
        val monthDayFormatter = SimpleDateFormat("dd", Locale.getDefault())

        @SuppressLint("ConstantLocale")
        val weekDayFormatter = SimpleDateFormat("EE", Locale.getDefault())

        @SuppressLint("ConstantLocale")
        val monthFormatter = SimpleDateFormat("LLLL yyyy", Locale.getDefault())
    }
}
