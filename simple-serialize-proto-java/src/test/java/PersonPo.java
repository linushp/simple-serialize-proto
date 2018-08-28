import cn.ubibi.commons.ssp.SimpleSerializeProto;
import cn.ubibi.commons.ssp.SimpleSerializeProtoManager;
import cn.ubibi.commons.ssp.annotation.SimpleSerializable;
import cn.ubibi.commons.ssp.annotation.SimpleSerializeField;

import java.util.*;


public class PersonPo implements SimpleSerializable {

    @SimpleSerializeField(value = 1)
    private String name = "hell0";

    @SimpleSerializeField(value = 2)
    private Integer sex = null;

    @SimpleSerializeField(value = 3)
    private long phone_number = 3223;

    @SimpleSerializeField(value = 4)
    private List<PersonPo> children;

    @SimpleSerializeField(value = 5)
    private boolean isOK = false;

    @SimpleSerializeField(value = 6)
    private Map<String, PersonPo> map;

    @SimpleSerializeField(value = 7)
    private PersonPo father;

    @SimpleSerializeField(value = 8)
    private byte[] bytes = null;


    public PersonPo() {
    }


    public PersonPo(String name, Integer sex, List children) {
        this.name = name;
        this.sex = sex;
        this.children = children;
    }

    public PersonPo(String name, int sex) {
        this.name = name;
        this.sex = sex;
        this.children = new ArrayList<>();
    }


    public static void main(String[] args) throws Exception {



        SimpleSerializeProtoManager.addClass(100001, PersonPo.class);


        byte[] bytes = new byte[]{3, 4};

        PersonPo personPo = new PersonPo();
        personPo.setName("goodnsdkjfndsjfjskdnfkj");
        personPo.setOK(true);
        personPo.setMap(new HashMap<>());
        personPo.setChildren(new ArrayList<>());
        personPo.setBytes(bytes);
        personPo.map.put("1111", new PersonPo(null, null, null));
        personPo.map.put("1112", new PersonPo("s323unwu", 5, null));
        personPo.getChildren().add(new PersonPo("张三3", 1821, null));
        personPo.getChildren().add(new PersonPo("张sf三3", 1213, null));
        personPo.getChildren().add(new PersonPo("张sad三3", 1251, null));
        personPo.getChildren().add(new PersonPo("张ds三3", 126));
        personPo.getChildren().add(new PersonPo("张三cxs3", 1291));
        personPo.getChildren().add(new PersonPo("张232三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张23的范德萨说收到粉丝的发生地2三", 1421));
        personPo.getChildren().add(new PersonPo("张232三", 1421));
        personPo.getChildren().add(new PersonPo("张232三", 1421));
        personPo.setFather(new PersonPo("里斯", 3200));



        while (true){

            long t1 = System.currentTimeMillis();
            byte[] byteArray = SimpleSerializeProto.toByteArray(personPo);
            System.out.println("length: " + byteArray.length);

            Object p2 = SimpleSerializeProto.parseObject(byteArray);
            long t2 = System.currentTimeMillis();
            System.out.println(p2);
            System.out.println(t2-t1);

            Thread.sleep(1000);
        }



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

    public Map<String, PersonPo> getMap() {
        return map;
    }

    public void setMap(Map<String, PersonPo> map) {
        this.map = map;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "PersonPo{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", phone_number=" + phone_number +
                ", children=" + children +
                ", isOK=" + isOK +
                ", map=" + map +
                ", father=" + father +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
