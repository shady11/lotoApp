package com.example.rossoneri.loto.entities;

public class DrawTicket {

    private String ticket_number;
    private String sold_at;

    public DrawTicket(String ticket_number, String sold_at) {
        this.ticket_number = ticket_number;
        this.sold_at = sold_at;
    }

    public DrawTicket() {
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getSold_at() {
        return sold_at;
    }

    public void setSold_at(String sold_at) {
        this.sold_at = sold_at;
    }
}
