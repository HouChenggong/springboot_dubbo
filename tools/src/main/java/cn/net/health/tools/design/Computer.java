package cn.net.health.tools.design;

/**
 * @author xiyou
 * 手撸建造者模式
 */
public class Computer {

    private String cpu;//必须
    private String ram;//必须
    private int usbCount;//可选
    private String keyboard;//可选
    private String display;//可选

    //第一步
    static class Builder {
        private String cpu;//必须
        private String ram;//必须
        private int usbCount;//可选
        private String keyboard;//可选
        private String display;//可选

        //第三步
        public Builder(String cup, String ram) {
            this.cpu = cup;
            this.ram = ram;
        }

        //第四步
        public Builder setUsbCount(int usbCount) {
            this.usbCount = usbCount;
            return this;
        }

        //第四步
        public Builder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        //第四步
        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }

        //第五步（最后一步）
        public Computer build() {
            return new Computer(this);
        }

    }

    //第二步
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.usbCount = builder.usbCount;
        this.keyboard = builder.keyboard;
        this.display = builder.display;
    }

    public static void main(String[] args) {
        Computer computer = new Computer.Builder("因特尔", "三星")
                .setDisplay("三星24寸")
                .setKeyboard("罗技")
                .setUsbCount(2)
                .build();

    }
}
