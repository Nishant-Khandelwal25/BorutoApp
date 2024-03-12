package com.example.borutoapp.domain.repository

import com.example.borutoapp.domain.model.Hero

interface LocalDataSource {
    fun getSelectedHero(heroId: Int): Hero
}