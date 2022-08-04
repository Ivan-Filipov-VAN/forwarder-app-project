package com.softuni.forwardingApp.models.view;

public class StatisticViewModel {
    private Long imp;
    private Long exp;

    public StatisticViewModel(Long imp, Long exp) {
        this.imp = imp;
        this.exp = exp;
    }

    public Long getImp() {
        return imp;
    }

    public Long getExp() {
        return exp;
    }
}
