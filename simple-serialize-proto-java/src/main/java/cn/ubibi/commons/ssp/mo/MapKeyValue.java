package cn.ubibi.commons.ssp.mo;

import cn.ubibi.commons.ssp.annotation.SimpleSerializable;
import cn.ubibi.commons.ssp.annotation.SimpleSerializeField;

public class MapKeyValue {

//    @SimpleSerializeField(value = 1)
//    private int valueClassId = 0;

    @SimpleSerializeField(value = 2)
    private String key;

    @SimpleSerializeField(value = 3)
    private SimpleSerializable value;



    public MapKeyValue(String key, SimpleSerializable value) {
        this.key = key;
        this.value = value;
    }


    public MapKeyValue() {
    }




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SimpleSerializable getValue() {
        return value;
    }

    public void setValue(SimpleSerializable value) {
        this.value = value;
    }
}
