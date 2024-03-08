package com.potyvideo.library.globalEnums

enum class EnumControllerVisibility(var valueStr: String, val value: Int) {

    UNDEFINE("UNDEFINE", -1),
    VISIBLE("visible", 0),
    INVISIBLE("invisible", 1);

    companion object {

        operator fun get(value: String?): EnumControllerVisibility {
            if (value == null) {
                return UNDEFINE
            }
            val `arr$` = values()
            for (`val` in `arr$`) {
                if (`val`.valueStr.equals(value.trim { it <= ' ' }, ignoreCase = true)) {
                    return `val`
                }
            }
            return UNDEFINE
        }

        operator fun get(value: Int?): EnumControllerVisibility {
            if (value == null) {
                return UNDEFINE
            }
            val `arr$` = values()
            for (`val` in `arr$`) {
                if (`val`.value === value) {
                    return `val`
                }
            }
            return UNDEFINE
        }
    }
}