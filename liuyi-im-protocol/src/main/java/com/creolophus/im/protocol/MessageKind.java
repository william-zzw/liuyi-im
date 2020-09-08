package com.creolophus.im.protocol;

/**
 * @author magicnana
 * @date 2020/8/19 4:33 PM
 */
public enum MessageKind {
    TEXT(1), IMAGE(2);

    int value;

    MessageKind(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static MessageKind valueOf(Integer value) {
        if(value == null) {
            return null;
        }

        for (MessageKind mt : MessageKind.values()) {
            if(mt.value() == value) {
                return mt;
            }
        }

        return null;
    }
}
