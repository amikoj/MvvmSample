package com.worfu.peagang.base.model

import com.contrarywind.interfaces.IPickerViewData

/**
 * 县/区
 *
 * @author suny
 */
class DistrictModel : IPickerViewData {
    override fun getPickerViewText(): String {
        return name ?: ""
    }
    var name: String? = null
    var zipcode: String? = null

    constructor() : super() {}

    constructor(name: String, zipcode: String) : super() {
        this.name = name
        this.zipcode = zipcode
    }

    override fun toString(): String {
        return "DistrictModel [name=$name, zipcode=$zipcode]"
    }

}
