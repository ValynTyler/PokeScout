package com.example.pokescouttrainer.domain.repository

import com.example.pokescouttrainer.domain.species.PokemonSpecies

interface PokemonRepository {
    suspend fun getSpeciesById(id: Int): PokemonSpecies?
}