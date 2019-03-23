package com.project.mqtttest;

public class ItemModal {
    private String bname;
    private String rfid;
    private String itemid;
    private String bamount;
    private String baccount;
    private String btotal;
    private int thumbnail;

    public String getBtotal() {
        return btotal;
    }

    public void setitemid(String itemid) {
        this.itemid=itemid;
    }

    public String getitemid() {
        return itemid;
    }

    public void setrfid(String rfid) {
        this.rfid=rfid;
    }

    public String getRfid() {
        return rfid;
    }

    public void setBtotal(String title) {
        this.btotal= title;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String title) {
        this.bname= title;
    }

    public String getBamount() {
        return bamount;
    }
    public void setBamount(String title) {
        this.bamount= title;
    }

    public String getBaccount() {
        return baccount;
    }
    public void setBaccount(String title) {
        this.baccount= title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
