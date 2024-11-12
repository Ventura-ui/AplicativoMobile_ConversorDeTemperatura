package br.edu.ifsp.dmo1.conversordetemperatura.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo1.conversordetemperatura.R
import br.edu.ifsp.dmo1.conversordetemperatura.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.conversordetemperatura.model.FahrenheitToCelsiusStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.CelsiusToFahrenheitStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.CelsiusToKelvinStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.FahrenheitToKelvinStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.KelvinToCelsiusStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.KelvinToFahrenheitStrategy
import br.edu.ifsp.dmo1.conversordetemperatura.model.TemperatureConverter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var converterStrategy: TemperatureConverter
    private var unidadeSelecionada: String = "Celsius"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSpinner()
    }

    private fun setSpinner(){
        binding.spinnerTemperature.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                unidadeSelecionada = parent.getItemAtPosition(position).toString()
                setClickListener()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setClickListener() {
        binding.btnCelsius.setOnClickListener(null)
        binding.btnFahrenheit.setOnClickListener(null)
        binding.btnKelvin.setOnClickListener(null)

        if(unidadeSelecionada == "Celsius"){
            binding.btnCelsius.setOnClickListener {
                Toast.makeText(this, R.string.error_sameConverter, Toast.LENGTH_LONG).show()
            }

            binding.btnFahrenheit.setOnClickListener(View.OnClickListener {
                handleConversion(CelsiusToFahrenheitStrategy)
            })

            binding.btnKelvin.setOnClickListener(View.OnClickListener {
                handleConversion(CelsiusToKelvinStrategy)
            })
        }

        if(unidadeSelecionada == "Kelvin"){
            binding.btnCelsius.setOnClickListener {
                handleConversion(KelvinToCelsiusStrategy)
            }

            binding.btnFahrenheit.setOnClickListener(View.OnClickListener {
                handleConversion(KelvinToFahrenheitStrategy)
            })

            binding.btnKelvin.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, R.string.error_sameConverter, Toast.LENGTH_SHORT).show()
            })
        }

        if(unidadeSelecionada == "Fahrenheit"){
            binding.btnCelsius.setOnClickListener {
                handleConversion(FahrenheitToCelsiusStrategy)
            }

            binding.btnFahrenheit.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, R.string.error_sameConverter, Toast.LENGTH_SHORT).show()
            })

            binding.btnKelvin.setOnClickListener(View.OnClickListener {
                handleConversion(FahrenheitToKelvinStrategy)
            })
        }
    }

    private fun readTemperature(): Double {
        return try {
            binding.edittextTemperature.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            throw NumberFormatException("Input Error")
        }
    }

    private fun handleConversion(strategy: TemperatureConverter) {
        if (unidadeSelecionada == null) {
            Toast.makeText(this, R.string.error_nothing_on_spinner, Toast.LENGTH_SHORT).show()
            return
        }

        converterStrategy = strategy
        try {
            val inputValue = readTemperature()
            binding.textviewResultNumber.text = String.format("%.2f %s", converterStrategy.converter(inputValue), converterStrategy.getScale())
            binding.textviewResultMessage.text = if (this.converterStrategy is FahrenheitToCelsiusStrategy) {
                getString(R.string.msgFtoC)
            } else if(this.converterStrategy is CelsiusToFahrenheitStrategy){
                getString(R.string.msgCtoF)
            }else if(this.converterStrategy is CelsiusToKelvinStrategy){
                getString(R.string.msgCtoK)
            }else if(this.converterStrategy is KelvinToCelsiusStrategy){
                getString(R.string.msgKtoC)
            }else if(this.converterStrategy is KelvinToFahrenheitStrategy){
                getString(R.string.msgKtoF)
            }else{
                getString(R.string.msgFtoK)
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.error_popup_notify),
                Toast.LENGTH_SHORT
            ).show()
            Log.e("APP_DMO", e.stackTraceToString())
        }
    }

}