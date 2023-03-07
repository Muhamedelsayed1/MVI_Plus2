package com.example.mviplus2


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var numberTv:TextView
    lateinit var add_num_btn:Button

    private  val viewModel : AddNumberViewModel by lazy {
        ViewModelProviders.of(this).get(AddNumberViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberTv = findViewById(R.id.number_tv)
        add_num_btn = findViewById(R.id.add_number_btn)
        render()
        add_num_btn.setOnClickListener{
            lifecycleScope.launch {
            viewModel.intentChannel.send(MainIntent.AddNumber)
            }

        }
    }
    private fun render(){
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is MainViewState.Idle-> numberTv.text = "Idle"
                    is MainViewState.Number-> numberTv.text = it.number.toString()
                    is MainViewState.Error-> numberTv.text = it.error
                }
            }
        }

    }
}

