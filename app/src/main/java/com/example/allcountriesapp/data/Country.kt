package com.example.allcountriesapp.data

import java.io.Serializable

class Country(
    val name: String,
    val nativeName: String,
    val alpha3Code: String,
    val area: String,
    val borders: ArrayList<String>
    ) : Comparable<Country>, Serializable {

    override fun compareTo(other: Country): Int {
        return this.name.compareTo(other.name)
    }

    override fun toString(): String {
        return "Country { " +
                "name= $name, " +
                "nativeName= $nativeName, " +
                "alpha3Code= $alpha3Code, " +
                "area= $area, " +
                "borders= $borders }"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Country) return false
        val that: Country = other
        return name == that.name &&
                nativeName == that.nativeName &&
                alpha3Code == that.alpha3Code &&
                area == that.area &&
                borders == that.borders
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + nativeName.hashCode()
        result = 31 * result + alpha3Code.hashCode()
        result = 31 * result + area.hashCode()
        result = 31 * result + borders.hashCode()
        return result
    }

}