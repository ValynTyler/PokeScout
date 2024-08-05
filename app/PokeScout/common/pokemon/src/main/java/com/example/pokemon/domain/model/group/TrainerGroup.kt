package com.example.pokemon.domain.model.group

object TrainerGroup {
    sealed class Type {
        data object Beginner: Type()
        data object Intermediate: Type()
        data object Advanced: Type()
    }

    fun parseString(string: String): Result<Type> {
        return when (string) {
            "Beginner" -> Result.success(Type.Beginner)
            "Intermediate" -> Result.success(Type.Intermediate)
            "Advanced" -> Result.success(Type.Advanced)
            else -> Result.failure(Exception("Invalid group"))
        }
    }
}