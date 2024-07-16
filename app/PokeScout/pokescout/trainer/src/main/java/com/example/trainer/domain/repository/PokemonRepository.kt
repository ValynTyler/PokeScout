package com.example.trainer.domain.repository

import com.example.trainer.domain.model.PokemonSpecies

interface PokemonRepository {
    suspend fun getSpeciesById(id: Int): PokemonSpecies?
}