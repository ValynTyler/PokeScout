package com.example.pokescouttrainer.data.repository

import android.util.Log
import com.example.pokescouttrainer.data.mappers.toPokemonSpecies
import com.example.pokescouttrainer.data.remote.PokemonApi
import com.example.pokescouttrainer.domain.species.PokemonSpecies
import com.example.pokescouttrainer.domain.repository.PokemonRepository
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