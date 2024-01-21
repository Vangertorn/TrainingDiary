package com.yankin.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BundleParcelable<B : Parcelable>(
    private val key: String,
    private val defaultValue: B? = null
) : ReadWriteProperty<Fragment, B> {
    private var cache: B? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B = cache
        ?: thisRef.arguments?.getParcelable<B>(key).also { cache = it }
        ?: (defaultValue
            ?: throw IllegalArgumentException("Returning value can not be null. Please, specify non null default value"))

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: B) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putParcelable(key, value)
        cache = value
    }
}

class BundleLong(
    private val key: String,
    private val defaultValue: Long = Long.MIN_VALUE
) : ReadWriteProperty<Fragment, Long> {
    private var cache: Long? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Long = cache
        ?: thisRef.arguments?.getLong(key, defaultValue).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Long) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putLong(key, value)
        cache = value
    }
}

class BundleDouble(
    private val key: String,
    private val defaultValue: Double = Double.NaN
) : ReadWriteProperty<Fragment, Double> {
    private var cache: Double? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Double = cache
        ?: thisRef.arguments?.getDouble(key, defaultValue).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Double) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putDouble(key, value)
        cache = value
    }
}

class BundleShort(
    private val key: String,
) : ReadWriteProperty<Fragment, Short> {
    private var cache: Short? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Short = cache
        ?: thisRef.arguments?.getShort(key).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Short) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putShort(key, value)
        cache = value
    }
}

class BundleInt(
    private val key: String,
    private val defaultValue: Int = Int.MIN_VALUE
) : ReadWriteProperty<Fragment, Int> {
    private var cache: Int? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Int = cache
        ?: thisRef.arguments?.getInt(key, defaultValue).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Int) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putInt(key, value)
        cache = value
    }
}

class BundleBoolean(
    private val key: String,
    private val defaultValue: Boolean = false
) : ReadWriteProperty<Fragment, Boolean> {
    private var cache: Boolean? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Boolean = cache
        ?: thisRef.arguments?.getBoolean(key, defaultValue).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Boolean) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putBoolean(key, value)
        cache = value
    }
}

class BundleString(
    private val key: String,
    private val defaultValue: String = ""
) : ReadWriteProperty<Fragment, String> {
    private var cache: String? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): String = cache
        ?: thisRef.arguments?.getString(key, defaultValue).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: String) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putString(key, value)
        cache = value
    }
}

class BundleLongArray(
    private val key: String
) : ReadWriteProperty<Fragment, LongArray> {
    private var cache: LongArray? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): LongArray = cache
        ?: (thisRef.arguments?.getSerializable(key) as? LongArray).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: LongArray) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putSerializable(key, value)
        cache = value
    }
}

@Suppress("UNCHECKED_CAST")
class BundleSerializable<T : Serializable>(
    private val key: String
) : ReadWriteProperty<Fragment, T> {
    private var cache: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T = cache
        ?: thisRef.arguments?.getSerializable(key)?.also { cache = it as T? } as T?
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it }).putSerializable(key, value)
        cache = value
    }

}

@Suppress("UNCHECKED_CAST")
class BundleList<T>(
    private val key: String
) : ReadWriteProperty<Fragment, List<T>> {
    private var cache: List<T>? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): List<T> = cache
        ?: ((thisRef.arguments?.getSerializable(key) as? ArrayList<T>))?.also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: List<T>) {
        (thisRef.arguments
            ?: Bundle().also { thisRef.arguments = it }).putSerializable(key, arrayListOf<T>().apply { addAll(value) })
        cache = value
    }
}

@Suppress("UNCHECKED_CAST")
class BundleArray<T>(
    private val key: String
) : ReadWriteProperty<Fragment, Array<T>> {
    private var cache: Array<T>? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Array<T> = cache
        ?: ((thisRef.arguments?.getSerializable(key) as? Array<T>))?.also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Array<T>) {
        (thisRef.arguments
            ?: Bundle().also { thisRef.arguments = it }).putSerializable(key, arrayListOf<T>().apply { addAll(value) })
        cache = value
    }
}

class BundleParcelableList<T : Parcelable>(
    private val key: String
) : ReadWriteProperty<Fragment, List<T>> {
    private var cache: ArrayList<T>? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): List<T> = cache
        ?: (thisRef.arguments?.getParcelableArrayList<T>(key)).also { cache = it }
        ?: throw IllegalArgumentException()

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: List<T>) {
        (thisRef.arguments ?: Bundle().also { thisRef.arguments = it })
            .putParcelableArrayList(key, ArrayList<T>(value).also { cache = it })
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Bundle.getValue(key: String): T =
    this[key]?.let { value ->
        value as? T ?: throw IllegalArgumentException()
    } ?: throw IllegalArgumentException()
