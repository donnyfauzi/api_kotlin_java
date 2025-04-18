package com.donnyfauzi.api_kotlin_java.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,
    
    @Column(unique = true)
    val email: String,

    var password: String
)