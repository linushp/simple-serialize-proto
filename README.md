# simple-serialize-proto


## java 序列化和反序列化工具

1.对象
```
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

}
```


序列化
```
 PersonPo personPo = new PersonPo();
 byte[] byteArray = SimpleSerializeProto.toByteArray(personPo);
```


反序列化

```
    PersonPo p2 = SimpleSerializeProto.parseObject(byteArray);
```


注意,使用之前需要为Class分配一个ClassId
```
  SimpleSerializeProtoManager.addClass(1, PersonPo.class);
```
    
    
