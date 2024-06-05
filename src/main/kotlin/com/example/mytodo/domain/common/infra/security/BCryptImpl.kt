package com.example.mytodo.domain.common.infra.security

import at.favre.lib.crypto.bcrypt.BCrypt

class BCryptImpl {

    companion object {
        fun encryption(password: String, key: Int): String {
           return BCrypt.withDefaults().hashToString(10, password.toCharArray())
        }

        fun validPassword(password: String, hashPassword: String): Boolean {
            val result = BCrypt.verifyer().verify(password.toCharArray(), hashPassword)
            return result.verified
        }
    }
}