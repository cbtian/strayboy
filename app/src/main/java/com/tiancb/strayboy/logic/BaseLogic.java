package com.tiancb.strayboy.logic;

import com.tiancb.strayboy.MainApplication;

/**
 * Created by tiancb on 2018/4/11.
 */

public class BaseLogic {
    public MainApplication app;
    public BaseLogic(){
        app = (MainApplication) MainApplication.getInstance();
    }
}
