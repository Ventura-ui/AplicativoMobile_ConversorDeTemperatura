package br.edu.ifsp.dmo1.conversordetemperatura.model

object CelsiusToFahrenheitStrategy : TemperatureConverter{

    override fun converter(temperature: Double): Double {
        return 1.8 * temperature + 32
    }

    override fun getScale(): String {
        return "ºF"
    }
}