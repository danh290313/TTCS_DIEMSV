/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.swing;

import java.awt.Color;
import javax.swing.JScrollPane;

/**
 *
 * @author danh
 */
public class scroll {
    public static void sroll(JScrollPane jscroll)
    {
        jscroll.setVerticalScrollBar(new ScrollBar());
        jscroll.getVerticalScrollBar().setBackground(Color.WHITE);
        jscroll.getViewport().setBackground(Color.WHITE);
    }
}
