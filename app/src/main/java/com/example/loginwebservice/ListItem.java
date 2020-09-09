package com.example.loginwebservice;

public class ListItem
{
    private  String cid;
    private  String desc;
    private String status;
    private String ctype;

    public ListItem(String cid, String desc, String status, String ctype) {
        this.cid = cid;
        this.desc = desc;
        this.status = status;
        this.ctype = ctype;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }
}
