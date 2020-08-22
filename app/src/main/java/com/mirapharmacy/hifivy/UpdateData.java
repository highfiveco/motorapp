package com.mirapharmacy.hifivy;

class UpdateData {

    private boolean status;
    private String msg;
    private int id;
    private int code;

    public UpdateData(boolean status, String msg, int id, int code) {
        this.status = status;
        this.msg = msg;
        this.id = id;
        this.code = code;
    }

    public UpdateData() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
