package com.yankin.navigation

import androidx.navigation.NavGraphBuilder

interface NavigationNode {

    fun addNode(navGraphBuilder: NavGraphBuilder)
}