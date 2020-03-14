package com.example.seefood

import org.junit.runner.RunWith

class Restaurant(private var name: String?, private var address: String?, private var rating: Int, private var distance: Double, private var numReviews: Int, private var category: String?, private var image: Int) {
    fun getName(): String? {
        return name
    }

    fun getAddress(): String? {
        return address
    }

    fun getRating(): Int {
        return rating
    }

    fun getDistance(): Double {
        return distance
    }

    fun getNumReviews(): Int {
        return numReviews
    }

    fun getCategory(): String? {
        return category
    }

    fun getImage(): Int {
        return image
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun setRating(rating: Int) {
        this.rating = rating
    }

    fun setDistance(distance: Double) {
        this.distance = distance
    }

    fun setNumReviews(numReviews: Int) {
        this.numReviews = numReviews
    }

    fun setCategory(category: String?) {
        this.category = category
    }

    fun setImage(image: Int) {
        this.image = image
    }

}