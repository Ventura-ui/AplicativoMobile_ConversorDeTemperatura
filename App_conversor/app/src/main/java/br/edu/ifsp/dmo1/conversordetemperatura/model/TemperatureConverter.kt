package br.edu.ifsp.dmo1.conversordetemperatura.model

interface TemperatureConverter {

    fun converter(temperature: Double): Double

    fun getScale(): String
}