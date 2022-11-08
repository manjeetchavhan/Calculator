package eu.tutorials.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var isNumericBefore= false
    var isDecimalBefore= false
    var lastNumeric = false
    private var tvInput: TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View){
            tvInput?.append((view as Button).text)
            isNumericBefore=true
            lastNumeric= true
    }
    fun onClear(view:View){
        tvInput?.text=""
        isNumericBefore= false
        isDecimalBefore= false

    }
    fun onDecimalPoint(view:View){
        if(isNumericBefore && !isDecimalBefore){
            tvInput?.append((view as Button).text)
            isDecimalBefore=true
            lastNumeric= false

        }
        else if(!isNumericBefore && !isDecimalBefore){
            tvInput?.append("0.")
            isNumericBefore= true
            isDecimalBefore= true
            lastNumeric= false

        }
        else if(isDecimalBefore){
            Toast.makeText(this,"Not possible.Already a decimal.", Toast.LENGTH_LONG).show()
        }

    }

    fun onOperator(view:View){
        tvInput?.text?.let {
            if(lastNumeric && !operatorAdded((tvInput?.text).toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
            }
            else if(((tvInput?.text)?.length ==0) && (view as Button).text == "-"){
                tvInput?.append("-")
            }
        }
    }

    fun operatorAdded(value: String): Boolean{
        if(value.startsWith("-")) {
            var str = value.substring(1)
            return str.contains("+") || str.contains("-") || str.contains("*")|| str.contains("/")
        }
        return value.contains("+") || value.contains("-") || value.contains("*")|| value.contains("/")
    }

    fun onEqual(view:View){
        if(lastNumeric){
            var tvValue= tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text= removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text= removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text= removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text= removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    fun removeZeroAfterDot(result:String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length-2)
        }
        return value
    }

}