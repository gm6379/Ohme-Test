package com.georgemcdonnell.biirr

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Beers(
    val data: List<Beer>
)

@Parcelize
data class Beer(
    val id: String,
    val name: String,
    val labels: Labels?,
    val style: Style?,
    val abv: String?,
    val ibu: String?
): Parcelable

@Parcelize
data class Labels (val icon: String): Parcelable

@Parcelize
data class Style(val category: Category, val description: String?): Parcelable

@Parcelize
data class Category(val name: String): Parcelable

@Parcelize
class BeerViewModel(
    val beer: Beer,
    val flag: Int = parseFlag(beer),
    val ibuMap: String? = parseIBU(beer.ibu)): Parcelable {

    companion object {
        fun parseIBU(ibu: String?): String? {
            if (ibu == null) { return null }
            val ibuValue = ibu.toDouble()

            return if (ibuValue <= 20) "Smooth"
            else if (ibuValue > 20 && ibuValue <= 50) "Bitter"
            else "Hipster Plus"
        }

        fun parseFlag(beer: Beer): Int {
            if (beer.style == null) return R.drawable.other

            return when(beer.style.category.name) {
                "British Origin Ales" -> R.drawable.uk
                "Irish Origin Ales" -> R.drawable.ie
                "North American Origin Ales",
                "North American Lager" -> R.drawable.us
                "German Origin Ales" -> R.drawable.de
                "Belgian And French Origin Ales" -> R.drawable.bg
                "International Ale Styles" -> R.drawable.it
                "European-germanic Lager" -> R.drawable.eu
                else -> R.drawable.other
            }
        }
    }

    override fun toString(): String {
        return "Beer: id ${beer.id}," +
                  " name ${beer.name}," +
                  " Labels: ${beer.labels?.icon}," +
                  " Style: Category: name ${beer.style?.category?.name}," +
                  " description: ${beer.style?.category?.name}, " +
                  " abv: ${beer.abv}," +
                  " ibu: ${beer.ibu}"

    }
}
