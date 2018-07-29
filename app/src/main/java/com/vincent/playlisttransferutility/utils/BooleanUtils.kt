package com.vincent.playlisttransferutility.utils

class BooleanUtils {
    companion object {
        fun atLeastTwo(valOne: Boolean, valTwo: Boolean, valThree: Boolean): Boolean {
            return valOne && (valTwo || valThree) || (valTwo && valThree)
        }
    }

}