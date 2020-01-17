package cloud.entity;


public enum GradeEnum implements EnumCommon {
    //年级枚举
    GRADUATION(0, "毕业"), TOBIN(1, "托班"), SMALL(2, "小班"), MIDDLE(3, "中班"), BIG(4, "大班"), OTHER(5, "其它"), KINDER(6, "学前班");

    private int value;  //枚举value字段
    private String description; //枚举描述字段


    GradeEnum(int value, String description) { //构造初始化赋值
        this.description = description;
        this.value = value;
    }

    public static GradeEnum valueOf(int value) {
        switch (value) {
            case 0:
                return GRADUATION;
            case 1:
                return TOBIN;
            case 2:
                return SMALL;
            case 3:
                return MIDDLE;
            case 4:
                return BIG;
            case 5:
                return OTHER;
            case 6:
                return KINDER;
            default:
                return null;
        }
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
