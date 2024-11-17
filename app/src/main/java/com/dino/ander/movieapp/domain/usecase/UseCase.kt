package com.dino.ander.movieapp.domain.usecase

interface UseCase<in Params, out T> {
    fun execute(params: Params): T
}