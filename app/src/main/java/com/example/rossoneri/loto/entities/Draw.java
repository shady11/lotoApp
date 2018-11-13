package com.example.rossoneri.loto.entities;

import java.io.Serializable;

public class Draw implements Serializable {

    private int id;
    private int draw_number;
    private int length;
    private String lottery;
    private int seller_tickets_count;
    private int sold_tickets_count;

    public Draw(int id, int draw_number, int length, String lottery, int seller_tickets_count, int sold_tickets_count) {
        this.id = id;
        this.draw_number = draw_number;
        this.length = length;
        this.lottery = lottery;
        this.seller_tickets_count = seller_tickets_count;
        this.sold_tickets_count = sold_tickets_count;
    }

    public Draw() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDraw_number() {
        return draw_number;
    }

    public void setDraw_number(int draw_number) {
        this.draw_number = draw_number;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getLottery() {
        return lottery;
    }

    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    public int getSeller_tickets_count() {
        return seller_tickets_count;
    }

    public void setSeller_tickets_count(int seller_tickets_count) {
        this.seller_tickets_count = seller_tickets_count;
    }

    public int getSold_tickets_count() {
        return sold_tickets_count;
    }

    public void setSold_tickets_count(int sold_tickets_count) {
        this.sold_tickets_count = sold_tickets_count;
    }
}
