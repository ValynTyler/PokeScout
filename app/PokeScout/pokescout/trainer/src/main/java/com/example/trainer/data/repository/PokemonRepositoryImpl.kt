package com.example.trainer.data.repository

import android.util.Log
import com.example.trainer.data.mappers.toPokemonSpecies
import com.example.trainer.data.remote.PokemonApi
import com.example.trainer.domain.model.PokemonSpecies
import com.example.trainer.domain.repository.PokemonRepository
import java.lang.Exception
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
): PokemonRepository {
    override suspend fun getSpeciesById(id: Int): PokemonSpecies? {
        return try {
            api.getSpeciesById(id).toPokemonSpecies()
        } catch (e: Exception) {
            e.message?.let { msg -> Log.e("POKEMON API ERROR", msg) }
            null
        }
    }
}