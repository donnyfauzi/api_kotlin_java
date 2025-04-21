package com.donnyfauzi.api_kotlin_java.exception

open class BaseException(message: String) : RuntimeException(message) 
    
class EmailAlreadyInUseException(message: String) : BaseException(message)
class UserNotFoundException(message: String) : BaseException(message)
class InvalidPasswordException(message: String) : BaseException(message)
