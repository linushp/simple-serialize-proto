import cn.ubibi.commons.ssp.SimpleSerializeProto;
import cn.ubibi.commons.ssp.annotation.SimpleSerializable;
import cn.ubibi.commons.ssp.annotation.SimpleSerializeField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonPo implements SimpleSerializable {

    @SimpleSerializeField(value = 1)
    private String name = "hell0";

    @SimpleSerializeField(value = 2)
    private int sex = 1;

    @SimpleSerializeField(value = 3)
    private long phone_number = 3223;

    @SimpleSerializeField(value = 4 , elementClass = PersonPo.class)
    private List<PersonPo> children = new ArrayList<>();

    @SimpleSerializeField(value = 5)
    private boolean isOK = false;

    @SimpleSerializeField(value = 6, elementClass = PersonPo.class)
    private Map<String, PersonPo> map = new HashMap<>();

    @SimpleSerializeField(value = 7)
    private PersonPo father;


    public PersonPo() {
    }


    public PersonPo(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }



    public static void main(String [] args) throws IOException, IllegalAccessException, InstantiationException {
        PersonPo personPo = new PersonPo();
        personPo.setName("goodnsdkjfndsjfjskdnfkj");
        personPo.setOK(true);
        personPo.getChildren().add(new PersonPo("张三3",1821));
        personPo.getChildren().add(new PersonPo("张sf三3",1213));
        personPo.getChildren().add(new PersonPo("张sad三3",1251));
        personPo.getChildren().add(new PersonPo("张ds三3",126));
        personPo.getChildren().add(new PersonPo("张三cxs3",1291));
        personPo.getChildren().add(new PersonPo("张232三",1421));
        byte[] byteArray = SimpleSerializeProto.toByteArray(personPo);
        System.out.println(byteArray);


        PersonPo p2 = SimpleSerializeProto.parseObject(byteArray,PersonPo.class);
        System.out.println(p2);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public List<PersonPo> getChildren() {
        return children;
    }

    public void setChildren(List<PersonPo> children) {
        this.children = children;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public PersonPo getFather() {
        return father;
    }

    public void setFather(PersonPo father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "PersonPo{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", phone_number=" + phone_number +
                '}';
    }

    @Override
    public int getClassId() {
        return 0;
    }
}
