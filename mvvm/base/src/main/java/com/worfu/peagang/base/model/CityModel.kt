package com.worfu.peagang.base.model

import com.contrarywind.interfaces.IPickerViewData

/**
 * 市
 *
 * @author jerry
 */
class CityModel  : IPickerViewData {
    override fun getPickerViewText(): String {
        return name ?: ""
    }
    var name: String? = null
    var districtList: List<DistrictModel>? = null

    constructor() : super() {}

    constructor(name: String, districtList: List<DistrictModel>) : super() {
        this.name = name
        this.districtList = districtList
    }

    override fun toString(): String {
        return ("CityModel [name=" + name + ", districtList=" + districtList
                + "]")
    }

}
