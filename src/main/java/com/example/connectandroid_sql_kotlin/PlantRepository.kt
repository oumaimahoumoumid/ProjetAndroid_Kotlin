package com.example.connectandroid_sql_kotlin

import android.net.Uri
import com.example.connectandroid_sql_kotlin.PlantRepository.Singleton.databaseRef
import com.example.connectandroid_sql_kotlin.PlantRepository.Singleton.downloadUri
import com.example.connectandroid_sql_kotlin.PlantRepository.Singleton.plantList
import com.example.connectandroid_sql_kotlin.PlantRepository.Singleton.storageReference
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.net.URI
import java.util.*
import javax.security.auth.callback.Callback

class PlantRepository {
    object Singleton{
      // donner le lien pour acceder au bucket
      private val BUCKET_URL: String="gs://naturecollection-84ee5.appspot.com"

        // se connecter à notre espace de stockage
     val storageReference=FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)
    // se connecter à la référence "plants"

     val databaseRef=FirebaseDatabase.getInstance().getReference("plants")
    // créer une liste qui va contenir nos plantes
    val plantList= arrayListOf<PlantModel>()

        //contenir le lien de l'image courante
        var downloadUri:Uri?=null
    }
    fun upodateData(callback: () -> Unit ){
        //absorber  lkes données depuis la databaseRef ->Lise de plantes
      databaseRef.addValueEventListener(object : ValueEventListener{
          override fun onDataChange(p0: DataSnapshot) {

              // retierer les anciennes
              plantList.clear()

              // recolter la liste
              for(ds in p0.children){

                  //construire un objet plante
                  val plant=ds.getValue(PlantModel::class.java)

                  // verifier que la plante n'est pas null
                  if(plant != null)
                  {
                      //ajouter la plante à notre liste
                      plantList.add(plant)
                  }
              }
              // actionnner le callback
              callback()
          }

          override fun onCancelled(p0: DatabaseError) {}

      })
    }
    //créer une fonction pour envoyer des fichers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit){
        //verifier que ce fichier n'est pas null
        if(file != null){
            val fileName= UUID.randomUUID().toString()+".jpg"
            val ref= storageReference.child(fileName)
            val uploadTask=ref.putFile(file)

            //demarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task->
                // si il y a eu un probleme lors de l'envoi du fichier
                if(!task.isSuccessful){
                    task.exception?.let { throw it}
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task->
                //verifier si tout a bien fonctionné
                if(task.isSuccessful)
                {
                    //recuperer l'image
                    downloadUri=task.result
                    callback()
                }
            }

        }
    }
    // mettre à jour un objet plante en bdd
    fun updatePlant(plant: PlantModel)=databaseRef.child(plant.id).setValue(plant)

    //inserer une nouvelle plante en bdd
    fun insertPlant(plant: PlantModel)=databaseRef.child(plant.id).setValue(plant)


    //supprimer une plante de la base
    fun deletePlant(plant: PlantModel)= databaseRef.child(plant.id).removeValue()

}