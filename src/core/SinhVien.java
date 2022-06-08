/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author Admin
 */
public class SinhVien {
    private String maSV,hoTen,DiaChi,KhoaHoc,maDA,maCN,maLop;
    private boolean phai,trangThaiNghi;

    public SinhVien(String maSV, String hoTen, String DiaChi, String KhoaHoc, String maDA, String maCN, String maLop, boolean phai, boolean trangThaiNghi) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.DiaChi = DiaChi;
        this.KhoaHoc = KhoaHoc;
        this.maDA = maDA;
        this.maCN = maCN;
        this.maLop = maLop;
        this.phai = phai;
        this.trangThaiNghi = trangThaiNghi;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getKhoaHoc() {
        return KhoaHoc;
    }

    public void setKhoaHoc(String KhoaHoc) {
        this.KhoaHoc = KhoaHoc;
    }

    public String getMaDA() {
        return maDA;
    }

    public void setMaDA(String maDA) {
        this.maDA = maDA;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public boolean isPhai() {
        return phai;
    }

    public void setPhai(boolean phai) {
        this.phai = phai;
    }

    public boolean isTrangThaiNghi() {
        return trangThaiNghi;
    }

    public void setTrangThaiNghi(boolean trangThaiNghi) {
        this.trangThaiNghi = trangThaiNghi;
    }
    
}
