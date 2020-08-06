package com.creolophus.im.protocol;

/**
 * Client -> Server
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class PushMessageUp {

    private Long messageId;
    private Long groupId;
    private Long receiverId;
    private Long senderId;

    public Long getMessageId() {
        return messageId;
    }
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getGroupId() {
        return groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public PushMessageUp(Long messageId, Long groupId, Long receiverId, Long senderId) {
        this.messageId = messageId;
        this.groupId = groupId;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public PushMessageUp(){}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"messageId\":").append(messageId);
        sb.append(",\"groupId\":").append(groupId);
        sb.append(",\"receiverId\":").append(receiverId);
        sb.append(",\"senderId\":").append(senderId);
        sb.append('}');
        return sb.toString();
    }
}
