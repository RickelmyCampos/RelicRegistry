package com.gilbersoncampos.relicregistry.data.remote.FirebaseDataSource

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import com.gilbersoncampos.relicregistry.data.mapper.toRecordModel
import com.gilbersoncampos.relicregistry.data.mapper.toRecordRemote
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.data.remote.RemoteDataSource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
const val RECORD_COLECTION="record"
class FirebaseDataSource @Inject constructor():RemoteDataSource {
    override suspend fun getAllRecord(): Flow<List<CatalogRecordModel>> = callbackFlow {
        var recordCollection:CollectionReference? = null
        try {
            recordCollection=FirebaseFirestore.getInstance().collection(RECORD_COLECTION)
        }catch (e:Throwable){
            close(e)
        }

        val subscription = recordCollection?.addSnapshotListener{ snapshot,error->
            if(snapshot == null){return@addSnapshotListener}
            try {
                val list=snapshot.documents.mapNotNull { snap->snap.toRecordModel() }
                trySend(list)
            }catch (e:Throwable){
                close(e)
            }
        }
        awaitClose{subscription?.remove()}
    }

    override fun getRecordById(id: Long): Flow<CatalogRecordModel> {
        TODO("Not yet implemented")
    }

    override suspend fun createRecord(record: CatalogRecordModel):String { // Alterado o tipo de retorno para String
        val re = record.toRecordRemote()
        return try {
            val documentReference = FirebaseFirestore.getInstance()
                .collection(RECORD_COLECTION)
                .add(re)
                .await()

            Log.d("FIREBASE", "Enviado com sucesso ${documentReference.id}")
            documentReference.id
        } catch (e: Exception) {
            Log.e("FIREBASE", "Erro ao enviar para o firebase", e)
            throw e
        }}

    override suspend fun updateRecord(record: CatalogRecordModel) {
        val re=record.toRecordRemote()
        record.idRemote?.let {
            FirebaseFirestore.getInstance().collection(RECORD_COLECTION).document(it).set(re, SetOptions.merge()).addOnSuccessListener{ documentReferece->
                Log.d("FIREBASE", "Atualizado com sucesso")
            }.addOnFailureListener { e->
                Log.d("FIREBASE", "Erro ao enviar para o firebase $e")

            }
        }

    }
}