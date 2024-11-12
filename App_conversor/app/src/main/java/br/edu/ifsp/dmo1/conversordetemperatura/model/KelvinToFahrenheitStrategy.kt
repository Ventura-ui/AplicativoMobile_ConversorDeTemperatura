package br.edu.ifsp.dmo1.conversordetemperatura.model

object KelvinToFahrenheitStrategy : TemperatureConverter {

    override fun converter(temperature: Double): Double {
        return (temperature * 1.8) - 459.67
    }

    override fun getScale(): String {
        return "ºF"
    }
}