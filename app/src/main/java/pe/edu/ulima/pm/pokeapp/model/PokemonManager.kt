package pe.edu.ulima.pm.pokeapp.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import pe.edu.ulima.pm.pokeapp.room.PKAppDatabase
import pe.edu.ulima.pm.pokeapp.model.Pokemon

class PokemonManager(context: Context) {

    private val dbFirebase = Firebase.firestore

    fun getAllPokemon(callbackOK: (List<Pokemon>) -> Unit, callbackError: (String) -> Unit) {
        dbFirebase.collection("pokemon")
            .get()
            .addOnSuccessListener { res ->
                val listPokes = arrayListOf<Pokemon>()
                for(document in res){

                    val pk = Pokemon(
                        document.id.toLong(),
                        document.data["name"]!! as String,
                        (document.data["hp"]!! as Long).toFloat(),
                        (document.data["attack"]!! as Long).toFloat(),
                        (document.data["defense"]!! as Long).toFloat(),
                        (document.data["specialAttack"]!! as Long).toFloat(),
                        (document.data["specialDefense"]!! as Long).toFloat(),
                        document.data["url"]!! as String
                    )
                    listPokes.add(pk)
                }
                callbackOK(listPokes)
            }
            .addOnFailureListener {
                callbackError(it.message!!)
            }
    }


    fun getPokemon(i:Long): Pokemon{
        var pk : Pokemon? = null
        dbFirebase.collection("pokemon").get()
            .addOnSuccessListener { res ->
                for(document in res){
                    if(document.id.toLong() == i){
                        pk = Pokemon(
                            document.id.toLong(),
                            document.data["name"]!! as String,
                            (document.data["hp"]!! as Long).toFloat(),
                            (document.data["attack"]!! as Long).toFloat(),
                            (document.data["defense"]!! as Long).toFloat(),
                            (document.data["specialAttack"]!! as Long).toFloat(),
                            (document.data["specialDefense"]!! as Long).toFloat(),
                            document.data["url"]!! as String
                        )
                    }
                }
            }
        return pk!!
    }
}