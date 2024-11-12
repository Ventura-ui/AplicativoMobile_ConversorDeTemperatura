package br.edu.ifsp.dmo1.conversordetemperatura.model

object FahrenheitToCelsiusStrategy : TemperatureConverter{

    override fun converter(temperature: Double) = (temperature - 32) / 1.8

    override fun getScale() = "ºC"
}