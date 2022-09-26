package com.out_focusing.out_focusing_backend.global.config

import org.hibernate.dialect.MySQL8Dialect
import org.hibernate.dialect.function.SQLFunctionTemplate
import org.hibernate.type.StandardBasicTypes

class MySQL8DialectCustom : MySQL8Dialect() {

    init {
        registerFunction("match one index", SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "match(?1) against (?2)"))
        registerFunction("match two index", SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "match(?1, ?2) against (?3)"))
    }

}
