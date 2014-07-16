package com.tektak.iloop.rm.datamodel;

import com.tektak.iloop.rm.common.SendEmail;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tektak on 7/16/14.
 */
public class LogReportParamater {
    private String UId;

    private String fy;
    private String fm;
    private String fd;

    private String ty;
    private String tm;
    private String td;

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getFy() {
        return fy;
    }

    public void setFy(String fy) {
        this.fy = fy;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public void getParameter(HttpServletRequest request){
        this.UId=(String)request.getParameter("filter-by-user");

        this.fy=(String)request.getParameter("from-filter-by-year");
        this.fm=(String)request.getParameter("from-filter-by-month");
        this.fd=(String)request.getParameter("from-filter-by-day");

        this.ty=(String)request.getParameter("to-filter-by-year");
        this.tm=(String)request.getParameter("to-filter-by-month");
        this.td=(String)request.getParameter("to-filter-by-day");
    }

    public String getFromDate(){
        return fy+"-"+fm+"-"+fd;
    }

    public void setFromDate(String Year,String Month,String Day){
        this.fy=Year;
        this.fm=Month;
        this.fd=Day;
    }

    public void setToDate(String Year,String Month,String Day){
        this.ty=Year;
        this.tm=Month;
        this.td=Day;
    }

    public String getToDate(){
        return ty+"-"+tm+"-"+td;
    }

    public boolean IsNull(){
        if(this.UId==null||this.fy==null||this.fm==null||this.fd==null||this.ty==null||this.tm==null||this.td==null){
            return true;
        }
        return false;
    }
}
