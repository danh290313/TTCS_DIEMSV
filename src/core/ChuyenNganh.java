/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author danh
 */
public class ChuyenNganh {
    private String macn;
    private String tencn;

    public String getMacn() {
        return macn;
    }

    public void setMacn(String macn) {
        this.macn = macn;
    }

    public String getTencn() {
        return tencn;
    }

    public void setTencn(String tencn) {
        this.tencn = tencn;
    }

    public ChuyenNganh(String macn, String tencn) {
        this.macn = macn;
        this.tencn = tencn;
    }

    public ChuyenNganh() {
    }
    
    
}
