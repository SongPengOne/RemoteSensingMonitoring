package com.aipuer.remotesensingmonitoring;

import java.util.List;

public class AddressBean {

    private String name;
    private boolean isChecked =false;
    private List<BeanSecondItem> beanSecondItem;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "name='" + name + '\'' +
                ", beanSecondItem=" + beanSecondItem +
                '}';
    }

    public List<BeanSecondItem> getBeanSecondItem() {
        return beanSecondItem;
    }

    public void setBeanSecondItem(List<BeanSecondItem> beanSecondItem) {
        this.beanSecondItem = beanSecondItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static class BeanSecondItem {//二级标签类
        public String name;
        public Boolean isCheck;

        public Boolean getCheck() {
            return isCheck;
        }

        public void setCheck(Boolean check) {
            isCheck = check;
        }

        public BeanSecondItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
