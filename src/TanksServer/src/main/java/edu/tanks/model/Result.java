package edu.tanks.model;

import java.sql.Timestamp;

public class Result {

    private Long id;
    private int p1Shots;
    private int p1Hits;
    private int p1Misses;
    private int p2Shots;
    private int p2Hits;
    private int p2Misses;
    private Timestamp dateTime;

    public Result() {
    }

    public Result(int p1Shots, int p1Hits, int p1Misses, int p2Shots, int p2Hits, int p2Misses, Timestamp dateTime) {
        id = null;
        this.p1Shots = p1Shots;
        this.p1Hits = p1Hits;
        this.p1Misses = p1Misses;
        this.p2Shots = p2Shots;
        this.p2Hits = p2Hits;
        this.p2Misses = p2Misses;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getP1Shots() {
        return p1Shots;
    }

    public void setP1Shots(int p1Shots) {
        this.p1Shots = p1Shots;
    }

    public int getP1Hits() {
        return p1Hits;
    }

    public void setP1Hits(int p1Hits) {
        this.p1Hits = p1Hits;
    }

    public int getP1Misses() {
        return p1Misses;
    }

    public void setP1Misses(int p1Misses) {
        this.p1Misses = p1Misses;
    }

    public int getP2Shots() {
        return p2Shots;
    }

    public void setP2Shots(int p2Shots) {
        this.p2Shots = p2Shots;
    }

    public int getP2Hits() {
        return p2Hits;
    }

    public void setP2Hits(int p2Hits) {
        this.p2Hits = p2Hits;
    }

    public int getP2Misses() {
        return p2Misses;
    }

    public void setP2Misses(int p2Misses) {
        this.p2Misses = p2Misses;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
