package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // daqui para baixo é onde decidimos o que o app vai fazer
        setContentView(R.layout.activity_main)

        //criação do campo de texto que o usuário vai digitar
        val editText: EditText = findViewById(R.id.edit_number)
        //criaçãdo o objeto do campo que vai exibir os resultados gerados
        val txtResult: TextView = findViewById(R.id.txt_result)
        //criação do botão que vai gerar o jogo
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        //banco de dados de preferencias
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        /*if (result != null){
            txtResult.text = "Ultima aposta: $result"
        }
        */
        result.let {
            txtResult.text = "Ultima aposta: $result"
        }

        // opção 1 para escutar eventos de touch precisamos adicionar no xml a tag onclink:
        //android:onClick="buttonClicked"
        //adicionar um função do tipo view: View fora da MainActivity

        //opção:2 criar uma var que seja do tipo (Objeto Anonimo) View.OnClickListener (interface)
        //btnGenerate.setOnClickListener(buttonClickListener)

        //opção 3: mais simples possivel - bloco de código sera disparado pelo onClickListener
        btnGenerate.setOnClickListener {

            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        //validar quando o campo é não é vazio falha numero 1
        if (text.isEmpty()) {
            // vai falhar
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt() //converter string para inteiro

        //aqui é a falha numero 2
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return

        }

        //aqui é o sucesso
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60) //gerar de 0 até 59
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

        /*alternativa 2
        prefs.edit().apply(){
            putString("result",txtResult.text.toString())
            apply()
        }
        */

        /*editor.commit -> Salva de forma sincrona (bloqueia a interface
        * e informa se teve sucesso ou não*/

        /*editor.apply -> Salva de forma assincrona (não bloqueia a interface
        * e não informa se teve sucesso ou não*/

    }


//    val buttonClickListener = View.OnClickListener {
    //aqui podemos colocar nossa logica de programaão. pq será disparado depois do
    //evento de touch do usuário
    //Log.i("Teste", "Botão clicado") }

//    val buttonClickListener = object : View.OnClickListener {
//        //quem chama o onclick é o próprio sdk do android que dispara após o evento de touch
//        override fun onClick(p0: View?) {
//            Log.i("Teste", "Botão clicado")
//        }
//    }

    //essa é função que vai escutar o toque no botão ou Listener
    //fun buttonClicked(view: View){
    //    Log.i("Teste", "Botão clicado")
    //}
}