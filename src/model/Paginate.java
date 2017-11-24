/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author RIfat
 */
public class Paginate {
    private int total;
    private int start;
    private int end;
    private int perPage;

    public Paginate() {
    }

    public Paginate(int total, int perPage) {
        this.total = total;
        this.perPage = perPage;
    }
    
    

    public Paginate(int total, int start, int end, int perPage) {
        this.total = total;
        this.start = start;
        this.end = end;
        this.perPage = perPage;
    }
    
    

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
    
    
}
