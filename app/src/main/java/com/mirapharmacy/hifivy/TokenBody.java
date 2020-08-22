package com.mirapharmacy.hifivy;

import java.util.List;

class TokenBody {

    private boolean status;
    private List<User> data;

    public TokenBody(boolean status, List<User> data) {
        this.status = status;
        this.data = data;
    }

    public TokenBody() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
