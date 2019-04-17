package com.worfu.peagang.base.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.worfu.peagang.base.model.CityModel;
import com.worfu.peagang.base.model.DistrictModel;
import com.worfu.peagang.base.model.ProvinceModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * create by http://me.xuxiao.wang on 2019/4/15 0015 11:18
 * 地区三级联动
 */
public class AddressPickUtils {
    public void addressPickView(Context context, final OnAddressPickListener listener) {

       final List<ProvinceModel> jsonBean = getAddress();

        if (jsonBean != null) {

            final List<List<String>> bean2 = new ArrayList<>();
            final List<List<ArrayList<String>>> bean3 = new ArrayList<>();

            for (int i = 0; i < jsonBean.size(); i++) {//遍历省份


                ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                List<CityModel> cityListTemp = jsonBean.get(i).getCityList(); // 当前省份城市列表数据

                if (cityListTemp != null) {
                    for (int c = 0; c < cityListTemp.size(); c++) {//遍历该省份的所有城市
                        String cityName = cityListTemp.get(c).getName();
                        cityList.add(cityName);//添加城市
                        ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                        List<DistrictModel> districtListTemp = cityListTemp.get(c).getDistrictList();

                        if (districtListTemp != null) {

                            for (int j = 0; j < districtListTemp.size(); j++) {
                                city_AreaList.add(districtListTemp.get(j).getName());
                            }
                        }

                        province_AreaList.add(city_AreaList);//添加该省所有地区数据
                    }
                }

                /**
                 * 添加省数据
                 * */
//                bean1.add(jsonBean.get(i).getName());

                /**
                 * 添加城市数据
                 */
                bean2.add(cityList);

                /**
                 * 添加地区数据
                 */
                bean3.add(province_AreaList);
            }


            OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v1) {
                    //返回的分别是三个级别的选中位置
                    listener.addressPick(jsonBean.get(options1).getName(), bean2.get(options1).get(options2), bean3.get(options1).get(options2).get(options3));

                }
            }).build();
            pvOptions.setPicker(jsonBean, bean2, bean3);
            pvOptions.show();
        } else {
            ToastUtils.Companion.getInstance().showShortToast("获取地区数据失败");
        }

    }

    private List<ProvinceModel> getAddress() {

        AssetManager asset = Utils.Companion.getContext().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            return handler.getDataList();

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }


    public interface OnAddressPickListener {
        void addressPick(String p1, String p2, String p3);
    }

    public class XmlParserHandler extends DefaultHandler {
        /**
         * 存储所有的解析对象
         */
        private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

        public XmlParserHandler() {

        }

        public List<ProvinceModel> getDataList() {
            return provinceList;
        }

        @Override
        public void startDocument() throws SAXException {
            // 当读到第一个开始标签的时候，会触发这个方法
        }

        ProvinceModel provinceModel = new ProvinceModel();
        CityModel cityModel = new CityModel();
        DistrictModel districtModel = new DistrictModel();

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            // 当遇到开始标记的时候，调用这个方法
            if (qName.equals("province")) {
                provinceModel = new ProvinceModel();
                provinceModel.setName(attributes.getValue(0));
                provinceModel.setCityList(new ArrayList<CityModel>());
            } else if (qName.equals("city")) {
                cityModel = new CityModel();
                cityModel.setName(attributes.getValue(0));
                cityModel.setDistrictList(new ArrayList<DistrictModel>());
            } else if (qName.equals("district")) {
                districtModel = new DistrictModel();
                districtModel.setName(attributes.getValue(0));
                districtModel.setZipcode(attributes.getValue(1));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            // 遇到结束标记的时候，会调用这个方法
            if (qName.equals("district")) {
                cityModel.getDistrictList().add(districtModel);
            } else if (qName.equals("city")) {
                provinceModel.getCityList().add(cityModel);
            } else if (qName.equals("province")) {
                provinceList.add(provinceModel);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
        }

    }

}
