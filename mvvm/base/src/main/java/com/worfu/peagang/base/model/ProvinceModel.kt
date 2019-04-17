package com.worfu.peagang.base.model

import com.contrarywind.interfaces.IPickerViewData

/**
 * 省
 *
 * @author suny
 */
class ProvinceModel : IPickerViewData {
    override fun getPickerViewText(): String {
        return name ?: ""
    }

    var name: String? = null
    var cityList: List<CityModel>? = null

    constructor() : super() {}

    constructor(name: String, cityList: List<CityModel>) : super() {
        this.name = name
        this.cityList = cityList
    }

    override fun toString(): String {
        return "ProvinceModel [name=$name, cityList=$cityList]"
    }

}
